# Software Implementation Plan

## Agentic SDLC — Java To-Do Application

| Document Information | Value |
|---|---|
| Project | Agentic SDLC — Java To-Do Application |
| Document | Software Implementation Plan |
| Version | 1.0 |
| Status | Approved by developer on 2026-11-05 |
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

The plan guides the Implementation Agent. The Testing Agent verifies acceptance criteria, and the Orchestrator coordinates final verification using Testing Agent results and Code Review findings; no separate Verification Agent is required.

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

- `src/main/java/org/capstone/model/Task.java`

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

### Execution Record

**Status:** Completed on 2026-11-05. `Task.java` compiled successfully with `mvn -o -DskipTests compile`; Maven used the configured Java release 21 target while the installed JDK was 25. This does not verify a build using an installed Java 21 JDK. No TestNG tests were executed because no test sources existed, and no separate model tests were added. TASK-01 verification limitations remain: `mvn test` succeeded without executing tests, and `mvn clean test` could not resolve a Maven Central dependency, so a clean build was not verified.

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

- `src/main/java/org/capstone/service/TodoService.java`
- `src/test/java/org/capstone/service/TodoServiceTest.java`

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

### Execution Record

**Status:** Completed on 2026-11-05. The offline command `mvn -o -Dtest=TodoServiceTest test` failed because the Surefire TestNG provider was not cached. The online command `mvn -Dtest=TodoServiceTest test` passed all five tests (0 failures, 0 errors, 0 skipped). Maven compiled with the configured Java release 21 target on installed JDK 25; a build using an installed JDK 21 remains unverified.

---

## TASK-04: Implement TodoService — Task Listing

### Objective

Allow the service to provide the current task list.

### Requirements Covered

- FR-002: View All Tasks

### Dependencies

- TASK-03

### Expected Files

- `src/main/java/org/capstone/service/TodoService.java`
- `src/test/java/org/capstone/service/TodoServiceTest.java`

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

### Execution Record

**Status:** Completed on 2026-11-05. `mvn -o -Dtest=TodoServiceTest test` passed all eight TestNG tests (0 failures, 0 errors, 0 skipped). `git diff --check` passed for the TASK-04 source and test files. Maven compiled with the configured Java release 21 target on installed JDK 25; a build using an installed JDK 21 remains unverified.

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

- `src/main/java/org/capstone/service/TodoService.java`
- `src/test/java/org/capstone/service/TodoServiceTest.java`

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

### Execution Record

**Status:** Completed on 2026-11-05. `mvn -o -Dtest=TodoServiceTest test` passed all ten TestNG tests (0 failures, 0 errors, 0 skipped), and `git diff --check` passed for the TASK-05 source and test files. Maven compiled with the configured Java release 21 target on installed JDK 25; a build using an installed JDK 21 remains unverified.

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

- `src/main/java/org/capstone/service/TodoService.java`
- `src/test/java/org/capstone/service/TodoServiceTest.java`

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

### Execution Record

**Status:** Completed on 2026-11-05. `mvn -o -Dtest=TodoServiceTest test` passed all thirteen TestNG tests (0 failures, 0 errors, 0 skipped), and `git diff --check` passed for the TASK-06 source and test files. Maven compiled with the configured Java release 21 target on installed JDK 25; a build using an installed JDK 21 remains unverified.

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

- `src/main/java/org/capstone/ui/ConsoleUI.java`

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

### Execution Record

**Status:** Completed on 2026-11-05. `mvn -o -Dtest=ConsoleUITest test` passed all three focused TestNG tests (0 failures, 0 errors, 0 skipped). The offline Maven test command succeeded, and `git diff --check` passed for the TASK-07 files. Maven compiled with the configured Java release 21 target on installed JDK 25; a build using an installed JDK 21 remains unverified. Valid menu selections currently display a selection acknowledgment; task operations and application startup remain separate plan tasks.

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

- `src/main/java/org/capstone/ui/ConsoleUI.java`

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

