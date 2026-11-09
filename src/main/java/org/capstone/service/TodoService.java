package org.capstone.service;

import org.capstone.model.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TodoService {
    private final List<Task> tasks = new ArrayList<>();
    private int nextTaskId = 1;

    public Task addTask(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Task title cannot be empty.");
        }

        String trimmedTitle = title.trim();
        Task task = new Task(nextTaskId, trimmedTitle);
        tasks.add(task);
        nextTaskId++;
        return task;
    }

    public List<Task> getAllTasks() {
        return Collections.unmodifiableList(new ArrayList<>(tasks));
    }

    public boolean completeTask(int taskId) {
        for (Task task : tasks) {
            if (task.getId() == taskId) {
                task.markCompleted();
                return true;
            }
        }

        return false;
    }

    public boolean deleteTask(int taskId) {
        for (int index = 0; index < tasks.size(); index++) {
            if (tasks.get(index).getId() == taskId) {
                tasks.remove(index);
                return true;
            }
        }

        return false;
    }
}
