# Software Architecture Document

## Agentic SDLC — Java To-Do Application

| Document Information | Value |
|---|---|
| Project | Agentic SDLC — Java To-Do Application |
| Document | Software Architecture Document |
| Version | 1.0 |
| Status | Approved by developer on 2026-11-04 |
| Language | Java 21 |
| Build Tool | Maven |
| Testing Framework | TestNG |
| Boilerplate Reduction | Lombok |
| Interface | Terminal |
| Storage | In-memory |

---

# 1. Introduction

## 1.1 Purpose

This document describes the architecture of the Java To-Do application.

It defines the major components, their responsibilities, relationships, data structures, interactions, error-handling approach, and testing strategy.

The architecture is based on the approved requirements baseline and is intended to guide implementation while avoiding unnecessary complexity.

## 1.2 Architectural Goals

The architecture shall:

- Support all approved functional requirements.
- Keep the application small and understandable.
- Separate user interaction from business logic.
- Make business logic testable without interactive console input.
- Handle expected invalid input gracefully.
- Support automated testing using TestNG.
- Use Java 21 and Maven.
- Use Lombok selectively.
- Support the Agentic SDLC demonstration through clear, reviewable implementation boundaries.

## 1.3 Architectural Principles

### Separation of Responsibilities

Each component shall have a focused responsibility.

### Simplicity

The design shall avoid unnecessary frameworks, layers, and abstractions.

### Testability

Business logic shall be testable independently of the console interface.

### Requirements Traceability

Architecture components and decisions shall support the documented functional and non-functional requirements.

### Human Oversight

Architecture changes proposed by agents shall be reviewed and approved by the developer.

---

# 2. System Context

## 2.1 System Overview

The application is a standalone terminal-based To-Do program.

The user interacts with the application through a menu. The menu delegates task operations to a service. The service manages task data in memory.

## 2.2 System Context Diagram

```text
+------------------+
|       User       |
+------------------+
         |
         | Menu selections
         | Task titles
         | Task IDs
         v
+--------------------------+
|   Java To-Do Application |
|                          |
|      ConsoleUI           |
|          |               |
|      TodoService         |
|          |               |
|   In-Memory Task List    |
+--------------------------+
         |
         | Results and messages
         v
+------------------+
|       User       |
+------------------+
```

## 2.3 External Dependencies

The application does not require external services for core functionality.

| Dependency | Role |
|---|---|
| Java 21 | Runtime and language |
| Maven | Build and dependency management |
| TestNG | Automated tests |
| Lombok | Optional boilerplate reduction |
| IntelliJ IDEA | Development environment |
| Git | Version control |
| GitHub Copilot | Agentic SDLC assistance |

---

# 3. Architectural Style

## 3.1 Selected Style

The application shall use a **simple layered design** consisting of:

1. Presentation layer
2. Business logic layer
3. Model layer
4. In-memory data collection

This is a lightweight separation of responsibilities rather than a large enterprise architecture.

## 3.2 Rationale

A layered design is suitable because:

- The application has a small number of operations.
- Console input and output should not be mixed with business rules.
- Task operations need to be tested independently.
- The design is easy for a developer and Copilot agents to understand.
- The design avoids unnecessary framework and infrastructure setup.

## 3.3 Architecture Diagram

```text
+--------------------------------+
|          Main                  |
| Application Composition        |
+--------------------------------+
                 |
                 v
+--------------------------------+
|          ConsoleUI             |
| Menu, Input, Output, Retry     |
+--------------------------------+
                 |
                 v
+--------------------------------+
|          TodoService           |
| Add, List, Complete, Delete    |
| Validation, ID Management      |
+--------------------------------+
                 |
                 v
+--------------------------------+
|       In-Memory Collection     |
|        Collection<Task>        |
+--------------------------------+
                 |
                 v
+--------------------------------+
|             Task               |
| ID, Title, Completion Status   |
+--------------------------------+
```

---

# 4. Package and Directory Structure

## 4.1 Proposed Structure

```text
agentic-todo-java/
├── .github/
│   ├── agents/
│   ├── prompts/
│   ├── instructions/
│   └── skills/
├── docs/
│   ├── requirements.md
│   ├── architecture.md
│   ├── design-review.md
│   ├── implementation-plan.md
│   └── user-stories.md
├── src/
│   ├── main/
│   │   └── java/
│   │       └── org/
│   │           └── capstone/
│   │               ├── Main.java
│   │               ├── model/
│   │               │   └── Task.java
│   │               ├── service/
│   │               │   └── TodoService.java
│   │               └── ui/
│   │                   └── ConsoleUI.java
│   └── test/
│       └── java/
│           └── org/
│               └── capstone/
│                   ├── model/
│                   └── service/
│                       └── TodoServiceTest.java
├── pom.xml
└── README.md
```