### Execution Record

**Status:** Completed on 2026-11-05. `ConsoleUITest` passed 5 tests (0 failures, 0 errors, 0 skipped), and `git diff --check` passed. Maven compiled with the configured Java release 21 target on installed JDK 25; a build using an installed JDK 21 remains unverified. Complete and Delete menu operations still only acknowledge the selection (TASK-09), and application startup remains separate work.

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

- `src/main/java/org/capstone/ui/ConsoleUI.java`

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
- **AC-T09.8:** A non-numeric ID displays a helpful input error, makes no task-data changes, and returns to the menu.

### Verification

Test valid IDs, non-existent numeric IDs, and non-numeric IDs for both operations. For non-numeric IDs, verify that no task data changes and the menu is displayed again.

### Definition of Done

Complete and Delete operations are accessible through the menu and satisfy the requirements.

### Execution Record

**Status:** Completed on 2026-11-05. The initial focused command `mvn -o -Dtest=ConsoleUITest test` ran 5 tests with 1 failure, 0 errors, and 0 skipped because `runAcceptsValidSelectionsAndReturnsToMenu` expected the previous `Complete Task selected.` acknowledgment and its input omitted task IDs. After an authorized, targeted update to that test's input and assertions, the final `mvn -o -Dtest=ConsoleUITest test` passed all 5 tests (0 failures, 0 errors, 0 skipped). A temporary verification harness passed all six requested scenarios: valid, nonexistent numeric, and non-numeric IDs for both Complete and Delete, including task-data and menu-return checks. `git diff --check` reported no whitespace errors but did not cover untracked Java files; separate no-index whitespace checks passed for `ConsoleUI.java` and `ConsoleUITest.java`. Compilation used JDK 25 with the Java 21 release target; a build on an installed JDK 21 remains unverified.

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

- `src/main/java/org/capstone/Main.java`

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

### Execution Record

**Status:** Completed on 2026-11-05. `mvn -o "-Dtest=ConsoleUITest,TodoServiceTest" test` passed 18 tests (0 failures, 0 errors, 0 skipped). Offline compilation with `mvn -o -DskipTests compile` succeeded. Running `Main` with Exit input displayed the menu and terminated with exit code 0. Offline packaging with `mvn -o -DskipTests package` could not resolve the uncached Maven JAR plugin, so packaging was not verified. Compilation used JDK 25 with the Java 21 release target; a build on an installed JDK 21 remains unverified.

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

1. The Testing Agent reviews the requirements traceability matrix and confirms coverage.
2. The Testing Agent runs all TestNG tests through Maven.
3. Verify invalid task-title behavior.
4. Verify invalid task-ID behavior, including non-numeric IDs, unchanged task data, and return to the menu.
5. Verify menu continuity.
6. Verify task ID uniqueness.
7. Record defects and route them to the Implementation Agent.
8. The Implementation Agent makes approved fixes for reported defects.
9. After fixes, the Testing Agent reruns relevant tests and the complete test suite.
10. Complete final verification only after the fixes and retesting are complete.
11. Record actual test results and final verification evidence.

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

The Testing Agent has recorded actual test results after any approved fixes and retesting. Final verification occurs only after that retesting is complete; the Orchestrator coordinates it using the Testing Agent's evidence and Code Review findings. No separate Verification Agent is used.

### Execution Record

