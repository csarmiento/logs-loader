package com.camilo.db;

import com.camilo.config.DatabaseConfig;
import com.google.inject.Inject;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class MySQLConnectionFactory extends AbstractConnectionFactory implements ConnectionFactory {
    private static final Logger logger = LoggerFactory.getLogger(MySQLConnectionFactory.class);

    private final DatabaseConfig dbConfig;

    private DataSource dataSource;

    @Inject
    MySQLConnectionFactory(DatabaseConfig dbConfig) {
        super();
        this.dbConfig = dbConfig;
    }

    @Override
    public Connection getConnection() throws SQLException {
        try {
            return getOrCreateDataSource().getConnection();
        } catch (SQLException e) {
            logger.error("Unable to get a connection to database: '" + dbConfig.getUrl() + "'");
            throw e;
        }
    }

    @Override
    public DataSource getOrCreateDataSource() {
        DataSource localDataSource = dataSource;
        if (localDataSource == null) {
            synchronized (this) {
                localDataSource = dataSource;
                if (localDataSource == null) {
                    localDataSource = createDataSource(dbConfig, dbConfig.getUrl());
                    dataSource = localDataSource;
                }
            }
        }
        return dataSource;
    }

    @Override
    public void close() {
        Optional.ofNullable(((HikariDataSource) dataSource)).ifPresent(HikariDataSource::close);
    }
}