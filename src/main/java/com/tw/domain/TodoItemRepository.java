package com.tw.domain;

import java.sql.*;
import java.util.Optional;

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
        if (null == name) throw new IllegalArgumentException("Name must be provided.");
        String insetSql = "INSERT INTO `todo_items` (name) VALUES (?)";
        String querySql = "SELECT `id` FROM `todo_items` WHERE `name` =  ?";
        try (Connection conn = getConnection();
             PreparedStatement preStat1 = conn.prepareStatement(insetSql);
             PreparedStatement preStat2 = conn.prepareStatement(querySql);
        ) {
            preStat1.setString(1, name);
            preStat1.executeUpdate();
            preStat2.setString(1, name);
            ResultSet rs = preStat2.executeQuery();
            long id = -1L;
            while (rs.next()) {
                id = rs.getLong("id");
            }
            rs.close();
            return id;
        }
        // --end-->
    }

    /**
     * Change item checked status to specified state.
     *
     * @param id      The item to update.
     * @param checked The new checked status.
     * @return If the item exist returns true, otherwise returns false.
     */
    public boolean changeCheckedStatus(long id, boolean checked) throws Exception {
        // TODO:
        //   Please implement the method.
        // <-start-
        String querySql = "SELECT * FROM `todo_items` WHERE `id` = ?";
        String updateSql = "UPDATE `todo_items` SET `checked` = ? WHERE `id` = ?";
        try (Connection con = getConnection();
             PreparedStatement preStat1 = con.prepareStatement(querySql);
             PreparedStatement preStat2 = con.prepareStatement(updateSql);
        ) {
            preStat1.setLong(1, id);
            ResultSet rs = preStat1.executeQuery();
            if (!rs.next()) return false;
            rs.close();
            preStat2.setBoolean(1, checked);
            preStat2.setLong(2, id);
            preStat2.executeUpdate();
            return true;
        }
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
        String querySql = "SELECT * FROM `todo_items` WHERE `id` = ?";
        try (Connection conn = getConnection();
             PreparedStatement preStat = conn.prepareStatement(querySql);
        ) {
            preStat.setLong(1, id);
            preStat.executeQuery();
            ResultSet rs = preStat.getResultSet();
            TodoItem res = null;
            while (rs.next()) {
                long idd = rs.getLong("id");
                String name = rs.getString("name");
                boolean checked = rs.getBoolean("checked");
                res = new TodoItem(idd, name, checked);
            }
            rs.close();
            return Optional.ofNullable(res);
        }
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
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
        return conn;
    }
    // --end-->
}
