package com.camilo.binding;

import com.camilo.config.ArchaiusMySqlConfiguration;
import com.camilo.config.DatabaseConfig;
import com.camilo.db.ConnectionFactory;
import com.camilo.db.MySQLConnectionFactory;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class WebLogsLoaderModule extends AbstractModule {
    @Override
    protected void configure() {
        // configs
        bind(DatabaseConfig.class).to(ArchaiusMySqlConfiguration.class).in(Scopes.SINGLETON);

        // connection factory
        bind(ConnectionFactory.class).to(MySQLConnectionFactory.class).in(Scopes.SINGLETON);
    }
}