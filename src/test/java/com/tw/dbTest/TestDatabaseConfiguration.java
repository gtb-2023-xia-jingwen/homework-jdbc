package com.tw.dbTest;

import com.tw.DatabaseConfiguration;
import com.tw.domain.ServiceConfiguration;

public class TestDatabaseConfiguration {
    private static final DatabaseConfiguration configuration = new
        DatabaseConfiguration("jdbc:h2:mem:testDB;MODE=MYSQL;", "sa", "p@ssword", "org.h2.Driver");

    public static ServiceConfiguration getConfiguration() {
        return configuration;
    }
}

