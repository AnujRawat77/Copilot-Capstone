# Software Implementation Plan
## Agentic SDLC — Java To-Do Application

| Document Information | Value |
|---|---|
| Project | Agentic SDLC — Java To-Do Application |
| Document | Software Implementation Plan |
| Version | 1.0 |
| Status | Initial Baseline — Pending Approval |
| Language | Java 21 |
| Build Tool | Maven |
| Testing Framework | TestNG |
| Boilerplate Reduction | Lombok |

---

# 1. Introduction

## 1.1 Purpose

This document defines the implementation tasks required to build the Java To-Do application.

The plan translates the approved requirements and architecture into small, ordered, testable work items.

Each task includes:

- Objective
- Requirements covered
- Dependencies
- Expected files
- Implementation activities
- Acceptance criteria
- Verification activities
- Definition of done

The plan will also guide the Implementation Agent and Verification Agent during the Agentic SDLC demonstration.

## 1.2 Implementation Objectives

The implementation shall:

- Deliver the approved basic To-Do functionality.
- Follow the approved architecture.
- Keep business logic separate from console interaction.
- Use Java 21 and Maven.
- Use TestNG for automated testing.
- Use Lombok selectively.
- Handle invalid input without unexpected termination.
- Maintain traceability between requirements, code, and tests.
- Avoid unnecessary features and complexity.

## 1.3 Implementation Approach

Implementation shall proceed incrementally.

The recommended sequence is:

1. Verify project configuration.
2. Implement the Task model.
3. Implement task creation and listing.
4. Implement completion and deletion.
5. Implement the console menu.
6. Connect the menu to service operations.
7. Implement startup and exit behavior.
8. Complete verification and review.

The implementation agent should work on one focused task at a time.

---

# 2. Implementation Environment

## 2.1 Required Environment

| Component | Requirement |
|---|---|
| Java | Java 21 |
| Build Tool | Maven |
| Testing Framework | TestNG |
| Boilerplate Reduction | Lombok |
| IDE | IntelliJ IDEA |
| Version Control | Git |
| Repository | GitHub |
| AI Development Assistance | GitHub Copilot |

## 2.2 Build Verification

The project shall support:

```bash
mvn test
```

The project shall compile and execute the automated test suite through Maven.

## 2.3 Coding Guidelines

- Use clear class and method names.
- Keep methods focused on a single responsibility.
- Keep task-management rules in `TodoService`.
- Keep terminal interaction in `ConsoleUI`.
- Avoid unnecessary abstractions.
- Use Lombok selectively.
- Do not introduce unapproved application features.
- Add or update tests when behavior changes.

---

# 3. Implementation Work Breakdown

## TASK-01: Verify Maven Project Configuration

### Objective

Confirm that the project is correctly configured for Java 21, Maven, TestNG, and Lombok.

### Requirements Covered

- TR-001: Java 21
- TR-002: Maven
- TR-003: TestNG
- TR-004: Lombok

### Dependencies

None.

### Expected Files

- `pom.xml`

### Implementation Activities

1. Inspect the existing Maven configuration.
2. Confirm the Java compiler release is configured for Java 21.
3. Confirm Lombok is included.
4. Confirm TestNG is included with test scope.
5. Confirm Maven Surefire can execute tests.
6. Confirm Lombok annotation processing is configured in IntelliJ.
7. Run the Maven test command.

### Acceptance Criteria

- **AC-T01.1:** Maven recognizes the project.
- **AC-T01.2:** Java 21 is configured.
- **AC-T01.3:** Lombok dependency is available.
- **AC-T01.4:** TestNG dependency is available.
- **AC-T01.5:** Maven test execution completes successfully.
- **AC-T01.6:** No application-specific functionality is added during configuration.

### Verification

```bash
java -version
mvn -version
mvn test
```

### Definition of Done

The project configuration is verified and ready for implementation.

---

## TASK-02: Implement Task Model

### Objective

Create the data model representing an individual task.

### Requirements Covered

- FR-001: Add Task
- FR-002: View All Tasks
- FR-003: Mark Task Complete
- FR-004: Delete Task

### Dependencies

- TASK-01

### Expected Files

- `src/main/java/com/capstone/todo/model/Task.java`

### Implementation Activities

1. Create the `model` package.
2. Create the `Task` class.
3. Add the task ID field.
4. Add the task title field.
5. Add the completion status field.
6. Define appropriate constructors.
7. Add required accessors.
8. Use Lombok where it improves readability.
9. Ensure new tasks can be initialized as incomplete.