The repository already contains `src/main/java/org/capstone/Main.java`; therefore, the recommended root package is `org.capstone`, with `model`, `service`, and `ui` subpackages. The `README.md` entry is optional and deferred; it is not required for the initial implementation.

## 4.2 Package Responsibilities

| Package | Responsibility |
|---|---|
| `org.capstone` | Application entry point |
| `org.capstone.model` | Task data representation |
| `org.capstone.service` | Task-management business logic |
| `org.capstone.ui` | Console interaction |
| Corresponding test packages | Automated verification |

The structure may be adjusted if implementation reveals a clear need, but unnecessary packages shall not be introduced.

---

# 5. Component Design

## 5.1 Main

### Responsibility

`Main` is the application entry point and composition root.

It creates the required application components and starts the user interface.

### Proposed Responsibilities

- Instantiate `TodoService`.
- Instantiate `ConsoleUI`.
- Provide the service to the UI.
- Start the application.

### Design Constraints

- `Main` shall not contain task-management business logic.
- `Main` shall not directly manipulate the task collection.
- `Main` shall not implement menu operations.

### Requirement Traceability

- FR-005: Exit Application
- FR-008: Interactive Menu System
- NFR-001: Simplicity
- NFR-002: Maintainability

---

## 5.2 Task

### Responsibility

`Task` represents one task managed by the application.

### Proposed Fields

| Field | Type | Description |
|---|---|---|
| `id` | `int` | Unique task identifier |
| `title` | `String` | Task title |
| `completed` | `boolean` | Completion status |

### Proposed Behavior

- A task shall contain an ID.
- A task shall contain a valid title.
- A newly created task shall initially be incomplete.
- The completion status may be updated when the task is completed.

### Lombok Usage

Lombok may be used selectively for:

- Getters
- Setters where required
- Constructors where appropriate
- Other repetitive methods if they improve readability

Lombok shall not obscure the task's behavior or introduce unnecessary generated methods.

### Requirement Traceability

- FR-001: Add Task
- FR-002: View All Tasks
- FR-003: Mark Task Complete
- FR-004: Delete Task
- NFR-007: Code Quality

---

## 5.3 TodoService

### Responsibility

`TodoService` implements the task-management business rules.

It manages the in-memory task collection and exposes operations needed by the UI.

### Proposed Internal State

- In-memory task collection
- Next task ID counter

### Proposed Operations

| Operation | Responsibility |
|---|---|
| Add task | Validate title, create task, assign ID |
| Get all tasks | Return the current task list |
| Complete task | Locate task and update completion status |
| Delete task | Locate task and remove it |
| Find task | Locate a task by ID |
| Validate title | Check title after trimming whitespace |

### Design Constraints

- The service shall not read from `System.in`.
- The service shall not display console messages.
- The service shall not depend on `ConsoleUI`.
- The service shall own task validation and ID assignment.
- The service shall handle task lookup consistently.
- The service shall not silently modify unrelated tasks.

### Error Handling Contract

The service shall communicate expected business errors in a predictable manner.

The implementation may use a small custom exception or a simple result-based approach. The final choice shall be made during implementation planning and documented if it materially changes the architecture.

The UI shall be responsible for converting service outcomes into user-facing messages.

### Requirement Traceability

- FR-001: Add Task
- FR-002: View All Tasks
- FR-003: Mark Task Complete
- FR-004: Delete Task
- FR-006: Invalid ID Handling
- FR-007: Input Validation
- NFR-002: Maintainability
- NFR-003: Testability
- NFR-004: Reliability

---

## 5.4 ConsoleUI

### Responsibility

`ConsoleUI` manages terminal interaction.

### Proposed Responsibilities

- Display the interactive menu.
- Read menu selections.
- Read task titles.
- Read task IDs.
- Invoke service operations.
- Display task information.
- Display success and error messages.
- Prompt again after invalid task-title input.
- Continue the menu after handled errors.
- Exit when the user selects Exit.

### Design Constraints

- The UI shall not directly manipulate the task collection.
- The UI shall not assign task IDs.
- The UI shall not duplicate business validation rules.
- The UI shall handle user interaction and presentation.
- The UI shall keep the menu loop active until Exit is selected.

### Requirement Traceability

