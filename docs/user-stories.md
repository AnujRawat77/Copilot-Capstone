# User Stories — Java To-Do Application

## Baseline and Provenance

This document records the derived user-story baseline ratified by the developer on **2026-11-09**. The stories were derived from the functional requirements in [requirements.md](requirements.md) and the approved project baseline.

The prior historical user-stories source artifact was not recovered from the available workspace or local Git history. Therefore, these stories are recorded as a newly ratified derived baseline; they are not represented as the contents of a recovered artifact or as having been historically approved on 2026-11-04. The approval statement already present in `requirements.md` remains that document's own historical record and is not changed by this artifact.

This derived baseline does not amend the requirements. The approved technical baseline remains Java 21, Maven, TestNG, selective Lombok, a terminal interface, layered responsibilities, and in-memory task storage.

## Stories

### US-001: Add a valid task

As a user, I want to add a task with a nonempty title so that I can track work I need to do.

**Acceptance criteria**

- A valid title creates a task with a unique ID.
- The trimmed title is stored, and successful task creation is confirmed to the user.
- A new task starts incomplete.
- A blank or whitespace-only title is rejected after trimming, and the user is prompted to retry.

**Traceability:** FR-001, FR-007

### US-002: View all tasks

As a user, I want to view the tasks held in memory and their completion status so that I can see my current work.

**Acceptance criteria**

- When tasks exist, the application displays each task's ID, title, and completion status.
- When no tasks exist, the application displays an empty-list message.
- Viewing the list does not mutate task data.

**Traceability:** FR-002

### US-003: Complete a task

As a user, I want to mark a task complete by its ID so that its status reflects the work I finished.

**Acceptance criteria**

- The selected task's status is changed to complete.
- The application confirms the operation.

**Traceability:** FR-003

### US-004: Delete a task

As a user, I want to delete a task by its ID so that it is removed from my list.

**Acceptance criteria**

- The identified task is removed from the list.
- The application confirms the deletion.

**Traceability:** FR-004

### US-005: Handle a nonexistent numeric task ID

As a user, I want a clear response when I enter a numeric ID that does not exist so that I can correct my request and continue.

**Acceptance criteria**

- The application displays the exact message `Task ID not found`.
- The application remains operational and allows continued interaction.
- No unrelated task is mutated.

**Traceability:** FR-006

### US-006: Handle a nonnumeric task ID

As a user, I want a helpful error when I enter a nonnumeric ID for Complete or Delete so that I can recover without changing my task data.

**Acceptance criteria**

- The application displays a helpful input error.
- No task data is changed.
- The application returns to the menu.

**Traceability:** FR-006, FR-008

### US-007: Use the interactive menu

As a user, I want a five-option menu that routes my selections and repeats until I exit so that I can perform task operations in one session.

**Acceptance criteria**

- The menu provides Add Task, View All Tasks, Mark Task Complete, Delete Task, and Exit.
- Each valid selection routes to its corresponding operation.
- An invalid choice is handled without ending the application.
- The menu is displayed again after operations and handled errors.
- The menu continues until the user selects Exit, which terminates normally.

**Traceability:** FR-005, FR-008
