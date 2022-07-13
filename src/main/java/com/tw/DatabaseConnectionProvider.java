package com.tw;

import com.tw.domain.ServiceConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnectionProvider {
    private DatabaseConnectionProvider() {
    }

    public static Connection createConnection(ServiceConfiguration configuration) throws Exception {
        return DriverManager.getConnection(
            configuration.getUri(), configuration.getUsername(), configuration.getPassword());
    }
}
