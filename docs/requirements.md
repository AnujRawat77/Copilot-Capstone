# Software Requirements Specification
## Agentic SDLC — Java To-Do Application

| Document Information | Value |
|---|---|
| Project Name | Agentic SDLC — Java To-Do Application |
| Document | Software Requirements Specification |
| Version | 1.0 |
| Status | Initial Baseline — Pending Review |
| Application Type | Terminal-based Java application |
| Language | Java 21 |
| Build Tool | Maven |
| Testing Framework | TestNG |
| Boilerplate Reduction | Lombok |
| Storage | In-memory |
| Primary Objective | Demonstrate an Agentic Software Development Life Cycle |

---

# 1. Introduction

## 1.1 Purpose

This document defines the functional and non-functional requirements for the Java To-Do application developed as part of the Agentic SDLC capstone project.

The document establishes a common understanding of the application's scope, expected behavior, technical constraints, and acceptance criteria.

It will serve as the primary requirements baseline for:

- Architecture design
- Design review
- Implementation planning
- Java implementation
- Automated testing
- Code review
- Verification
- Pull request preparation

The requirements are intentionally detailed enough to support traceability from the original requirement through implementation and testing.

## 1.2 Project Background

The capstone demonstrates how GitHub Copilot and specialized agents can assist throughout the Software Development Life Cycle.

The application itself will remain small. The primary focus is the agentic workflow used to develop it.

The workflow is expected to support activities such as:

1. Requirements analysis
2. Architecture design
3. Independent design review
4. Implementation planning
5. Implementation
6. Automated testing
7. Code review
8. Verification
9. Pull request preparation

Human approval will be required at selected workflow checkpoints.

## 1.3 Project Objectives

The project shall:

- Demonstrate a structured Agentic SDLC.
- Use specialized agents with clearly defined responsibilities.
- Maintain SDLC artifacts throughout development.
- Include human approval at important decision points.
- Demonstrate requirements-driven implementation.
- Demonstrate automated testing using TestNG.
- Demonstrate independent code review and verification.
- Keep the application simple enough that the workflow remains the primary focus.
- Avoid unnecessary application features and excessive Copilot usage.

## 1.4 Intended Audience

This document is intended for:

- The capstone developer
- GitHub Copilot agents participating in the SDLC
- Reviewers evaluating the capstone
- Testers verifying application behavior
- Anyone reviewing the project's requirements and implementation decisions

## 1.5 Definitions and Terminology

| Term | Definition |
|---|---|
| Agentic SDLC | A software development workflow in which AI agents assist with defined SDLC activities |
| Orchestrator | Agent responsible for coordinating workflow stages and tracking progress |
| Specialist Agent | Agent responsible for a specific SDLC activity |
| Human Approval | Explicit confirmation from the developer before a workflow proceeds past a designated checkpoint |
| Task | An item created and managed by the To-Do application |
| Task ID | Unique identifier assigned to a task during an application run |
| In-memory Storage | Data held in application memory and not retained after termination |
| Functional Requirement | A capability or behavior the system must provide |
| Non-functional Requirement | A quality attribute or constraint the system must satisfy |
| Acceptance Criteria | Conditions used to determine whether a requirement has been satisfied |
| Traceability | Ability to link requirements to design, implementation, and verification |

---

# 2. Project Scope

## 2.1 Application Overview

The application shall provide basic task-management functionality through an interactive terminal menu.

A user shall be able to:

- Add a task
- View all tasks
- Mark a task as completed
- Delete a task
- Exit the application

The application shall store tasks in memory during execution.

## 2.2 In-Scope Features

The following capabilities are included in the initial application version:

| Feature ID | Feature | Description |
|---|---|---|
| F-01 | Add Task | Create a task using a valid task title |
| F-02 | View Tasks | Display all tasks and their current status |
| F-03 | Complete Task | Mark an existing task as completed |
| F-04 | Delete Task | Remove an existing task |
| F-05 | Interactive Menu | Display and process available menu options |
| F-06 | Input Validation | Reject invalid task titles and unsupported menu input |
| F-07 | Invalid ID Handling | Handle references to non-existent task IDs |
| F-08 | Graceful Exit | Terminate the application when the user selects Exit |