### Acceptance Criteria

- **AC-T02.1:** The Task class contains an ID.
- **AC-T02.2:** The Task class contains a title.
- **AC-T02.3:** The Task class contains a completion status.
- **AC-T02.4:** A task can be initialized with the required fields.
- **AC-T02.5:** A newly created task can be initialized as incomplete.
- **AC-T02.6:** The class compiles successfully.
- **AC-T02.7:** Lombok usage does not obscure the model's purpose.

### Verification

- Compile the project.
- Review the model fields and constructors.
- Add focused model tests only if they provide meaningful value.

### Definition of Done

The Task model is implemented and ready for use by the service.

---

## TASK-03: Implement TodoService — Task Creation

### Objective

Implement task creation and title validation.

### Requirements Covered

- FR-001: Add Task
- FR-007: Input Validation

### Dependencies

- TASK-02

### Expected Files

- `src/main/java/com/capstone/todo/service/TodoService.java`
- `src/test/java/com/capstone/todo/service/TodoServiceTest.java`

### Implementation Activities

1. Create the `service` package.
2. Create `TodoService`.
3. Add an in-memory task collection.
4. Add a task ID counter.
5. Implement task title validation.
6. Trim leading and trailing whitespace.
7. Reject empty titles.
8. Reject whitespace-only titles.
9. Create valid tasks.
10. Assign unique task IDs.
11. Initialize new tasks as incomplete.
12. Add TestNG tests.

### Acceptance Criteria

- **AC-T03.1:** A valid task title creates a task.
- **AC-T03.2:** The created task receives a unique ID.
- **AC-T03.3:** The created task is initially incomplete.
- **AC-T03.4:** Empty titles are rejected.
- **AC-T03.5:** Whitespace-only titles are rejected.
- **AC-T03.6:** Valid titles are stored without leading or trailing whitespace.
- **AC-T03.7:** Invalid input does not create a task.
- **AC-T03.8:** Relevant TestNG tests pass.

### Verification

Test valid, empty, whitespace-only, and trimmed titles.

### Definition of Done

Task creation and business-level title validation are implemented and tested.

---

## TASK-04: Implement TodoService — Task Listing

### Objective

Allow the service to provide the current task list.

### Requirements Covered

- FR-002: View All Tasks

### Dependencies

- TASK-03

### Expected Files

- `TodoService.java`
- `TodoServiceTest.java`

### Implementation Activities

1. Implement the task-list retrieval operation.
2. Define how the service exposes task data.
3. Avoid allowing callers to modify internal state unintentionally.
4. Add tests for empty and populated task lists.

### Acceptance Criteria

- **AC-T04.1:** The service can retrieve all tasks.
- **AC-T04.2:** An empty task collection is handled.
- **AC-T04.3:** A populated task collection returns all current tasks.
- **AC-T04.4:** Listing tasks does not modify task data.
- **AC-T04.5:** Tests pass.

### Verification

- Test listing with no tasks.
- Test listing after creating multiple tasks.
- Confirm returned task information is correct.

### Definition of Done

Task retrieval is implemented and tested.

---

## TASK-05: Implement TodoService — Complete Task

### Objective

Allow an existing task to be marked as completed.

### Requirements Covered

- FR-003: Mark Task Complete
- FR-006: Invalid ID Handling

### Dependencies

- TASK-03

### Expected Files

- `TodoService.java`
- `TodoServiceTest.java`

### Implementation Activities

1. Implement task lookup by ID.
2. Implement task completion.
3. Define the service behavior when the task does not exist.
4. Ensure only the identified task is modified.
5. Add TestNG tests for success and failure cases.

### Acceptance Criteria

- **AC-T05.1:** An existing task can be located by ID.
- **AC-T05.2:** The correct task is marked completed.
- **AC-T05.3:** Other tasks remain unchanged.
- **AC-T05.4:** A non-existent ID is handled predictably.
- **AC-T05.5:** Tests pass.

### Verification

- Create a task and complete it.
- Confirm its status changes.
- Attempt completion using an invalid ID.
- Confirm no unrelated task is modified.

### Definition of Done

Task completion is implemented and tested.

---

## TASK-06: Implement TodoService — Delete Task

### Objective

Allow an existing task to be deleted.

### Requirements Covered

- FR-004: Delete Task
- FR-006: Invalid ID Handling

### Dependencies

- TASK-03

### Expected Files

- `TodoService.java`
- `TodoServiceTest.java`

### Implementation Activities

