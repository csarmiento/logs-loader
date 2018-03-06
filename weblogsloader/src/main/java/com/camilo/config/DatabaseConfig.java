package com.camilo.config;

public interface DatabaseConfig {
    /**
     * JDBC driver full class name.
     *
     * @return JDBC driver full class name.
     */
    String getDriverClass();

    /**
     * DB user name.
     *
     * @return db user name.
     */
    String getUsername();

    /**
     * DB password.
     *
     * @return db password.
     */
    String getPassword();

    /**
     * Default database name.
     *
     * @return default database name.
     */
    String getDb();

    /**
     * Full JDBC url to connect to the db.
     *
     * @return full JDBC url to connect to the db.
     */
    String getUrl();

    int getConnectionMin();

    int getConnectionMax();

    long getConnectionTimeoutMs();

    long getConnectionIdleTimeoutMs();

    long getConnectionMaxLifetimeMs();

    /**
     * 'Test' query fired periodically by the connection pool to keep the connection open.
     *
     * @return 'Test' query fired periodically by the connection pool to keep the connection open.
     */
    String getConnectionTestQuery();
}