## 2.3 Out-of-Scope Features

The following features are excluded from the initial version:

- User registration or authentication
- Authorization and role management
- Multiple users
- Database integration
- Persistent file storage
- Web interface
- Mobile application
- REST API
- Notifications or reminders
- Task priorities
- Task categories or tags
- Due dates
- Task search
- Task collaboration
- Cloud deployment
- External service integrations

These features shall not be added unless the approved project scope changes.

## 2.4 Project Boundaries

The application is a standalone Java console program.

The capstone's agentic workflow may use GitHub and GitHub Copilot capabilities, but the To-Do application itself shall not require a network connection to perform its core task-management operations.

---

# 3. Stakeholders and User Characteristics

## 3.1 Primary User

The primary user is an individual interacting with the application through a terminal.

The user is expected to:

- Read the displayed menu
- Enter a menu option
- Provide task titles when requested
- Provide task IDs when requested
- Read success or error messages
- Continue using the application until selecting Exit

## 3.2 Developer

The developer is responsible for:

- Reviewing and approving requirements
- Reviewing architecture and design decisions
- Approving implementation plans
- Supervising Copilot-assisted implementation
- Reviewing generated code
- Running automated tests
- Approving workflow transitions where required

## 3.3 Copilot Agents

Copilot agents shall assist with designated SDLC activities.

Agents shall use the approved project artifacts as context and shall not independently expand the application scope without developer approval.

---

# 4. Functional Requirements

## FR-001: Add Task

### Description

The application shall allow the user to create a task by providing a task title.

### Inputs

- Task title

### Preconditions

- The application is running.
- The user selects the Add Task menu option.

### Expected Behavior

1. The application prompts the user to enter a task title.
2. The application validates the title.
3. If valid, the application creates a task.
4. The task receives a unique identifier.
5. The task is initially marked as incomplete.
6. The application confirms that the task was added.

### Acceptance Criteria

- **AC-001.1:** The application prompts the user for a task title.
- **AC-001.2:** A valid task title results in a new task.
- **AC-001.3:** A newly created task has a unique task ID.
- **AC-001.4:** A newly created task is initially incomplete.
- **AC-001.5:** The application displays a confirmation after successful creation.
- **AC-001.6:** An empty task title is rejected.
- **AC-001.7:** A whitespace-only task title is rejected.
- **AC-001.8:** The application does not create a task when title validation fails.

### Related Requirements

- FR-007: Input Validation
- FR-008: Interactive Menu System

---

## FR-002: View All Tasks

### Description

The application shall allow the user to view all tasks currently stored in memory.

### Preconditions

- The application is running.
- The user selects the View All Tasks menu option.

### Expected Behavior

1. The application retrieves the current task list.
2. If tasks exist, the application displays each task.
3. If no tasks exist, the application displays an appropriate message.

### Acceptance Criteria

- **AC-002.1:** The application displays all tasks currently stored.
- **AC-002.2:** Each task displays its task ID.
- **AC-002.3:** Each task displays its title.
- **AC-002.4:** Each task displays its completion status.
- **AC-002.5:** Tasks are displayed in a consistent and understandable format.
- **AC-002.6:** If no tasks exist, the application displays a message indicating that the task list is empty.
- **AC-002.7:** Viewing tasks does not modify task data.

### Related Requirements

- FR-008: Interactive Menu System

---

## FR-003: Mark Task Complete

### Description

The application shall allow the user to mark an existing task as completed.

### Inputs

- Task ID

### Preconditions

- The application is running.
- The user selects the Mark Task Complete menu option.

### Expected Behavior

1. The application prompts the user for a task ID.
2. The application identifies the corresponding task.
3. If the task exists, its completion status is updated.
4. The application confirms the operation.
5. If the task does not exist, the application handles the invalid ID according to FR-006.

### Acceptance Criteria

- **AC-003.1:** The application prompts the user for a task ID.
- **AC-003.2:** A valid task ID identifies the correct task.
- **AC-003.3:** The identified task is marked as completed.
- **AC-003.4:** The application displays a confirmation after successful completion.
- **AC-003.5:** An invalid task ID does not terminate the application.
- **AC-003.6:** The task list reflects the updated completion status.