**Historical verification:** The initial `mvn clean test` on 2026-11-05, using Maven 3.10.0 and Java 25.0.4.1, passed 18 tests (0 failures, 0 errors, 0 skipped), with compilation using `--release 21`. After the user authorized focused ConsoleUI regression tests for nonexistent numeric IDs, non-numeric IDs, and empty-list output, `mvn -o -Dtest=ConsoleUITest test` passed 10 tests (0 failures, 0 errors, 0 skipped), and the full retest `mvn clean test` passed 23 tests (0 failures, 0 errors, 0 skipped). The new UI tests assert exact not-found/error messages, unchanged task state/list, menu return, and the empty-list message. The Testing Agent's scripted console checks also passed for non-numeric and nonexistent numeric IDs on Complete and Delete, preserving task data and returning to the menu; empty-list output, normal Exit flow, and status-0 termination were manually verified. Automated tests cover service operations, title validation/retry, task ID uniqueness, populated task display, menu selections, invalid menu choices, invalid task IDs, empty-list UI output, and Exit. No application behavior defects were observed. Independent Code Review found a Medium-severity NFR-007 defect: `Task.java` used manually written getters rather than selectively using Lombok, contrary to `requirements.md` NFR-007 and `architecture.md` AD-006. The user authorized a minimal `@Getter` correction, but the initial Implementation Agent attempt failed due to DNS resolution. The initial Code Review attempt also failed due to DNS resolution; a subsequent independent review completed and reported the finding above.

#### Post-Correction Verification - 2026-11-06

**Status:** Post-correction testing passed with limitations; TASK-11 remains in progress pending independent review of the final changes and Orchestrator final recording. `Task.java` now uses Lombok `@Getter`, and the approved `pom.xml` correction explicitly configures Lombok 1.18.46 in `annotationProcessorPaths`. The Testing Agent ran `mvn clean test`: BUILD SUCCESS, 23 tests passed (13 service tests and 10 ConsoleUI tests), 0 failures, 0 errors, and 0 skipped. Both main and test sources were rebuilt with the Java 21 release target on JDK 25.0.4.1 using Maven 3.10.0.

Traceability was checked against FR-001 through FR-008: the executed service and UI tests cover creation, listing including empty lists, completion, deletion, invalid titles and retry, nonexistent numeric and non-numeric IDs, unchanged task data after invalid input, ID uniqueness/non-reuse, menu continuity, and Exit. An additional scripted `java -cp targetclasses org.capstone.Main` integration check confirmed all task operations, completion status display, removal from listings, title validation and retry, both invalid-ID types for both operations, continued interaction, unique IDs after deletion, and normal Exit even with trailing input. The application exited with code 0 and empty stderr; these integration checks are separate from the 23 TestNG tests.

**Command notation correction - 2026-11-09 (T12-CR-002):** The integration command above was recorded as `java -cp targetclasses org.capstone.Main`. The correct Windows PowerShell notation for the normal Maven output directory is `java -cp target\classes org.capstone.Main`. This corrects the documented classpath notation only; the original record and reported exit-code-0/empty-stderr result are preserved. It does not establish which command was actually executed or claim a new integration run.

NFR-003 is supported by independent service tests and Maven TestNG execution. NFR-004 is supported by automated and integration invalid-input recovery checks. Selective Lombok usage for NFR-007 is present and its generated accessors were exercised successfully, but NFR-007 is not fully signed off until independent final Code Review. No failures or new behavior defects were observed. The earlier missing-accessor compilation blocker did not recur. DNS warnings prevented repository metadata refreshes, but cached artifacts allowed the exact planned command to succeed; no alternative Maven test command was substituted. Lombok emitted a `sun.misc.Unsafe` deprecation warning and TestNG emitted an SLF4J no-provider warning; neither failed execution. A build and runtime test on an installed JDK 21, and packaging, remain unverified. Only this documentation was changed during post-correction verification.

#### Post-Review-Fix Retest - 2026-11-06

After the authorized test-only correction in `ConsoleUITest.java`, `mvn clean test` completed with BUILD SUCCESS: 23 tests, 0 failures, 0 errors, and 0 skipped. The strengthened assertions for nonexistent numeric IDs confirm the exact `Task ID not found` message for both Complete and Delete. No regressions were detected in the full automated suite. The previously recorded scripted console integration checks were not rerun after this test-only change; application code was unchanged.

Compilation used JDK 25.0.4.1 with the Java 21 release target. Verification on an installed JDK 21 and packaging remain unverified. Repository metadata DNS warnings appeared during Maven execution, but did not prevent the build.

