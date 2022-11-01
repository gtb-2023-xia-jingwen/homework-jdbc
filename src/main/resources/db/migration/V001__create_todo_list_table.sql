-- TODO
-- Please create a table in this script
--
-- (1) The name of the table should be `todo_items`
-- (2) The table should contains the following columns
--     (a) `id` column. This is the primary key. Its type should be correspond to `Long` type in Java.
--         This column should be auto_increment when an record is inserted.
--     (b) `name` column. This column is of String type. Its maximum character count should be 128.
--         This column should not be null.
--     (c) `checked` column. This column is of Boolean type in Java. In SQL, it can be BIT. This column
--         should not be null. And its default value should be 0(false).

CREATE TABLE `todo_items` (
    `id` INT AUTO_INCREMENT,
    `name` VARCHAR(128) NOT NULL,
    `checked` BIT NOT NULL DEFAULT 0,
    PRIMARY KEY ( `id` ));