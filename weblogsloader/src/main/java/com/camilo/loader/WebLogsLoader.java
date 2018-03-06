package com.camilo.loader;

import com.camilo.bean.IpAccess;
import com.camilo.db.ConnectionFactory;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static java.lang.String.format;

public class WebLogsLoader {
    private static final Logger logger = LoggerFactory.getLogger(WebLogsLoader.class);

    private static final String DELIMITER = "|";
    private static final int CHUNK_SIZE = 10000;


    private static final String COUNT_ROWS = "SELECT count(1) FROM loader.%s";
    private static final String TRUNCATE_TABLE = "TRUNCATE TABLE loader.%s";

    private final ConnectionFactory connectionFactory;

    @Inject
    public WebLogsLoader(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void loadLogsToDb(File webAccessLog) throws IOException, SQLException {
        // As split method requires a regex, here it is quoted as a safety measure
        String quotedDelimiter = Pattern.quote(DELIMITER);

        truncateTablesIfNeeded();

        logger.info("Processing file: {}", webAccessLog.getAbsolutePath());
        ExecutorService pool = null;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(webAccessLog))) {
            int processors = Runtime.getRuntime().availableProcessors();
            logger.debug("Creating a thread pool with {} threads", processors);
            pool = Executors.newFixedThreadPool(processors);

            String line = bufferedReader.readLine();
            List<IpAccess> accessList = new ArrayList<>();
            while (line != null) {
                String[] parts = line.split(quotedDelimiter);
                accessList.add(new IpAccess(parseDate(parts[0]), parts[1], parts[2]));

                if (accessList.size() >= CHUNK_SIZE) {
                    pool.execute(new WebLogInsertTask(accessList, connectionFactory));
                    accessList = new ArrayList<>();
                }

                line = bufferedReader.readLine();
            }
            pool.execute(new WebLogInsertTask(accessList, connectionFactory));

        } finally {
            if (pool != null) {
                shutdownAndAwaitTermination(pool);
            }
        }
    }

    private void shutdownAndAwaitTermination(ExecutorService pool) {
        // Disable new tasks from being submitted
        pool.shutdown();
        try {
            // Wait a while for existing tasks to terminate
            if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
                pool.shutdownNow(); // Cancel currently executing tasks
                // Wait a while for tasks to respond to being cancelled
                if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
                    System.err.println("Pool did not terminate");
                }
            }
        } catch (InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted
            pool.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }

    private void truncateTablesIfNeeded() throws SQLException {
        try (Connection conn = connectionFactory.getConnection()) {
            // It is assumed that the tool should start with a clean slate
            if (getRowCount(conn, "web_server_access") > 0) {
                logger.info("Table web_server_access is not empty, truncating it");
                truncateTable(conn, "web_server_access");
            }
            if (getRowCount(conn, "blocked_ips") > 0) {
                logger.info("Table blocked_ips is not empty, truncating it");
                truncateTable(conn, "blocked_ips");
            }
        }
    }

    private void truncateTable(Connection conn, String tableName) throws SQLException {
        try (Statement st = conn.createStatement()) {
            st.execute(format(TRUNCATE_TABLE, tableName));
        }
    }

    private int getRowCount(Connection conn, String tableName) throws SQLException {
        try (Statement st = conn.createStatement();
             ResultSet resultSet = st.executeQuery(format(COUNT_ROWS, tableName))) {
            resultSet.next();
            return resultSet.getInt(1);

        }
    }

    private Timestamp parseDate(String dateStr) {
        return Timestamp.valueOf(LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
    }
}