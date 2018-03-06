package com.camilo.loader;

import com.camilo.bean.IpAccess;
import com.camilo.db.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class WebLogInsertTask implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(WebLogInsertTask.class);
    private static final int BATCH_SIZE = 1000;

    private static final String INSERT_INTO_WEB_SERVER_ACCESS = "INSERT INTO loader.web_server_access VALUES (?, ?, ?)";

    private List<IpAccess> accessList;
    private ConnectionFactory connectionFactory;

    WebLogInsertTask(List<IpAccess> accessList, ConnectionFactory connectionFactory) {
        this.accessList = accessList;
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void run() {
        logger.debug("Starting insertion of {} records", accessList.size());
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_INTO_WEB_SERVER_ACCESS)) {

            int processedRecords = 0;
            for (IpAccess access : accessList) {
                ps.setTimestamp(1, access.getAccessTimestamp());
                ps.setString(2, access.getIp());
                ps.setString(3, access.getRequest());

                ps.addBatch();

                if (++processedRecords % BATCH_SIZE == 0) {
                    ps.executeBatch();
                }
            }
            ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}