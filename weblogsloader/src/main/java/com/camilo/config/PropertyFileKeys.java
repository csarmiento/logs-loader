package com.camilo.config;

public class PropertyFileKeys {
    // MySQL configuration environment keys
    public static final String MYSQL_USER = "mysql.user";
    public static final String MYSQL_PASSWORD = "mysql.password";
    public static final String MYSQL_CONNECTION_MINIMUM_IDLE = "mysql.connection.minimum.idle";
    public static final String MYSQL_CONNECTION_MAXIMUM_CONNECTIONS = "mysql.connection.maximum.connections";
    public static final String MYSQL_CONNECTION_TIMEOUT = "mysql.connection.timeout";
    public static final String MYSQL_IDLE_TIMEOUT = "mysql.idle.timeout";
    public static final String MYSQL_URL = "mysql.url";
    public static final String MYSQL_DEFAULT_DATABASE = "mysql.default.database";
    public static final String MYSQL_CONNECTION_MAX_LIFETIME = "mysql.connection.max.lifetime";

    // Suppress default constructor for noninstantiability
    private PropertyFileKeys() {
        throw new UnsupportedOperationException();
    }
}