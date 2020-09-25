package com.tw;

import com.tw.domain.ServiceConfiguration;

public class DatabaseConfiguration implements ServiceConfiguration {
    private final String uri;
    private final String username;
    private final String password;
    private final String driver;

    public DatabaseConfiguration(String uri, String username, String password, String driver) {
        this.uri = uri;
        this.username = username;
        this.password = password;
        this.driver = driver;
    }

    @Override
    public String getUri() {
        return uri;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getDriver() {
        return driver;
    }
}
