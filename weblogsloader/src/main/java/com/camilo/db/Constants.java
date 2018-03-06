package com.camilo.db;

public class Constants {
    // Driver names
    public static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";

    // Base URLs
    public static final String MYSQL_SERVER_PORT_URL = "jdbc:mysql://%s/%s";

    // Suppress default constructor for noninstantiability
    private Constants() {
        throw new UnsupportedOperationException();
    }
}