#### Final Orchestrator Assessment - 2026-11-08

**Status:** TASK-11 completed with verification limitations recorded. The approved acceptance criteria and Definition of Done are satisfied by the recorded post-fix testing and independent Code Review evidence.

Completion criteria

Recorded evidence

AC-T11.1 and AC-T11.2: Compilation and Maven TestNG execution

The post-review-fix `mvn clean test` rebuilt main and test sources and returned BUILD SUCCESS: 23 tests, 0 failures, 0 errors, and 0 skipped. Saved Surefire results confirm these totals.

AC-T11.3: Required task operations

Service and UI tests cover creation, listing, completion, and deletion; the earlier scripted integration flow also exercised these operations.

AC-T11.4 and AC-T11.5: Invalid titles and IDs

Tests cover title validation/retry, nonexistent numeric and non-numeric IDs, and unchanged task data. Both strengthened exact `Task ID not found` assertions passed in the post-fix full suite.

AC-T11.6: Menu behavior

Automated tests cover menu continuity, invalid selections, and Exit; earlier integration evidence records normal application termination.

AC-T11.7: Known defects

The approved Lombok correction and explicit annotation processor configuration resolved the NFR-007 finding and compilation blocker. The low-severity exact-message assertion finding is resolved. Independent final review and focused follow-up reported no remaining correctness findings.

AC-T11.8 and Definition of Done: Actual post-fix evidence

Focused and full-suite results after approved corrections are recorded above, with earlier history preserved. The Orchestrator assessed those results and the independent review; no new test execution is claimed by this assessment.

**Final Code Review:** The independent review recommendation is Approved. The focused follow-up on 2026-11-08 confirmed that both assertions account for prompts printed without a newline and require the exact error text followed by the platform line separator. Code Review is complete for TASK-11, with no unresolved correctness findings. The recorded correction was test-only; existing dirty/untracked files prevented independently proving its full change scope from Git alone. Recorded traceability supports FR-001 through FR-008 and NFR-003, NFR-004, and NFR-007 within the verified environment.

**Outstanding limitations:** Verification used JDK 25.0.4.1 with release target 21; a build and runtime test on an installed JDK 21 remain unverified. Packaging remains unverified after the earlier offline attempt could not resolve the uncached Maven JAR plugin. The scripted integration flow was not rerun after the test-only assertion correction; only the full automated suite was rerun. These limitations do not leave an approved TASK-11 acceptance criterion unmet, but completion must not be interpreted as installed-JDK-21 or packaging verification. TASK-12 has not been started, and overall project completion is not declared.

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

### Execution Record

#### Orchestrator Completion Assessment - 2026-11-09

**Status:** In Progress. Independent review and post-fix automated verification are complete, but PR documentation is outstanding. TASK-12 and the overall project are not declared complete.

**Independent review:** The TASK-12 review initially recommended Changes Required. T12-CR-001 (Medium) identified that Add Task waited for input before printing its title prompt. The authorized correction prints the prompt before checking for input, including retries, and preserves graceful EOF handling. Staged-input and EOF regression tests were added without removing existing assertions. T12-CR-002 (Low) identified incorrect classpath notation in the historical integration record. The correction note now literally contains `java -cp target\classes org.capstone.Main`, while preserving the original recorded command and execution result and explicitly claiming no new integration run. The independent follow-up review confirmed both findings resolved and recommended Approved, with no remaining findings.

**Verification evidence:** The Testing role ran `mvn clean test` after the prompt correction: BUILD SUCCESS, exit code 0, 25 passed, 0 failures, 0 errors, and 0 skipped. The saved Surefire report confirms the totals and successful execution of `addTaskPromptsBeforeReadingTitleAndRetryInput` and `addTaskHandlesEndOfInputBeforeTitleOrRetryWithoutCreatingTask`. No regressions were detected in the full automated suite. After the documentation-only correction, `git diff --check -- docs/implementation-plan.md` passed with exit code 0; independent follow-up also confirmed the literal classpath notation and preservation of the historical record. This assessment uses recorded execution and saved results; it does not claim another test or integration run.