### Related Requirements

- FR-006: Invalid ID Handling
- FR-008: Interactive Menu System

---

## FR-004: Delete Task

### Description

The application shall allow the user to delete an existing task.

### Inputs

- Task ID

### Preconditions

- The application is running.
- The user selects the Delete Task menu option.

### Expected Behavior

1. The application prompts the user for a task ID.
2. The application identifies the corresponding task.
3. If the task exists, it is removed from the task collection.
4. The application confirms the deletion.
5. If the task does not exist, the application handles the invalid ID according to FR-006.

### Acceptance Criteria

- **AC-004.1:** The application prompts the user for a task ID.
- **AC-004.2:** A valid task ID identifies the correct task.
- **AC-004.3:** The identified task is removed from the task collection.
- **AC-004.4:** The application displays a confirmation after successful deletion.
- **AC-004.5:** A deleted task no longer appears in the task list.
- **AC-004.6:** An invalid task ID does not terminate the application.

### Related Requirements

- FR-006: Invalid ID Handling
- FR-008: Interactive Menu System

---

## FR-005: Exit Application

### Description

The application shall allow the user to exit through the interactive menu.

### Preconditions

- The application is running.

### Expected Behavior

1. The application displays an Exit option.
2. The user selects Exit.
3. The application terminates gracefully.

### Acceptance Criteria

- **AC-005.1:** The menu displays an Exit option.
- **AC-005.2:** Selecting Exit terminates the application.
- **AC-005.3:** The application does not continue displaying the menu after Exit is selected.
- **AC-005.4:** The application exits without an unhandled exception during normal termination.

### Related Requirements

- FR-008: Interactive Menu System

---

## FR-006: Invalid ID Handling

### Description

The application shall handle task IDs that do not correspond to an existing task.

### Expected Behavior

When the user provides a non-existent task ID during a task operation, the application shall display the required error message and remain operational.

### Acceptance Criteria

- **AC-006.1:** The application identifies when a requested task ID does not exist.
- **AC-006.2:** The application displays the exact error message:

  `Task ID not found`

- **AC-006.3:** The application does not terminate after displaying the error.
- **AC-006.4:** The user can continue interacting with the application after the error.
- **AC-006.5:** An invalid task ID does not modify unrelated tasks.
- **AC-006.6:** The invalid-ID behavior applies to task operations that require an existing task ID, including completing and deleting tasks.

### Related Requirements

- FR-003: Mark Task Complete
- FR-004: Delete Task
- FR-008: Interactive Menu System

---

## FR-007: Input Validation

### Description

The application shall validate task-name input before creating a task.

### Expected Behavior

1. The application receives a task title.
2. The title is checked after trimming leading and trailing whitespace.
3. If the title is empty after trimming, the application rejects it.
4. The application displays a validation error.
5. The application prompts the user to retry.
6. A task is created only after a valid title is provided.

### Acceptance Criteria

- **AC-007.1:** Task titles are validated after trimming leading and trailing whitespace.
- **AC-007.2:** An empty task title is rejected.
- **AC-007.3:** A whitespace-only task title is rejected.
- **AC-007.4:** The application displays a helpful validation error.
- **AC-007.5:** The application prompts the user to retry after validation failure.
- **AC-007.6:** No task is created from invalid input.
- **AC-007.7:** A valid title is accepted.
- **AC-007.8:** Leading and trailing whitespace is not retained in the stored task title.
- **AC-007.9:** Validation failure does not terminate the application.

### Related Requirements

- FR-001: Add Task
- FR-008: Interactive Menu System

---

## FR-008: Interactive Menu System

### Description

The application shall provide an interactive menu through which the user accesses the available task operations.

### Required Menu Options

1. Add task
2. View all tasks
3. Mark task complete
4. Delete task
5. Exit application

### Expected Behavior

1. The application displays the menu.
2. The user enters a menu selection.
3. The application processes the selected operation.
4. After the operation completes, the menu is displayed again.
5. The menu continues looping until the user selects Exit.

