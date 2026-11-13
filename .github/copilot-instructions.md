# GitHub Copilot Instructions — Todo App Capstone

## Project Context

This repository contains a beginner-friendly Todo App capstone demonstrating an **Agentic Software Development Life Cycle (Agentic SDLC)**.

The primary goal is to demonstrate how AI agents can support requirements analysis, architecture, design review, implementation, testing, code review, and documentation—with human approval at important checkpoints.

Keep the application intentionally simple. Do not add features merely to make it more sophisticated.

## Technology Stack

Use the approved stack:

-   Java 21
-   Maven
-   TestNG
-   Lombok
-   IntelliJ IDEA
-   Terminal-based user interface
-   In-memory task storage

Do not introduce additional frameworks, libraries, databases, or external services without explicit user approval.

## Source of Truth

Consult the relevant project artifacts before performing substantial work:

-   `docs/requirements.md`
-   `docs/architecture.md`
-   `docs/design-review.md`
-   `docs/implementation-plan.md`
-   `docs/user-stories.md`, when available

Approved requirements and architecture take precedence over assumptions or suggestions.

If project documents conflict, identify the conflict and ask for clarification. Do not silently choose an interpretation or change an approved decision.

## Functional Requirements

Preserve the documented Todo App behavior:

-   Add a task
-   View all tasks
-   Mark a task complete
-   Delete a task
-   Reject task names that are empty after trimming
-   Display an error and allow retry after invalid task-name input
-   Display exactly `Task ID not found` when a non-existent task ID is provided
-   Keep the application operational after an invalid task ID
-   Continue displaying the interactive menu until the user selects Exit

Do not alter these behaviors without explicit approval.

## Coding Guidelines

-   Write clear, beginner-friendly Java.
-   Follow the existing package structure and naming conventions.
-   Keep methods focused and responsibilities clear.
-   Separate console interaction from task-management logic.
-   Prefer straightforward solutions over unnecessary abstractions or design patterns.
-   Avoid unrelated refactoring.
-   Do not add features outside the approved requirements.
-   Reuse existing dependencies whenever possible.

## Testing Guidelines

-   Use TestNG for automated tests.
-   Add or update tests for implemented acceptance criteria.
-   Include relevant normal, boundary, and error scenarios.
-   Keep tests readable and independent.
-   Run the narrowest relevant tests first.
-   Run the full Maven test suite when appropriate.
-   Report actual test results.
-   Never claim tests passed unless they were executed and passed.

## Agentic SDLC Workflow

The project uses specialized agents with distinct responsibilities:

-   **Orchestrator:** Coordinates stages and approval checkpoints.
-   **Requirements:** Reviews requirement clarity, completeness, and testability.
-   **Architecture:** Reviews system structure and technical decisions.
-   **Design Review:** Independently evaluates design readiness and risks.
-   **Implementation:** Implements approved tasks from the implementation plan.
-   **Testing:** Verifies behavior against acceptance criteria.
-   **Code Review:** Reviews code changes for defects and regressions.
-   **Documentation:** Maintains accurate project documents and SDLC evidence.

Do not claim an agent performed work unless it actually did.

## Human Approval

The user is the final decision-maker.

Request explicit approval before:

-   Moving between major SDLC stages
-   Starting implementation
-   Changing approved requirements or architecture
-   Adding significant scope or dependencies
-   Declaring the project complete

An agent recommendation is not equivalent to user approval.

## Credit-Conscious Behavior

The user has limited GitHub Copilot credits.

-   Work on one clearly defined task at a time.
-   Use only the context needed for the task.
-   Prefer targeted edits over rewriting entire files.
-   Avoid broad repository analysis unless necessary.
-   Do not repeat large documents in responses.
-   Ask before performing broad refactoring or work beyond the assigned scope.
-   Keep explanations concise and beginner-friendly.

## Reporting Expectations

For substantial work, report:

1.  **Task:** What was requested
2.  **Changes:** Files created or modified
3.  **Verification:** Tests or checks actually performed
4.  **Status:** Completed, partially completed, blocked, or not verified
5.  **Next action:** One recommended next step

Be transparent about uncertainty, limitations, and work that has not been verified.