1. Implement deletion by task ID.
2. Reuse the service's task lookup behavior.
3. Define behavior when the task does not exist.
4. Ensure deleting one task does not remove unrelated tasks.
5. Add TestNG tests.

### Acceptance Criteria

- **AC-T06.1:** An existing task can be deleted.
- **AC-T06.2:** The deleted task no longer appears in the task list.
- **AC-T06.3:** Other tasks remain unchanged.
- **AC-T06.4:** A non-existent ID is handled predictably.
- **AC-T06.5:** Task IDs are not reused during the application run.
- **AC-T06.6:** Tests pass.

### Verification

- Create multiple tasks.
- Delete one task.
- Confirm the correct task is removed.
- Attempt deletion using an invalid ID.
- Confirm the remaining task list is unchanged.

### Definition of Done

Task deletion is implemented and tested.

---

## TASK-07: Implement ConsoleUI — Menu

### Objective

Create the interactive terminal menu.

### Requirements Covered

- FR-008: Interactive Menu System
- FR-005: Exit Application

### Dependencies

- TASK-03
- TASK-04
- TASK-05
- TASK-06

### Expected Files

- `src/main/java/com/capstone/todo/ui/ConsoleUI.java`

### Implementation Activities

1. Create the `ui` package.
2. Create `ConsoleUI`.
3. Define the required menu options.
4. Implement the menu loop.
5. Read user selections.
6. Handle supported selections.
7. Handle unsupported selections.
8. Continue the menu after handled errors.
9. Exit the loop when the user selects Exit.

### Acceptance Criteria

- **AC-T07.1:** All five required menu options are displayed.
- **AC-T07.2:** Valid menu selections are accepted.
- **AC-T07.3:** Unsupported selections display a helpful message.
- **AC-T07.4:** The menu continues after unsupported input.
- **AC-T07.5:** The menu loops until Exit is selected.
- **AC-T07.6:** Selecting Exit terminates the menu loop.

### Verification

Test menu display, invalid selection handling, and exit behavior using controlled input where practical.

### Definition of Done

The menu loop is implemented and behaves according to FR-008.

---

## TASK-08: Implement ConsoleUI — Add and View Tasks

### Objective

Connect task creation and listing to the interactive menu.

### Requirements Covered

- FR-001: Add Task
- FR-002: View All Tasks
- FR-007: Input Validation
- FR-008: Interactive Menu System

### Dependencies

- TASK-07

### Expected Files

- `ConsoleUI.java`

### Implementation Activities

1. Implement the Add Task menu operation.
2. Prompt for a task title.
3. Call the service to create the task.
4. Display success or validation messages.
5. Prompt again after invalid task-title input.
6. Implement the View All Tasks menu operation.
7. Display task IDs, titles, and completion status.
8. Display an appropriate message when the task list is empty.

### Acceptance Criteria

- **AC-T08.1:** The Add Task option prompts for a title.
- **AC-T08.2:** Valid titles create tasks.
- **AC-T08.3:** Invalid titles display a helpful error.
- **AC-T08.4:** Invalid titles prompt the user to retry.
- **AC-T08.5:** The View All Tasks option displays all tasks.
- **AC-T08.6:** Empty task lists display an appropriate message.
- **AC-T08.7:** The menu returns after each operation.

### Verification

Use controlled console input to test valid and invalid task-title scenarios.

### Definition of Done

Add and View operations are accessible through the menu and behave according to requirements.

---

## TASK-09: Implement ConsoleUI — Complete and Delete Tasks

### Objective

Connect task completion and deletion to the interactive menu.

### Requirements Covered

- FR-003: Mark Task Complete
- FR-004: Delete Task
- FR-006: Invalid ID Handling
- FR-008: Interactive Menu System

### Dependencies

- TASK-07
- TASK-08

### Expected Files

- `ConsoleUI.java`

### Implementation Activities

1. Implement the Mark Task Complete menu operation.
2. Prompt for a task ID.
3. Call the service to complete the task.
4. Display success or invalid-ID messages.
5. Implement the Delete Task menu operation.
6. Prompt for a task ID.
7. Call the service to delete the task.
8. Display success or invalid-ID messages.
9. Ensure handled errors return to the menu.

### Acceptance Criteria

- **AC-T09.1:** The Complete Task option prompts for a task ID.
- **AC-T09.2:** A valid ID completes the correct task.
- **AC-T09.3:** The Delete Task option prompts for a task ID.
- **AC-T09.4:** A valid ID deletes the correct task.
- **AC-T09.5:** An invalid ID displays exactly `Task ID not found`.
- **AC-T09.6:** The application remains operational after invalid-ID errors.
- **AC-T09.7:** The menu continues after each operation.