### Acceptance Criteria

- **AC-008.1:** The application displays all five required menu options.
- **AC-008.2:** The application accepts user menu input.
- **AC-008.3:** A valid selection invokes the corresponding operation.
- **AC-008.4:** The menu is displayed again after a task operation completes.
- **AC-008.5:** The menu is displayed again after a handled task-ID error.
- **AC-008.6:** Unsupported menu input produces a helpful error message.
- **AC-008.7:** Unsupported menu input does not terminate the application.
- **AC-008.8:** The menu continues looping until the user selects Exit.
- **AC-008.9:** Selecting Exit terminates the menu loop.

### Related Requirements

- FR-001 through FR-007

---

# 5. Non-Functional Requirements

## NFR-001: Simplicity

The application shall remain small and understandable.

### Acceptance Criteria

- The application shall not introduce unnecessary frameworks or architectural layers.
- The implementation shall remain focused on the approved task-management functionality.
- Features outside the approved scope shall not be added without review.

## NFR-002: Maintainability

The source code shall be organized so that responsibilities are clear.

### Acceptance Criteria

- Console interaction shall be separated from task-management logic.
- Task data shall be represented by a dedicated model.
- Class and method names shall communicate their purpose.
- Business rules shall not be unnecessarily duplicated.

## NFR-003: Testability

The task-management logic shall be testable independently of interactive console input.

### Acceptance Criteria

- Core task operations shall be accessible through service methods.
- TestNG shall be used for automated tests.
- Tests shall not require manual console interaction to verify core business logic.
- Test results shall be executable through Maven.

## NFR-004: Reliability

The application shall handle expected invalid input without terminating unexpectedly.

### Acceptance Criteria

- Invalid task titles shall be handled according to FR-007.
- Invalid task IDs shall be handled according to FR-006.
- Unsupported menu options shall be handled according to FR-008.
- Normal user errors shall not cause unhandled application termination.

## NFR-005: Usability

The application shall provide understandable prompts, confirmations, and error messages.

### Acceptance Criteria

- The menu shall clearly identify available options.
- The application shall prompt users for required input.
- Successful operations shall provide confirmation.
- Error messages shall explain the problem in a user-friendly manner.
- The required invalid-ID message shall be displayed exactly as specified.

## NFR-006: Compatibility

The application shall use the agreed development environment.

### Acceptance Criteria

- The application shall compile using Java 21.
- Maven shall manage dependencies and build execution.
- The project shall be compatible with the configured Maven build.
- TestNG tests shall be executable through Maven.

## NFR-007: Code Quality

The implementation shall follow basic Java coding conventions.

### Acceptance Criteria

- Classes and methods shall have clear names.
- Methods shall have focused responsibilities.
- Unnecessary duplication shall be avoided.
- Lombok shall be used selectively.
- Code shall be reviewed before the implementation is considered complete.

## NFR-008: Resource Usage

The application shall not require significant system resources.

### Acceptance Criteria

- The application shall run as a standalone console program.
- The application shall not require a database server.
- The application shall not require external services for core task operations.

## NFR-009: Data Lifetime

The application shall store task data in memory only.

### Acceptance Criteria

- Tasks shall remain available while the application is running.
- Tasks shall not be required to persist after application termination.
- The loss of in-memory data after exit shall be treated as an accepted limitation.

---

# 6. Technical Requirements and Constraints

## 6.1 Technology Requirements

| ID | Requirement |
|---|---|
| TR-001 | The application shall be implemented in Java 21. |
| TR-002 | Maven shall be used as the build and dependency-management tool. |
| TR-003 | TestNG shall be used for automated testing. |
| TR-004 | Lombok may be used to reduce repetitive Java boilerplate. |
| TR-005 | The application shall use a terminal-based interface. |
| TR-006 | The application shall use in-memory task storage. |
| TR-007 | Source code shall be maintained in Git. |
| TR-008 | The project shall be developed and reviewed using IntelliJ IDEA. |

## 6.2 Implementation Constraints

