package com.tw.domain;

import com.tw.dbTest.DbAssert;
import com.tw.dbTest.InMemoryDbSupport;
import com.tw.dbTest.TestDatabaseConfiguration;
import org.jooq.Record;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@InMemoryDbSupport
class TodoItemRepositoryTest {

    @Test
    void should_connection() {
        TodoItemRepository repository = new TodoItemRepository(
                TestDatabaseConfiguration.getConfiguration());

        Connection conn = repository.getConnection();

        assertNotEquals(null, conn);
    }

    @Test
    void should_create_todo_list_item() throws Exception {
        // Given
        TodoItemRepository repository = new TodoItemRepository(
            TestDatabaseConfiguration.getConfiguration());

        // When
        long id = repository.create("Read a book");

        // Then
        Record todoItem = DbAssert.getTodoItem(id);
        assertEquals("Read a book", todoItem.getValue("name", String.class));
        assertFalse(todoItem.getValue("checked", boolean.class));
    }

    @Test
    void should_throw_if_name_is_not_provided() throws Exception {
        // Given
        TodoItemRepository repository = new TodoItemRepository(
            TestDatabaseConfiguration.getConfiguration());

        // When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> repository.create(null));
        assertEquals("Name must be provided.", exception.getMessage());
    }

    @Test
    void should_check_todo_list_item() throws Exception {
        // Given
        TodoItemRepository repository = new TodoItemRepository(
            TestDatabaseConfiguration.getConfiguration());
        long id = repository.create("The name is not important");

        // When
        boolean updated = repository.changeCheckedStatus(id, true);

        // Then
        assertTrue(updated);

        Record todoItem = DbAssert.getTodoItem(id);
        assertTrue(todoItem.getValue("checked", boolean.class));
    }

    @Test
    void should_uncheck_todo_list_item() throws Exception {
        // Given
        TodoItemRepository repository = new TodoItemRepository(
            TestDatabaseConfiguration.getConfiguration());
        long id = repository.create("The name is not important");
        boolean updated = repository.changeCheckedStatus(id, true);
        assertTrue(updated);

        // When
        repository.changeCheckedStatus(id, false);

        // Then
        Record todoItem = DbAssert.getTodoItem(id);
        assertFalse(todoItem.getValue("checked", boolean.class));
    }

    @Test
    void should_return_false_if_todo_list_item_does_not_exist() throws Exception {
        // Given
        TodoItemRepository repository = new TodoItemRepository(
            TestDatabaseConfiguration.getConfiguration());

        // When
        final int notExistedId = 666;
        boolean updated = repository.changeCheckedStatus(notExistedId, true);

        // Then
        assertFalse(updated);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    void should_get_todo_item() throws Exception {
        // Given
        TodoItemRepository repository = new TodoItemRepository(
            TestDatabaseConfiguration.getConfiguration());
        long firstId = repository.create("item 1");
        long secondId = repository.create("item 2");
        repository.changeCheckedStatus(secondId, true);

        // When
        TodoItem firstItem = repository.findById(firstId).get();
        TodoItem secondItem = repository.findById(secondId).get();

        assertEquals(firstId, firstItem.getId());
        assertEquals("item 1", firstItem.getName());
        assertFalse(firstItem.isChecked());

        assertEquals(secondId, secondItem.getId());
        assertEquals("item 2", secondItem.getName());
        assertTrue(secondItem.isChecked());
    }

    @Test
    void should_return_empty_if_todo_item_does_not_exist() throws Exception {
        // Given
        TodoItemRepository repository = new TodoItemRepository(
            TestDatabaseConfiguration.getConfiguration());

        // When
        final int notExistedId = 666;
        final Optional<TodoItem> item = repository.findById(notExistedId);

        // Then
        assertFalse(item.isPresent());
    }
}