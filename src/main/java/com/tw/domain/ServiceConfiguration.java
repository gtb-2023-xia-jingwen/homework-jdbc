package com.tw.domain;

/**
 * @author Liu Xia
 *
 * IMPORTANT: You cannot modify this file.
 */
public interface ServiceConfiguration {
    /**
     * Get the uri of the data source
     * @return the data source uri.
     */
    String getUri();

    /**
     * Get the username used to access the data source.
     * @return the user name.
     */
    String getUsername();

    /**
     * Get the password used to access the data source.
     * @return the password.
     */
    String getPassword();

    /**
     * Get the driver of the data source.
     * @return the driver of the data source.
     */
    String getDriver();
}
