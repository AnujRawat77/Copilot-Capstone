package org.capstone.model;

import lombok.Getter;

@Getter
public final class Task {
    private final int id;
    private final String title;
    private final boolean completed;

    public Task(int id, String title) {
        this(id, title, false);
    }

    private Task(int id, String title, boolean completed) {
        this.id = id;
        this.title = title;
        this.completed = completed;
    }

    public Task completedCopy() {
        return new Task(id, title, true);
    }
}
