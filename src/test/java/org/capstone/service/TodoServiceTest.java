package org.capstone.service;

import org.capstone.model.Task;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotSame;
import static org.testng.Assert.assertSame;
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
    public void addTaskTrimsNullCharactersAndSpacesAroundTitle() {
        TodoService service = new TodoService();

        Task task = service.addTask("\u0000 Prepare report \u0000");

        assertEquals(task.getTitle(), "Prepare report");
        assertEquals(service.getAllTasks().get(0).getTitle(), "Prepare report");
    }

    @Test
    public void addTaskTrimsSpacesAndTabsAroundTitle() {
        TodoService service = new TodoService();

        Task task = service.addTask(" \tPrepare report\t ");

        assertEquals(task.getTitle(), "Prepare report");
        assertEquals(service.getAllTasks().get(0).getTitle(), "Prepare report");
    }

    @Test
    public void addTaskPreservesInteriorTabs() {
        TodoService service = new TodoService();

        Task task = service.addTask("First\tSecond");

        assertEquals(task.getTitle(), "First\tSecond");
        assertEquals(service.getAllTasks().get(0).getTitle(), "First\tSecond");
    }

    @Test
    public void addTaskAcceptsEmSpaceOnlyUnchanged() {
        TodoService service = new TodoService();

        Task task = service.addTask("\u2003");

        assertEquals(task.getTitle(), "\u2003");
        assertEquals(service.getAllTasks().size(), 1);
        assertEquals(service.getAllTasks().get(0).getTitle(), "\u2003");
    }

    @Test
    public void addTaskAcceptsNoBreakSpaceOnlyUnchanged() {
        TodoService service = new TodoService();

        Task task = service.addTask("\u00A0");

        assertEquals(task.getTitle(), "\u00A0");
        assertEquals(service.getAllTasks().size(), 1);
        assertEquals(service.getAllTasks().get(0).getTitle(), "\u00A0");
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

        List<Task> currentTasks = service.getAllTasks();
        assertEquals(currentTasks.size(), 2);
        assertSame(currentTasks.get(0), firstTask);
        assertFalse(currentTasks.get(0).isCompleted());
        assertNotSame(currentTasks.get(1), secondTask);
        assertEquals(currentTasks.get(1).getId(), secondTask.getId());
        assertEquals(currentTasks.get(1).getTitle(), secondTask.getTitle());
        assertTrue(currentTasks.get(1).isCompleted());
        assertFalse(firstTask.isCompleted());
        assertFalse(secondTask.isCompleted());
    }

    @Test
    public void getAllTasksElementsRemainUnchangedAfterCompletion() {
        TodoService service = new TodoService();
        Task addedTask = service.addTask("Existing");
        List<Task> snapshot = service.getAllTasks();
        Task snapshotTask = snapshot.get(0);

        assertTrue(service.completeTask(addedTask.getId()));

        assertEquals(snapshot.size(), 1);
        assertSame(snapshot.get(0), snapshotTask);
        assertFalse(snapshotTask.isCompleted());
        Task currentTask = service.getAllTasks().get(0);
        assertNotSame(currentTask, snapshotTask);
        assertEquals(currentTask.getId(), snapshotTask.getId());
        assertEquals(currentTask.getTitle(), snapshotTask.getTitle());
        assertTrue(currentTask.isCompleted());
    }

    @Test
    public void completingReturnedCopiesDoesNotChangeServiceState() {
        TodoService service = new TodoService();
        Task addedTask = service.addTask("Existing");
        Task listedTask = service.getAllTasks().get(0);

        Task addedTaskCopy = addedTask.completedCopy();
        Task listedTaskCopy = listedTask.completedCopy();

        assertNotSame(addedTaskCopy, addedTask);
        assertNotSame(listedTaskCopy, listedTask);
        assertEquals(addedTaskCopy.getId(), addedTask.getId());
        assertEquals(addedTaskCopy.getTitle(), addedTask.getTitle());
        assertEquals(listedTaskCopy.getId(), listedTask.getId());
        assertEquals(listedTaskCopy.getTitle(), listedTask.getTitle());
        assertTrue(addedTaskCopy.isCompleted());
        assertTrue(listedTaskCopy.isCompleted());
        assertFalse(addedTask.isCompleted());
        assertFalse(listedTask.isCompleted());
        assertFalse(service.getAllTasks().get(0).isCompleted());
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
        assertTrue(service.getAllTasks().isEmpty());

        Task validTask = service.addTask("Valid task");
        assertEquals(validTask.getId(), 1);
        assertEquals(validTask.getTitle(), "Valid task");
        assertFalse(validTask.isCompleted());
    }
}