| Criterion | Assessment | Evidence or remaining work |
|---|---|---|
| AC-T12.1: Code review findings are recorded | Satisfied | Both unique finding IDs, severities, corrections, and the final review outcome are recorded above. |
| AC-T12.2: Required findings are resolved or explicitly accepted | Satisfied | Both findings were corrected under explicit developer authorization and independently confirmed resolved; no acceptance of unresolved findings is needed. |
| AC-T12.3: Relevant tests pass after fixes | Satisfied | The full post-fix `mvn clean test` passed all 25 tests, including both new regression tests. The later change was documentation-only. |
| AC-T12.4: Documentation reflects approved implementation decisions | Satisfied for reviewed changes | This record documents the authorized prompt/EOF correction, final review, actual verification, and transparent command-notation correction. Requirements and architecture were not changed by these fixes. |
| AC-T12.5: PR documentation is prepared | Not satisfied | No prepared PR description or reviewer checklist was found in the repository. Both remain required. A changelog is conditional in the plan; no separate mandatory changelog requirement was identified in the reviewed workflow documents. Its necessity remains to be confirmed during authorized PR preparation. |
| AC-T12.6: PR creation requires human approval | Gate preserved; creation not authorized | No PR has been prepared or created as part of this assessment. Explicit approval is required for final PR preparation and, separately, before PR creation. Approval of preparation must not be interpreted as approval to create a PR. |
| Definition of Done: Reviewed, verified, and prepared for final PR demonstration | Not satisfied | Review and automated verification are complete; the required PR description and reviewer checklist are outstanding. PR-readiness and required approvals must be assessed after that preparation. |

**Outstanding limitations:** Verification used JDK 25 with release target 21. A build and runtime test on an installed JDK 21, and packaging, remain unverified. The earlier scripted integration flow was not rerun. Existing staged, unstaged, and untracked work must be preserved; its presence also requires an explicit scope check before any future staging or PR creation. TASK-11's historical evidence and final completion assessment remain unchanged.

**Next approval:** Developer review of this assessment, followed by separate authorization for PR documentation preparation if desired. No PR creation is authorized.

#### Final Assessment and Human Acceptance - 2026-11-09

**Status:** Complete. The human explicitly accepted the TASK-12 completion assessment and authorized this final documentation update on 2026-11-09. All TASK-12 acceptance criteria and its Definition of Done are satisfied. This final assessment supersedes the earlier assessment above that PR documentation was outstanding; that assessment is retained unchanged as historical information.

| Criterion | Final assessment | Evidence |
|---|---|---|
| AC-T12.1: Code review findings are recorded | Satisfied | T12-CR-001 and T12-CR-002, their severities, corrections, and independent follow-up outcomes are recorded above. |
| AC-T12.2: Required findings are resolved or explicitly accepted | Satisfied | Both authorized corrections were independently confirmed resolved. Final Code Review recommendation: Approved, with no remaining findings. |
| AC-T12.3: Relevant tests pass after fixes | Satisfied | Recorded `mvn clean test`: BUILD SUCCESS, exit code 0, 25 passed, 0 failures, 0 errors, and 0 skipped. Both new staged-input and EOF regression tests passed. No new test execution is claimed by this documentation update. |
| AC-T12.4: Documentation reflects approved implementation decisions | Satisfied | The implementation record and reviewed PR documentation reflect the approved design, authorized corrections, actual verification, and outstanding limitations. |
| AC-T12.5: PR documentation is prepared | Satisfied | `docs/pr-description.md` and `docs/reviewer-checklist.md` were prepared under authorization and reviewed for factual accuracy, FR-001 through FR-008 coverage, architecture alignment, review resolutions, verification evidence, limitations, and approval gates. No blocking corrections were identified. No mandatory standalone changelog requirement was found. The human accepted the completion assessment. |
| AC-T12.6: PR creation requires human approval | Satisfied; gate remains enforced | PR documentation preparation and TASK-12 acceptance do not authorize PR creation. PR creation has not been authorized or performed and requires separate explicit human approval. |
| Definition of Done: Reviewed, verified, and prepared for final PR demonstration | Satisfied | Independent review is Approved, post-fix automated verification passed, required PR documentation is prepared and reviewed, and the human explicitly accepted TASK-12 completion. |

