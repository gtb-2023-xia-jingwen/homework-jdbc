package com.tw;

import com.tw.domain.ServiceConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnectionProvider {
    public static Connection createConnection(ServiceConfiguration configuration) throws Exception {
        Class.forName(configuration.getDriver());
        return DriverManager.getConnection(
            configuration.getUri(), configuration.getUsername(), configuration.getPassword());
    }
}