- FR-001 through FR-008
- NFR-004: Reliability
- NFR-005: Usability

---

# 6. Data Design

## 6.1 Task Data Model

The application shall use a simple `Task` object.

```text
Task
----
id: int
title: String
completed: boolean
```

## 6.2 In-Memory Collection

The service shall maintain a collection of tasks.

The implementation may use a Java collection such as `ArrayList<Task>`.

### Rationale

- The application has no persistence requirement.
- The expected data volume is small.
- The collection is straightforward to understand.
- The collection is sufficient for add, list, complete, and delete operations.

## 6.3 Task ID Strategy

Task IDs shall be assigned by the service.

The proposed strategy is a monotonically increasing integer counter.

### Rules

- The first task shall receive an initial valid ID.
- Each subsequently created task shall receive a new ID.
- IDs shall not be reused during the same application run.
- Deleting a task shall not reset the ID counter.
- The service shall use the ID to locate tasks for completion and deletion.

## 6.4 Data Lifetime

Task data shall exist only while the application is running.

When the application exits, the in-memory collection is discarded.

This behavior is intentional and consistent with the approved scope.

---

# 7. Application Control Flow

## 7.1 Startup Flow

```text
Main starts
    |
    v
Create TodoService
    |
    v
Create ConsoleUI
    |
    v
Start menu loop
```

## 7.2 Menu Flow

```text
Display menu
    |
    v
Read user selection
    |
    v
Validate selection
    |
    +---- Invalid ----> Display error
    |                       |
    |                       v
    |                   Display menu
    |
    +---- Add Task ----> Prompt for title
    |
    +---- View Tasks --> Retrieve and display tasks
    |
    +---- Complete ----> Prompt for task ID
    |
    +---- Delete ------> Prompt for task ID
    |
    +---- Exit --------> Terminate application
```

## 7.3 Add Task Flow

```text
User selects Add Task
    |
    v
Prompt for task title
    |
    v
TodoService validates title
    |
    +---- Invalid ----> Display validation error
    |                       |
    |                       v
    |                  Prompt for retry
    |
    +---- Valid ------> Create task
                            |
                            v
                       Assign task ID
                            |
                            v
                       Mark incomplete
                            |
                            v
                       Display confirmation
```

## 7.4 Complete Task Flow

```text
User selects Complete Task
    |
    v
Prompt for task ID
    |
    v
TodoService searches for task
    |
    +---- Not found ---> Display "Task ID not found"
    |                         |
    |                         v
    |                    Return to menu
    |
    +---- Found -------> Mark task completed
                              |
                              v
                         Display confirmation
                              |
                              v
                         Return to menu
```

## 7.5 Delete Task Flow

```text
User selects Delete Task
    |
    v
Prompt for task ID
    |
    v
TodoService searches for task
    |
    +---- Not found ---> Display "Task ID not found"
    |                         |
    |                         v
    |                    Return to menu
    |
    +---- Found -------> Remove task
                              |
                              v
                         Display confirmation
                              |
                              v
                         Return to menu
```

---

# 8. Error Handling Design

## 8.1 Invalid Task Title

The service shall reject titles that are empty after trimming.

The UI shall:

1. Display a helpful validation message.
2. Prompt the user to retry.
3. Continue until a valid title is entered, subject to normal application termination behavior.

No task shall be created from invalid input.

## 8.2 Invalid Task ID

When a numeric task ID does not identify an existing task:

- The service shall indicate that the task was not found.
- The UI shall display the exact message `Task ID not found`.
- The application shall remain operational.
- The menu shall continue after the handled error.

When the user enters a non-numeric task ID during completion or deletion:

- The UI shall display a helpful input error.
- No task data shall be added, changed, or deleted.
- The application shall return to the menu and remain operational.

## 8.3 Invalid Menu Input

When a menu selection is unsupported:

- The UI shall display a helpful message.
- The application shall not terminate.
- The menu shall be displayed again.

## 8.4 Unexpected Errors

Unexpected programming errors shall not be silently ignored.

During development and testing, unexpected errors should remain visible so they can be diagnosed and corrected.

---

# 9. Testing Architecture

## 9.1 Testing Approach

Testing shall focus first on the service layer because it contains the core business rules.

TestNG shall be used for automated tests.

## 9.2 TodoService Unit Tests

The service test suite shall cover:

- Adding a valid task
- Initial completion status
- Unique task ID assignment
- Listing an empty task collection
- Listing a populated task collection
- Rejecting empty task titles
- Rejecting whitespace-only task titles
- Trimming task titles
- Completing an existing task
- Handling completion of a non-existent task
- Deleting an existing task
- Handling deletion of a non-existent task
- Ensuring deletion does not affect unrelated tasks