### Verification

Test valid and invalid IDs for both operations.

### Definition of Done

Complete and Delete operations are accessible through the menu and satisfy the requirements.

---

## TASK-10: Implement Application Startup and Exit

### Objective

Provide a single application entry point.

### Requirements Covered

- FR-005: Exit Application
- FR-008: Interactive Menu System

### Dependencies

- TASK-07
- TASK-08
- TASK-09

### Expected Files

- `src/main/java/com/capstone/todo/Main.java`

### Implementation Activities

1. Create `Main`.
2. Instantiate `TodoService`.
3. Instantiate `ConsoleUI`.
4. Start the application.
5. Ensure normal exit terminates the application gracefully.

### Acceptance Criteria

- **AC-T10.1:** The application starts from `Main`.
- **AC-T10.2:** The menu is displayed at startup.
- **AC-T10.3:** The service and UI are initialized correctly.
- **AC-T10.4:** Selecting Exit terminates the application.
- **AC-T10.5:** Normal termination does not produce an unhandled exception.

### Verification

Run the application and test its normal startup and exit flow.

### Definition of Done

The application can be launched and exited through its intended entry point.

---

## TASK-11: Complete Automated Verification

### Objective

Verify that the application satisfies the approved functional and non-functional requirements.

### Requirements Covered

- FR-001 through FR-008
- NFR-003: Testability
- NFR-004: Reliability
- NFR-007: Code Quality

### Dependencies

- TASK-01 through TASK-10

### Expected Files

- TestNG test classes
- Any implementation files requiring corrections

### Implementation Activities

1. Review the requirements traceability matrix.
2. Confirm each functional requirement has verification coverage.
3. Run all TestNG tests through Maven.
4. Verify invalid task-title behavior.
5. Verify invalid task-ID behavior.
6. Verify menu continuity.
7. Verify task ID uniqueness.
8. Fix defects found during testing.
9. Re-run the complete test suite.
10. Record actual verification results.

### Acceptance Criteria

- **AC-T11.1:** The project compiles successfully.
- **AC-T11.2:** TestNG tests execute through Maven.
- **AC-T11.3:** All required task operations are tested.
- **AC-T11.4:** Invalid task titles are tested.
- **AC-T11.5:** Invalid task IDs are tested.
- **AC-T11.6:** Menu behavior is verified.
- **AC-T11.7:** Known defects are recorded or resolved.
- **AC-T11.8:** Verification results reflect actual test execution.

### Verification Commands

```bash
mvn clean test
```

### Definition of Done

The application has passed the agreed verification checks and is ready for independent code review.

---

## TASK-12: Code Review and Capstone Evidence

### Objective

Prepare the implementation for review and demonstrate the Agentic SDLC workflow.

### Requirements Covered

- Agentic SDLC requirements
- NFR-002: Maintainability
- NFR-007: Code Quality

### Dependencies

- TASK-11

### Expected Files

- Source code
- TestNG tests
- Updated SDLC artifacts
- Changelog or PR documentation, as required by the capstone workflow

### Implementation Activities

1. Run the independent Code Review Agent.
2. Review findings and severity.
3. Resolve required findings.
4. Re-run relevant tests.
5. Update documentation if implementation decisions changed.
6. Prepare a PR description.
7. Prepare a reviewer checklist.
8. Record human approval before PR creation.

### Acceptance Criteria

- **AC-T12.1:** Code review findings are recorded.
- **AC-T12.2:** Required findings are resolved or explicitly accepted by the developer.
- **AC-T12.3:** Relevant tests pass after fixes.
- **AC-T12.4:** Documentation reflects approved implementation decisions.
- **AC-T12.5:** PR documentation is prepared.
- **AC-T12.6:** PR creation requires human approval.

### Definition of Done

The implementation has been reviewed, verified, and prepared for the capstone's final PR demonstration.

---

# 4. Task Dependency Overview

```text
TASK-01: Verify Maven Configuration
    |
    v
TASK-02: Task Model
    |
    v
TASK-03: Service — Add Task
    |
    v
TASK-04: Service — List Tasks
    |
    +--------------------+
    |                    |
    v                    v
TASK-05: Complete      TASK-06: Delete
    |                    |
    +---------+----------+
              |
              v
TASK-07: Console Menu
              |
              v
TASK-08: UI — Add and View
              |
              v
TASK-09: UI — Complete and Delete
              |
              v
TASK-10: Main and Exit
              |
              v
TASK-11: Verification
              |
              v
TASK-12: Code Review and PR Preparation
```

