package org.capstone.ui;

import org.capstone.model.Task;
import org.capstone.service.TodoService;

import java.io.PrintStream;
import java.util.Scanner;

public class ConsoleUI {
    private final TodoService todoService;
    private final Scanner input;
    private final PrintStream output;

    public ConsoleUI() {
        this(new TodoService(), new Scanner(System.in), System.out);
    }

    public ConsoleUI(Scanner input, PrintStream output) {
        this(new TodoService(), input, output);
    }

    public ConsoleUI(TodoService todoService, Scanner input, PrintStream output) {
        this.todoService = todoService;
        this.input = input;
        this.output = output;
    }

    public void run() {
        boolean running = true;

        while (running) {
            displayMenu();

            if (!input.hasNextLine()) {
                return;
            }

            String selection = input.nextLine().trim();

            switch (selection) {
                case "1":
                    addTask();
                    break;
                case "2":
                    viewTasks();
                    break;
                case "3":
                    completeTask();
                    break;
                case "4":
                    deleteTask();
                    break;
                case "5":
                    running = false;
                    break;
                default:
                    output.println("Invalid menu option. Please select a number from 1 to 5.");
                    break;
            }
        }
    }

    private void addTask() {
        while (true) {
            output.print("Enter task title: ");
            if (!input.hasNextLine()) {
                return;
            }
            String title = input.nextLine();

            try {
                Task task = todoService.addTask(title);
                output.println("Task added with ID " + task.getId() + ": " + task.getTitle());
                return;
            } catch (IllegalArgumentException exception) {
                output.println("Task title cannot be empty. Please try again.");
            }
        }
    }

    private void viewTasks() {
        var tasks = todoService.getAllTasks();
        if (tasks.isEmpty()) {
            output.println("No tasks found.");
            return;
        }

        for (Task task : tasks) {
            output.println("ID: " + task.getId()
                    + " | Title: " + task.getTitle()
                    + " | Status: " + (task.isCompleted() ? "Complete" : "Incomplete"));
        }
    }

    private void completeTask() {
        Integer taskId = readTaskId("Enter task ID to complete: ");
        if (taskId == null) {
            return;
        }

        if (todoService.completeTask(taskId)) {
            output.println("Task " + taskId + " marked complete.");
        } else {
            output.println("Task ID not found");
        }
    }

    private void deleteTask() {
        Integer taskId = readTaskId("Enter task ID to delete: ");
        if (taskId == null) {
            return;
        }

        if (todoService.deleteTask(taskId)) {
            output.println("Task " + taskId + " deleted successfully.");
        } else {
            output.println("Task ID not found");
        }
    }

    private Integer readTaskId(String prompt) {
        output.print(prompt);
        if (!input.hasNextLine()) {
            return null;
        }

        try {
            return Integer.parseInt(input.nextLine().trim());
        } catch (NumberFormatException exception) {
            output.println("Invalid task ID. Please enter a number.");
            return null;
        }
    }

    private void displayMenu() {
        output.println();
        output.println("1. Add Task");
        output.println("2. View Tasks");
        output.println("3. Complete Task");
        output.println("4. Delete Task");
        output.println("5. Exit");
        output.print("Select an option: ");
    }
}