**Preserved verification limitations:** Verification used JDK 25 with release target 21; build and runtime verification on an installed JDK 21 remain unverified. Packaging remains unverified. The earlier scripted integration flow was not rerun after the test-only correction or the later TASK-12 prompt fix. These limitations remain explicit in the PR documentation and are not represented as passed checks.

**Approval boundary:** TASK-12 completion is recorded, not overall project completion or PR creation approval. At the time this assessment was recorded, no PR had been created. Existing staged, unstaged, and untracked work remained subject to explicit scope review before any separately authorized staging or PR action.

**Current PR state (2026-11-09):** PR #1, “Implement Java console todo application,” has since been created and is open against `main`. This update records the current PR state and does not alter the dated TASK-12 assessment or its historical evidence.

#### Supplemental Scripted Main Integration Verification — report received 2026-11-09

The Testing Agent later reported a separate scripted integration run using `java -cp target/classes org.capstone.Main`. The execution date was not separately recorded. The report states that the process exited with code 0 and empty stderr. The scenario covered empty-list output; blank-title rejection and retry; trimmed task creation; nonnumeric and nonexistent numeric IDs for both Complete and Delete, including exact missing-ID messages; menu continuity; completed status display; deletion and an empty subsequent listing; assignment of ID 2 after deleting ID 1; and normal Exit. The report records 17 menu displays for 17 selections. The first harness expectation of 14 displays was corrected, then the scenario was rerun successfully.

This is later Testing Agent-reported evidence and is distinct from the earlier historical integration flow, which was not rerun after the TASK-11 test-only correction or the TASK-12 prompt fix. This supplemental record does not claim that the earlier run was repeated, nor that the Documentation Agent executed this scenario.

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

## 6.3 Final Verification Coordination

The Orchestrator shall coordinate final verification by:

- Using the Testing Agent's recorded test results, coverage, failures, and evidence.
- Considering the Code Review Agent's findings and their resolution status.
- Confirming verification claims are supported by actual test execution.
- Reporting unresolved findings and verification limitations.

The Testing Agent performs the requirement-based testing. A separate Verification Agent is not required.

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
| TASK-02: Task Model | Completed (verification limitations recorded) |
| TASK-03: Service — Add Task | Completed (verification limitations recorded) |
| TASK-04: Service — List Tasks | Completed (verification limitations recorded) |
| TASK-05: Service — Complete Task | Completed (verification limitations recorded) |
| TASK-06: Service — Delete Task | Completed (verification limitations recorded) |
| TASK-07: Console Menu | Completed (verification limitations recorded) |
| TASK-08: UI — Add and View | Completed (verification limitations recorded) |
| TASK-09: UI — Complete and Delete | Completed (verification limitation recorded) |
| TASK-10: Main and Exit | Completed (verification limitations recorded) |
| TASK-11: Verification | Completed (final verification and Code Review recorded; limitations preserved) |
| TASK-12: Code Review and PR Preparation | Complete (human acceptance recorded 2026-11-09; PR creation requires separate approval) |

---

# 10. Plan Approval

The implementation plan is ready for approval when:

- Tasks cover the approved requirements.
- Dependencies are clear.
- Completion criteria are measurable.
- Verification is planned.
- The plan follows the approved architecture.
- No unapproved application features are included.

**Current Status:** Implementation plan baseline approved by the developer on 2026-11-05.

## Approval Record

Artifact

Approver

Decision

Date

Implementation plan (`docs/implementation-plan.md`)

Developer

Approved as the implementation plan baseline

2026-11-05