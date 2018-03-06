package com.camilo.config;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ArchaiusMySqlConfigurationTest {
    @Test
    public void checkMySqlConfigValuesAreReadProperly() {
        ArchaiusMySqlConfiguration archaiusMySqlConfiguration = new ArchaiusMySqlConfiguration();

        assertEquals(10, archaiusMySqlConfiguration.getConnectionMax());
        assertEquals("jdbc:mysql://localhost:3306/loader", archaiusMySqlConfiguration.getUrl());
    }
}