---

# 5. Requirements-to-Implementation Traceability

| Requirement | Implementation Tasks | Primary Verification |
|---|---|---|
| FR-001: Add Task | TASK-02, TASK-03, TASK-08 | Valid creation and title validation tests |
| FR-002: View All Tasks | TASK-04, TASK-08 | Empty and populated list tests |
| FR-003: Mark Task Complete | TASK-05, TASK-09 | Completion and invalid-ID tests |
| FR-004: Delete Task | TASK-06, TASK-09 | Deletion and invalid-ID tests |
| FR-005: Exit Application | TASK-07, TASK-10 | Startup and exit flow |
| FR-006: Invalid ID Handling | TASK-05, TASK-06, TASK-09 | Exact message and continued operation |
| FR-007: Input Validation | TASK-03, TASK-08 | Empty, whitespace-only, valid title tests |
| FR-008: Interactive Menu | TASK-07 through TASK-10 | Menu display, routing, loop, and exit tests |

---

# 6. Agentic SDLC Execution Guidelines

## 6.1 Planning Agent

The Planning Agent shall:

- Use the approved requirements.
- Use the approved architecture.
- Consider design-review findings.
- Produce focused implementation tasks.
- Identify dependencies.
- Define completion criteria.
- Avoid unapproved scope expansion.

## 6.2 Implementation Agent

The Implementation Agent shall:

- Work on one focused task at a time.
- Follow the approved architecture.
- Implement only approved requirements.
- Add or update relevant tests.
- Report files changed.
- Report tests executed.
- Identify unresolved issues.
- Request clarification when requirements are ambiguous.

## 6.3 Verification Agent

The Verification Agent shall:

- Map implementation behavior to requirements.
- Review test coverage.
- Execute or request actual verification.
- Identify missing tests.
- Record failures and evidence.
- Avoid claiming tests passed unless they were actually executed.

## 6.4 Code Review Agent

The Code Review Agent shall:

- Review implementation independently.
- Identify correctness and maintainability issues.
- Assign severity.
- Recommend changes.
- Verify that changes address findings.
- Avoid expanding the application scope without approval.

## 6.5 Human Approval

The developer shall approve:

- Requirements baseline
- Architecture baseline
- Implementation plan
- Significant scope changes
- Resolution or acceptance of important review findings
- Final PR preparation and creation

---

# 7. Definition of Done

An implementation task is complete when:

- Its stated objective is achieved.
- Its acceptance criteria are satisfied.
- The implementation follows the approved architecture.
- Relevant tests are added or updated.
- Relevant tests pass.
- No unapproved feature has been introduced.
- Known issues are recorded.
- Required documentation is updated.
- The developer or workflow gate accepts the result.

---

# 8. Implementation Risks

| Risk ID | Risk | Mitigation |
|---|---|---|
| IR-001 | Copilot implements multiple tasks at once and introduces unnecessary changes | Use focused task prompts |
| IR-002 | Business logic is placed in the UI | Review component responsibilities |
| IR-003 | Validation differs between UI and service | Enforce business validation in the service |
| IR-004 | Invalid IDs terminate the menu | Add service and UI tests |
| IR-005 | Task IDs are reused | Maintain an increasing counter |
| IR-006 | Tests require manual interaction | Test business logic independently |
| IR-007 | Scope expands | Require developer approval for changes |
| IR-008 | Agent reports unverified success | Require actual Maven test evidence |

---

# 9. Implementation Status

| Task | Status |
|---|---|
| TASK-01: Maven Configuration | Completed |
| TASK-02: Task Model | Not Started |
| TASK-03: Service — Add Task | Not Started |
| TASK-04: Service — List Tasks | Not Started |
| TASK-05: Service — Complete Task | Not Started |
| TASK-06: Service — Delete Task | Not Started |
| TASK-07: Console Menu | Not Started |
| TASK-08: UI — Add and View | Not Started |
| TASK-09: UI — Complete and Delete | Not Started |
| TASK-10: Main and Exit | Not Started |
| TASK-11: Verification | Not Started |
| TASK-12: Code Review and PR Preparation | Not Started |

---

# 10. Plan Approval

The implementation plan is ready for approval when:

- Tasks cover the approved requirements.
- Dependencies are clear.
- Completion criteria are measurable.
- Verification is planned.
- The plan follows the approved architecture.
- No unapproved application features are included.

**Current Status:** Initial detailed implementation plan — pending developer approval.