- The application shall not require a database.
- The application shall not require a web framework.
- The application shall not require a graphical interface.
- The application shall not require external APIs.
- The initial implementation shall not include persistent storage.
- The implementation shall follow the approved architecture and implementation plan.

## 6.3 Capstone Constraints

- GitHub Copilot usage should be focused and credit-conscious.
- Agents should be used for meaningful SDLC activities.
- Human approval shall remain part of the workflow.
- The workflow shall not rely solely on generated claims of success.
- Verification shall include actual build and test execution where applicable.

---

# 7. Assumptions and Dependencies

## 7.1 Assumptions

- The application is intended for demonstration and learning.
- One user operates the application at a time.
- Tasks do not need to persist after application termination.
- Task titles are simple text values.
- Task IDs are integer values assigned by the application.
- The user interacts with the application through a terminal.
- The initial application does not require concurrent task operations.
- The capstone's primary objective is demonstrating the Agentic SDLC.

## 7.2 Dependencies

| Dependency | Purpose |
|---|---|
| Java 21 | Application implementation and execution |
| Maven | Build and dependency management |
| TestNG | Automated testing |
| Lombok | Optional reduction of boilerplate |
| IntelliJ IDEA | Development environment |
| Git | Version control |
| GitHub | Repository and workflow demonstration |
| GitHub Copilot | Agentic SDLC assistance |

---

# 8. Risks and Mitigations

| Risk ID | Risk | Impact | Mitigation |
|---|---|---|---|
| R-001 | Application scope grows unnecessarily | Increased effort and reduced focus on Agentic SDLC | Keep features limited to the approved scope |
| R-002 | Copilot usage exceeds available credits | Workflow development may be interrupted | Use focused prompts and reusable project instructions |
| R-003 | Business logic becomes coupled to console input | Difficult automated testing | Keep task operations in a separate service |
| R-004 | Invalid input causes application termination | Poor user experience and failed requirements | Define and test input-handling behavior |
| R-005 | Requirements are not traced to tests | Difficult to demonstrate verification | Maintain requirement-to-test traceability |
| R-006 | Agents make unsupported assumptions | Incorrect implementation | Require agents to consult approved artifacts and ask for clarification |
| R-007 | Agents proceed without approval | Reduced human control | Define explicit approval gates in the workflow |
| R-008 | Generated code is accepted without verification | Defects may remain | Run Maven tests and conduct independent code review |

---

# 9. Requirement Traceability Matrix

This matrix links functional requirements to the primary verification activities.

| Requirement | Primary Verification |
|---|---|
| FR-001: Add Task | Test valid task creation, title validation, initial status, and ID assignment |
| FR-002: View All Tasks | Test empty and populated task lists and displayed task information |
| FR-003: Mark Task Complete | Test completion of an existing task and invalid task ID behavior |
| FR-004: Delete Task | Test deletion of an existing task and invalid task ID behavior |
| FR-005: Exit Application | Test that selecting Exit terminates the menu loop |
| FR-006: Invalid ID Handling | Test exact error message and continued application operation |
| FR-007: Input Validation | Test empty, whitespace-only, and valid task titles; verify retry behavior |
| FR-008: Interactive Menu System | Test menu options, operation routing, looping, and invalid menu input |

Detailed test cases shall be defined during implementation and verification planning.

---

# 10. Acceptance and Approval

## 10.1 Requirements Acceptance

The requirements baseline shall be considered ready for architecture and implementation planning when:

- All in-scope features are documented.
- Functional requirements have acceptance criteria.
- Non-functional requirements are defined.
- Technical constraints are documented.
- Assumptions and risks are recorded.
- Requirements are traceable to planned verification activities.
- The developer has reviewed and approved the document.

## 10.2 Change Management

Any proposed change to the requirements shall:

1. Identify the requirement being changed.
2. Explain the reason for the change.
3. Identify potential impacts on architecture, implementation, and tests.
4. Be reviewed by the developer.
5. Be reflected in the relevant SDLC artifacts after approval.

## 10.3 Current Status

**Status:** Initial detailed requirements baseline — pending developer review.

This document is the requirements source of truth for the initial Java To-Do application scope.
