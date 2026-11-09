package org.capstone.service;

import org.capstone.model.Task;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertThrows;

public class TodoServiceTest {

    @Test
    public void addTaskStoresTrimmedTitleAndStartsIncomplete() {
        TodoService service = new TodoService();

        Task task = service.addTask("  Prepare report  ");

        assertEquals(task.getTitle(), "Prepare report");
        assertFalse(task.isCompleted());
    }

    @Test
    public void addTaskAssignsUniqueIncreasingIds() {
        TodoService service = new TodoService();

        Task firstTask = service.addTask("First");
        Task secondTask = service.addTask("Second");

        assertEquals(firstTask.getId(), 1);
        assertEquals(secondTask.getId(), 2);
    }

    @Test
    public void addTaskRejectsEmptyTitleWithoutCreatingTask() {
        assertInvalidTitleDoesNotConsumeTaskId("");
    }

    @Test
    public void addTaskRejectsWhitespaceOnlyTitleWithoutCreatingTask() {
        assertInvalidTitleDoesNotConsumeTaskId(" \t  ");
    }

    @Test
    public void addTaskRejectsNullTitleWithoutCreatingTask() {
        assertInvalidTitleDoesNotConsumeTaskId(null);
    }

    @Test
    public void getAllTasksReturnsEmptyListWhenNoTasksExist() {
        TodoService service = new TodoService();

        assertEquals(service.getAllTasks().size(), 0);
    }

    @Test
    public void getAllTasksReturnsAllCurrentTasksWithoutChangingThem() {
        TodoService service = new TodoService();
        Task firstTask = service.addTask("First");
        Task secondTask = service.addTask("Second");

        List<Task> tasks = service.getAllTasks();

        assertEquals(tasks.size(), 2);
        assertEquals(tasks.get(0).getId(), firstTask.getId());
        assertEquals(tasks.get(0).getTitle(), firstTask.getTitle());
        assertFalse(tasks.get(0).isCompleted());
        assertEquals(tasks.get(1).getId(), secondTask.getId());
        assertEquals(tasks.get(1).getTitle(), secondTask.getTitle());
        assertFalse(tasks.get(1).isCompleted());
        assertEquals(service.getAllTasks().size(), 2);
    }

    @Test
    public void getAllTasksReturnsUnmodifiableSnapshot() {
        TodoService service = new TodoService();
        service.addTask("First");

        List<Task> tasks = service.getAllTasks();
        service.addTask("Second");

        assertEquals(tasks.size(), 1);
        assertThrows(UnsupportedOperationException.class, () -> tasks.add(new Task(3, "Third")));
        assertEquals(service.getAllTasks().size(), 2);
    }

    @Test
    public void completeTaskMarksOnlyTheMatchingTaskComplete() {
        TodoService service = new TodoService();
        Task firstTask = service.addTask("First");
        Task secondTask = service.addTask("Second");

        assertTrue(service.completeTask(secondTask.getId()));

        assertFalse(firstTask.isCompleted());
        assertTrue(secondTask.isCompleted());
    }

    @Test
    public void completeTaskReturnsFalseAndDoesNotChangeTasksWhenIdDoesNotExist() {
        TodoService service = new TodoService();
        Task task = service.addTask("Existing");

        assertFalse(service.completeTask(999));

        assertFalse(task.isCompleted());
    }

    @Test
    public void deleteTaskRemovesOnlyTheMatchingTask() {
        TodoService service = new TodoService();
        Task firstTask = service.addTask("First");
        Task secondTask = service.addTask("Second");

        assertTrue(service.deleteTask(firstTask.getId()));

        List<Task> remainingTasks = service.getAllTasks();
        assertEquals(remainingTasks.size(), 1);
        assertEquals(remainingTasks.get(0).getId(), secondTask.getId());
        assertEquals(remainingTasks.get(0).getTitle(), secondTask.getTitle());
        assertEquals(remainingTasks.get(0).isCompleted(), secondTask.isCompleted());
    }

    @Test
    public void deleteTaskReturnsFalseAndLeavesTasksUnchangedWhenIdDoesNotExist() {
        TodoService service = new TodoService();
        Task task = service.addTask("Existing");

        assertFalse(service.deleteTask(999));

        List<Task> remainingTasks = service.getAllTasks();
        assertEquals(remainingTasks.size(), 1);
        assertEquals(remainingTasks.get(0).getId(), task.getId());
        assertEquals(remainingTasks.get(0).getTitle(), task.getTitle());
        assertEquals(remainingTasks.get(0).isCompleted(), task.isCompleted());
    }

    @Test
    public void deleteTaskDoesNotReuseDeletedTaskId() {
        TodoService service = new TodoService();
        Task deletedTask = service.addTask("To delete");
        Task remainingTask = service.addTask("Keep");

        assertTrue(service.deleteTask(deletedTask.getId()));
        Task nextTask = service.addTask("Next");

        assertEquals(remainingTask.getId(), 2);
        assertEquals(nextTask.getId(), 3);
    }

    private void assertInvalidTitleDoesNotConsumeTaskId(String invalidTitle) {
        TodoService service = new TodoService();

        assertThrows(IllegalArgumentException.class, () -> service.addTask(invalidTitle));

        Task validTask = service.addTask("Valid task");
        assertEquals(validTask.getId(), 1);
        assertEquals(validTask.getTitle(), "Valid task");
        assertFalse(validTask.isCompleted());
    }
}
