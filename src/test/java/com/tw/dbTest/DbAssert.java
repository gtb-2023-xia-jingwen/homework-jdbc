package com.tw.dbTest;

import com.tw.DatabaseConnectionProvider;
import com.tw.domain.ServiceConfiguration;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.util.function.BiFunction;

import static org.jooq.impl.DSL.field;

public class DbAssert {
    static { System.setProperty("org.jooq.no-logo", "true"); }

    public static Record getTodoItem(long id) throws Exception {
        return (Record) execute((connection, dslContext) -> dslContext.select(
                field("id"),
                field("name"),
                field("checked")
            )
            .from("todo_items")
            .where(field("id").eq(id))
            .fetchOne());
    }

    private static Object execute(BiFunction<Connection, DSLContext, Object> func) throws Exception {
        final ServiceConfiguration configuration = TestDatabaseConfiguration.getConfiguration();
        try (
            final Connection connection = DatabaseConnectionProvider.createConnection(configuration);
            final DSLContext context = DSL.using(connection, SQLDialect.H2)
        ) {
            return func.apply(connection, context);
        }
    }
}