## 9.3 ConsoleUI Tests

Console interaction tests may use controlled input and captured output where practical.

They should cover:

- Menu display
- Menu selection routing
- Invalid menu input
- Task-title retry behavior
- Invalid task-ID message
- Non-numeric task-ID input during completion and deletion, including a helpful error, unchanged task data, and return to the menu
- Returning to the menu after handled errors
- Exit behavior

## 9.4 Build and Test Execution

The project shall support execution through Maven.

The expected verification command is:

```bash
mvn test
```

The Testing Agent shall report whether the command was actually executed and whether it passed. The Orchestrator shall coordinate final verification using those results and the Code Review findings; a separate Verification Agent is not required.

---

# 10. Architecture Decisions

## AD-001: Standalone Console Application

**Decision:** Use a terminal-based Java application.

**Rationale:** The app is intentionally basic and the capstone focus is the Agentic SDLC.

**Consequences:** No GUI or web framework is required.

## AD-002: Simple Layered Design

**Decision:** Separate entry point, console UI, business logic, and model.

**Rationale:** Supports maintainability and testability without introducing unnecessary architecture.

**Consequences:** The service can be tested independently of the console.

## AD-003: In-Memory Storage

**Decision:** Use an in-memory task collection.

**Rationale:** Persistence is outside the approved scope.

**Consequences:** Tasks are lost after application termination.

## AD-004: Maven

**Decision:** Use Maven for builds and dependencies.

**Rationale:** Provides a repeatable build and test execution process.

## AD-005: TestNG

**Decision:** Use TestNG for automated tests.

**Rationale:** TestNG is the selected test framework for the capstone.

## AD-006: Selective Lombok

**Decision:** Use Lombok where it improves readability and reduces boilerplate.

**Rationale:** Avoids repetitive code while keeping the model understandable.

**Consequences:** Lombok annotation processing must be configured in the development environment.

## AD-007: Service-Owned Validation

**Decision:** Validate task titles in `TodoService`.

**Rationale:** Business rules should remain consistent regardless of the caller.

**Consequences:** The UI handles retries and presentation; the service enforces validity.

## AD-008: Human-Reviewed Architecture Changes

**Decision:** Architecture changes proposed by Copilot agents require developer review.

**Rationale:** Preserves human oversight and prevents unsupported scope expansion.

---

# 11. Architecture-to-Requirement Traceability

| Component | Requirements Supported |
|---|---|
| `Main` | FR-005, FR-008 |
| `ConsoleUI` | FR-001 through FR-008 |
| `TodoService` | FR-001, FR-002, FR-003, FR-004, FR-006, FR-007 |
| `Task` | FR-001, FR-002, FR-003, FR-004 |
| In-memory collection | FR-001, FR-002, FR-003, FR-004 |
| TestNG test suite | NFR-003, NFR-004, NFR-007 |
| Maven build | TR-002, TR-003 |
| Copilot workflow configuration | Agentic SDLC requirements |

---

# 12. Known Limitations

- Tasks are not persisted after application termination.
- The application is single-user.
- The application has no GUI.
- The application has no database.
- The application does not support task search or filtering.
- The application does not support priorities, categories, or due dates.

These limitations are accepted for the initial capstone scope.

---

# 13. Architecture Approval

## Approval Criteria

The architecture is ready for implementation planning when:

- It supports all approved functional requirements.
- Components have clear responsibilities.
- Business logic can be tested independently.
- Error handling is defined.
- Data structures and task ID behavior are documented.
- No unnecessary application complexity has been introduced.
- The developer has reviewed the architecture.

**Current Status (reconciled 2026-11-09):** Architecture baseline approved by the developer on 2026-11-04. The implementation plan was approved by the developer on 2026-11-05, and TASK-12 was completed on 2026-11-09. The previously pending independent Design Review subsequently assessed the implemented source, requirements, and tests. Its focused follow-up recommended DR-019 and DR-020 resolved; DR-021 was addressed by the documentation update. The human formally approved the design on 2026-11-09 and authorized progression to PR review, separately from agent recommendations. See [design-review.md](design-review.md), section 14. This does not constitute formal capstone closure approval.

## 13.1 Approval Record

Artifact

Approver

Decision

Date

Architecture baseline (`docs/architecture.md`), including the `org.capstone` package layout and in-memory storage

Developer

Approved

2026-11-04