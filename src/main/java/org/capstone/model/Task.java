package org.capstone.model;

import lombok.Getter;

@Getter
public class Task {
    private final int id;
    private final String title;
    private boolean completed;

    public Task(int id, String title) {
        this.id = id;
        this.title = title;
        this.completed = false;
    }

    public void markCompleted() {
        completed = true;
    }
}
