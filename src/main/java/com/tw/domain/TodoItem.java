package com.tw.domain;

public class TodoItem {
    private final long id;
    private final String name;
    private final boolean checked;

    public TodoItem(long id, String name, boolean checked) {
        this.id = id;
        this.name = name;
        this.checked = checked;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isChecked() {
        return checked;
    }
}
