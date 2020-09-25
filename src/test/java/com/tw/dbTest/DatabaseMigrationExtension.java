package com.tw.dbTest;

import com.tw.DatabaseConnectionProvider;
import com.tw.domain.ServiceConfiguration;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.sql.Connection;

public class DatabaseMigrationExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {
    private static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(
        "org", "tw", "DatabaseMigrationExtension"
    );
    private static final String CONNECTION_KEY = "H2-Connection";

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        Connection connection = (Connection) context.getStore(NAMESPACE).get(CONNECTION_KEY);
        if (connection == null) {
            return;
        }

        connection.close();
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        final ServiceConfiguration configuration = TestDatabaseConfiguration.getConfiguration();
        final String uri = configuration.getUri();
        final String username = configuration.getUsername();
        final String password = configuration.getPassword();

        Connection connection = DatabaseConnectionProvider.createConnection(configuration);

        try {
            context.getStore(NAMESPACE).put(CONNECTION_KEY, connection);
        } catch (Exception error) {
            connection.close();
            return;
        }

        Flyway migration = Flyway.configure().dataSource(uri, username, password).load();
        migration.migrate();
    }
}

