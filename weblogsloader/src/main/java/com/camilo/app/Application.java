package com.camilo.app;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.camilo.bean.BlockedIp;
import com.camilo.binding.WebLogsLoaderModule;
import com.camilo.db.BlockedIpsDao;
import com.camilo.db.WebServerAccessDao;
import com.camilo.jcommander.converters.DateConverter;
import com.camilo.jcommander.converters.DurationConverter;
import com.camilo.jcommander.validators.DurationValidator;
import com.camilo.loader.WebLogsLoader;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Parameters(separators = "=")
public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    @Parameter
    private List<String> parameters = new ArrayList<>();

    @Parameter(names = {"--startDate"}, description = "Start date with yyyy-MM-dd.HH:mm:ss format", converter = DateConverter.class, required = true)
    private LocalDateTime startDate;

    @Parameter(names = {"--duration"}, description = "Time interval, accepted values are: [daily, hourly]", converter = DurationConverter.class, validateWith = DurationValidator.class, required = true)
    private com.camilo.enums.Duration duration;

    @Parameter(names = {"--threshold"}, description = "Maximum number of allowed requests", required = true)
    private int threshold;

    @Parameter(names = {"--accesslog"}, description = "Path to web access log file", required = true)
    private String accessLog;

    private final WebLogsLoader webLogsLoader;
    private WebServerAccessDao webServerAccessDao;
    private BlockedIpsDao blockedIpsDao;

    @Inject
    public Application(WebLogsLoader webLogsLoader, WebServerAccessDao webServerAccessDao, BlockedIpsDao blockedIpsDao) {
        this.webLogsLoader = webLogsLoader;
        this.webServerAccessDao = webServerAccessDao;
        this.blockedIpsDao = blockedIpsDao;
    }

    public static void main(String[] args) throws IOException, SQLException {
        Injector injector = Guice.createInjector(new WebLogsLoaderModule());

        Application application = injector.getInstance(Application.class);

        JCommander.newBuilder()
                .addObject(application)
                .build()
                .parse(args);
        application.run();
    }

    private void run() throws IOException, SQLException {
        Instant start = Instant.now();
        webLogsLoader.loadLogsToDb(new File(accessLog));
        Instant end = Instant.now();
        logger.info("Loaded file to database in {} seconds", Duration.between(start, end).getSeconds());

        logger.info("IPs that made more than {} requests starting from {} ({})", threshold, startDate, duration);
        List<BlockedIp> blockedIps = webServerAccessDao.getBlockedIps(startDate, duration, threshold);
        blockedIps.forEach(blockedIp -> {
            logger.info("IP: {}, Requests made:{}", blockedIp.getIp(), blockedIp.getRequestsMade());
        });

        logger.info("Inserting blocked IPs to DB");

        blockedIpsDao.insert(blockedIps);
    }
}