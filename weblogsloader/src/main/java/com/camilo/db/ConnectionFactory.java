package com.camilo.db;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionFactory extends AutoCloseable {
    /**
     * Get or create the connection pool.
     *
     * @return DataSource
     */
    DataSource getOrCreateDataSource();

    /**
     * Get a Connection from the connection pool. The client is responsible for closing the Connection.
     *
     * @return DataSource.
     */
    Connection getConnection() throws SQLException;
}
