package com.camilo.config;

import com.netflix.config.DynamicIntProperty;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;

import static com.camilo.config.PropertyFileKeys.MYSQL_CONNECTION_MAXIMUM_CONNECTIONS;
import static com.camilo.config.PropertyFileKeys.MYSQL_CONNECTION_MAX_LIFETIME;
import static com.camilo.config.PropertyFileKeys.MYSQL_CONNECTION_MINIMUM_IDLE;
import static com.camilo.config.PropertyFileKeys.MYSQL_CONNECTION_TIMEOUT;
import static com.camilo.config.PropertyFileKeys.MYSQL_DEFAULT_DATABASE;
import static com.camilo.config.PropertyFileKeys.MYSQL_IDLE_TIMEOUT;
import static com.camilo.config.PropertyFileKeys.MYSQL_PASSWORD;
import static com.camilo.config.PropertyFileKeys.MYSQL_URL;
import static com.camilo.config.PropertyFileKeys.MYSQL_USER;
import static com.camilo.db.Constants.MYSQL_DRIVER;

public class ArchaiusMySqlConfiguration implements DatabaseConfig {

    private static final String EMPTY_STRING = "";

    @Override
    public String getDriverClass() {
        return MYSQL_DRIVER;
    }

    @Override
    public String getUsername() {
        return getStringProperty(MYSQL_USER);
    }

    @Override
    public String getPassword() {
        return getStringProperty(MYSQL_PASSWORD);
    }

    @Override
    public String getDb() {
        return getStringProperty(MYSQL_DEFAULT_DATABASE);
    }

    @Override
    public String getUrl() {
        if (this.getDb().equals(EMPTY_STRING)) {
            return getStringProperty(MYSQL_URL);
        } else {
            return getStringProperty(MYSQL_URL) + "/" + this.getDb();
        }
    }

    @Override
    public int getConnectionMin() {
        return getIntProperty(MYSQL_CONNECTION_MINIMUM_IDLE);
    }

    @Override
    public int getConnectionMax() {
        return getIntProperty(MYSQL_CONNECTION_MAXIMUM_CONNECTIONS);
    }

    @Override
    public long getConnectionTimeoutMs() {
        return getIntProperty(MYSQL_CONNECTION_TIMEOUT);
    }

    @Override
    public long getConnectionIdleTimeoutMs() {
        return getIntProperty(MYSQL_IDLE_TIMEOUT);
    }

    @Override
    public long getConnectionMaxLifetimeMs() {
        return getIntProperty(MYSQL_CONNECTION_MAX_LIFETIME);
    }

    @Override
    public String getConnectionTestQuery() {
        return "SELECT 1";
    }

    private String getStringProperty(String propertyKey) {
        DynamicStringProperty dynamicStringProperty = DynamicPropertyFactory.getInstance().getStringProperty(propertyKey, EMPTY_STRING);
        if (EMPTY_STRING.equals(dynamicStringProperty.get())) {
            throw new RuntimeException(String.format("Config value for: '%s' does not exist", propertyKey));
        } else {
            return dynamicStringProperty.get();
        }
    }

    private int getIntProperty(String propertyKey) {
        DynamicIntProperty dynamicIntProperty = DynamicPropertyFactory.getInstance().getIntProperty(propertyKey, Integer.MIN_VALUE);
        if (Integer.MIN_VALUE == dynamicIntProperty.get()) {
            throw new RuntimeException(String.format("Config value for: '%s' does not exist", propertyKey));
        } else {
            return dynamicIntProperty.get();
        }
    }
}