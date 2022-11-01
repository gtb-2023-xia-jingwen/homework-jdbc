package com.tw.domain;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

public class TodoItemRepository {
    private final ServiceConfiguration configuration;

    public TodoItemRepository(ServiceConfiguration configuration) {
        this.configuration = configuration;
    }

    /**
     * Create an item and save it into database. The `checked` status should default to `false`.
     *
     * @param name The name of the item.
     * @return The `id` of the item created.
     * @throws IllegalArgumentException The name is `null`.
     */
    public long create(String name) throws Exception {
        // TODO:
        //   Please implement the method.
        // <-start-
        throw new RuntimeException("Delete me");
        // --end-->
    }

    /**
     * Change item checked status to specified state.
     *
     * @param id The item to update.
     * @param checked The new checked status.
     * @return If the item exist returns true, otherwise returns false.
     */
    public boolean changeCheckedStatus(long id, boolean checked) throws Exception {
        // TODO:
        //   Please implement the method.
        // <-start-
        throw new RuntimeException("Delete me");
        // --end-->
    }

    /**
     * Get item by its id.
     *
     * @param id The id of the item.
     * @return The item entity. If the item does not exist, returns empty.
     */
    public Optional<TodoItem> findById(long id) throws Exception {
        // TODO:
        //   Please implement the method.
        // <-start-
        throw new RuntimeException("Delete me");
        // --end-->
    }

    // TODO:
    //   You can add additional method if you want
    // <-start-
    public Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName(configuration.getDriver());
            conn = DriverManager.getConnection(configuration.getUri(), configuration.getUsername(),
                    configuration.getPassword());
        }catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
        return conn;
    }

    public void createTable() {
        try(Connection con = getConnection()) {
            Statement stat = con.createStatement();
            BufferedReader bf = new BufferedReader(new FileReader("V001__create_todo_list_table.sql"));
            String createSql = bf.readLine();
            stat.execute(createSql);
        }catch (SQLException e) {
            System.out.println(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // --end-->
}
