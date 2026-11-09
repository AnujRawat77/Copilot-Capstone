package org.capstone.ui;

import org.capstone.service.TodoService;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ConsoleUITest {

    @Test
    public void runDisplaysAllMenuOptionsAndExitsWhenExitSelected() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ConsoleUI consoleUI = createConsoleUI("5\n", output);

        consoleUI.run();

        String text = output.toString(StandardCharsets.UTF_8);
        assertTrue(text.contains("1. Add Task"));
        assertTrue(text.contains("2. View Tasks"));
        assertTrue(text.contains("3. Complete Task"));
        assertTrue(text.contains("4. Delete Task"));
        assertTrue(text.contains("5. Exit"));
        assertEquals(countOccurrences(text, "1. Add Task"), 1);
    }

    @Test
    public void runReportsUnsupportedInputAndContinuesUntilExit() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ConsoleUI consoleUI = createConsoleUI("invalid\n9\n5\n", output);

        consoleUI.run();

        String text = output.toString(StandardCharsets.UTF_8);
        assertEquals(countOccurrences(text, "Invalid menu option."), 2);
        assertEquals(countOccurrences(text, "1. Add Task"), 3);
    }

    @Test
    public void runAcceptsValidSelectionsAndReturnsToMenu() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ConsoleUI consoleUI = createConsoleUI("1\nMy task\n2\n3\n1\n4\n1\n5\n", output);

        consoleUI.run();

        String text = output.toString(StandardCharsets.UTF_8);
        assertTrue(text.contains("Task added with ID 1: My task"));
        assertTrue(text.contains("ID: 1 | Title: My task | Status: Incomplete"));
        assertTrue(text.contains("Enter task title:"));
        assertTrue(text.contains("Enter task ID to complete:"));
        assertTrue(text.contains("Task 1 marked complete."));
        assertTrue(text.contains("Enter task ID to delete:"));
        assertTrue(text.contains("Task 1 deleted successfully."));
        assertEquals(countOccurrences(text, "1. Add Task"), 5);
    }

    @Test
    public void addTaskRetriesInvalidTitleThenAddsValidTaskAndReturnsToMenu() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ConsoleUI consoleUI = createConsoleUI("1\n   \n  Write tests  \n5\n", output);

        consoleUI.run();

        String text = output.toString(StandardCharsets.UTF_8);
        assertTrue(text.contains("Task title cannot be empty. Please try again."));
        assertTrue(text.contains("Task added with ID 1: Write tests"));
        assertEquals(countOccurrences(text, "1. Add Task"), 2);
    }

    @Test
    public void addTaskPromptsBeforeReadingTitleAndRetryInput() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        TodoService service = new TodoService();
        Readable stagedInput = new Readable() {
            private int stage;

            @Override
            public int read(CharBuffer target) {
                String text = output.toString(StandardCharsets.UTF_8);
                String nextInput = switch (stage++) {
                    case 0 -> "1\n";
                    case 1 -> {
                        assertTrue(text.endsWith("Enter task title: "),
                                "The title prompt must be printed before waiting for a title.");
                        yield "   \n";
                    }
                    case 2 -> {
                        assertTrue(text.contains("Task title cannot be empty. Please try again."));
                        assertEquals(countOccurrences(text, "Enter task title: "), 2,
                                "The retry prompt must be printed before waiting for retry input.");
                        assertTrue(text.endsWith("Enter task title: "));
                        assertEquals(service.getAllTasks().size(), 0);
                        yield "  Write tests  \n";
                    }
                    case 3 -> {
                        assertTrue(text.contains("Task added with ID 1: Write tests"));
                        yield "5\n";
                    }
                    default -> null;
                };
                if (nextInput == null) {
                    return -1;
                }
                target.put(nextInput);
                return nextInput.length();
            }
        };
        ConsoleUI consoleUI = new ConsoleUI(service, new Scanner(stagedInput),
                new PrintStream(output, true, StandardCharsets.UTF_8));

        consoleUI.run();

        assertEquals(service.getAllTasks().size(), 1);
        assertEquals(service.getAllTasks().get(0).getTitle(), "Write tests");
        assertEquals(countOccurrences(output.toString(StandardCharsets.UTF_8), "1. Add Task"), 2);
    }

    @Test(timeOut = 5000)
    public void addTaskHandlesEndOfInputBeforeTitleOrRetryWithoutCreatingTask() {
        for (String input : new String[]{"1\n", "1\n   \n"}) {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            TodoService service = new TodoService();
            ConsoleUI consoleUI = createConsoleUI(service, input, output);

            consoleUI.run();

            assertEquals(service.getAllTasks().size(), 0);
            assertEquals(countOccurrences(output.toString(StandardCharsets.UTF_8), "Enter task title: "),
                    input.equals("1\n") ? 1 : 2);
        }
    }

    @Test
    public void viewTasksDisplaysAllTasksAndTheirStatus() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        TodoService service = new TodoService();
        service.addTask("First task");
        service.addTask("Second task");
        ConsoleUI consoleUI = createConsoleUI(service, "2\n5\n", output);

        consoleUI.run();

        String text = output.toString(StandardCharsets.UTF_8);
        assertTrue(text.contains("ID: 1 | Title: First task | Status: Incomplete"));
        assertTrue(text.contains("ID: 2 | Title: Second task | Status: Incomplete"));
        assertEquals(countOccurrences(text, "1. Add Task"), 2);
    }

    @Test
    public void viewTasksDisplaysMessageWhenTaskListIsEmpty() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ConsoleUI consoleUI = createConsoleUI("2\n5\n", output);

        consoleUI.run();

        String text = output.toString(StandardCharsets.UTF_8);
        assertTrue(text.contains("No tasks found."));
        assertEquals(countOccurrences(text, "No tasks found."), 1);
        assertEquals(countOccurrences(text, "1. Add Task"), 2);
    }

    @Test
    public void completeTaskReportsMissingIdAndPreservesTasks() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        TodoService service = new TodoService();
        service.addTask("Existing task");
        ConsoleUI consoleUI = createConsoleUI(service, "3\n999\n2\n5\n", output);

        consoleUI.run();

        String text = output.toString(StandardCharsets.UTF_8);
        assertEquals(countOccurrences(text, "Task ID not found"), 1);
        assertTrue(text.contains("Enter task ID to complete: Task ID not found" + System.lineSeparator()));
        assertTrue(text.contains("ID: 1 | Title: Existing task | Status: Incomplete"));
        assertEquals(service.getAllTasks().size(), 1);
        assertEquals(countOccurrences(text, "1. Add Task"), 3);
    }

    @Test
    public void deleteTaskReportsMissingIdAndPreservesTasks() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        TodoService service = new TodoService();
        service.addTask("Existing task");
        ConsoleUI consoleUI = createConsoleUI(service, "4\n999\n2\n5\n", output);

        consoleUI.run();

        String text = output.toString(StandardCharsets.UTF_8);
        assertEquals(countOccurrences(text, "Task ID not found"), 1);
        assertTrue(text.contains("Enter task ID to delete: Task ID not found" + System.lineSeparator()));
        assertTrue(text.contains("ID: 1 | Title: Existing task | Status: Incomplete"));
        assertEquals(service.getAllTasks().size(), 1);
        assertEquals(countOccurrences(text, "1. Add Task"), 3);
    }

    @Test
    public void completeTaskRejectsNonNumericIdAndPreservesTasks() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        TodoService service = new TodoService();
        service.addTask("Existing task");
        ConsoleUI consoleUI = createConsoleUI(service, "3\nabc\n2\n5\n", output);

        consoleUI.run();

        String text = output.toString(StandardCharsets.UTF_8);
        assertEquals(countOccurrences(text, "Invalid task ID. Please enter a number."), 1);
        assertEquals(countOccurrences(text, "Task ID not found"), 0);
        assertTrue(text.contains("ID: 1 | Title: Existing task | Status: Incomplete"));
        assertEquals(service.getAllTasks().size(), 1);
        assertEquals(countOccurrences(text, "1. Add Task"), 3);
    }

    @Test
    public void deleteTaskRejectsNonNumericIdAndPreservesTasks() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        TodoService service = new TodoService();
        service.addTask("Existing task");
        ConsoleUI consoleUI = createConsoleUI(service, "4\nabc\n2\n5\n", output);

        consoleUI.run();

        String text = output.toString(StandardCharsets.UTF_8);
        assertEquals(countOccurrences(text, "Invalid task ID. Please enter a number."), 1);
        assertEquals(countOccurrences(text, "Task ID not found"), 0);
        assertTrue(text.contains("ID: 1 | Title: Existing task | Status: Incomplete"));
        assertEquals(service.getAllTasks().size(), 1);
        assertEquals(countOccurrences(text, "1. Add Task"), 3);
    }

    private ConsoleUI createConsoleUI(String selections, ByteArrayOutputStream output) {
        Scanner input = new Scanner(selections);
        PrintStream outputStream = new PrintStream(output, true, StandardCharsets.UTF_8);
        return new ConsoleUI(input, outputStream);
    }

    private ConsoleUI createConsoleUI(TodoService service, String selections, ByteArrayOutputStream output) {
        Scanner input = new Scanner(selections);
        PrintStream outputStream = new PrintStream(output, true, StandardCharsets.UTF_8);
        return new ConsoleUI(service, input, outputStream);
    }

    private int countOccurrences(String text, String value) {
        int count = 0;
        int index = 0;

        while ((index = text.indexOf(value, index)) != -1) {
            count++;
            index += value.length();
        }

        return count;
    }
}
