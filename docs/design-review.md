# Software Design Review
## Agentic SDLC — Java To-Do Application

| Document Information | Value |
|---|---|
| Project | Agentic SDLC — Java To-Do Application |
| Document | Software Design Review |
| Version | 1.0 |
| Status | Preliminary Review — Pending Independent Agent Review |
| Review Type | Requirements and Architecture Review |
| Reviewer | Capstone Developer / Design Review Agent |
| Review Baseline | Requirements v1.0 and Architecture v1.0 |

---

# 1. Review Purpose

The purpose of this review is to evaluate the proposed architecture before implementation begins.

The review checks whether the design:

- Satisfies the documented requirements.
- Separates responsibilities appropriately.
- Handles expected user errors.
- Supports automated testing.
- Avoids unnecessary complexity.
- Provides a clear implementation path.
- Supports the capstone's Agentic SDLC objectives.

This document records a preliminary review. The actual Design Review Agent shall independently evaluate the artifacts and may add, modify, or reject findings.

# 2. Review Scope

## 2.1 Included

- Requirements coverage
- Component responsibilities
- Task data model
- In-memory storage
- Task ID management
- Input validation
- Error handling
- Menu control flow
- Testability
- Technical choices
- Scope control

## 2.2 Excluded

- Detailed source-code review
- Performance benchmarking
- Database design
- Security assessment for a networked application
- Deployment architecture
- UI design beyond the terminal menu
- Review of implementation code not yet created

# 3. Architecture Under Review

The proposed architecture contains:

- `Main`
- `ConsoleUI`
- `TodoService`
- `Task`
- An in-memory task collection

The application uses Java 21, Maven, TestNG, and selective Lombok usage.

The proposed design separates console interaction from task-management business logic.

# 4. Review Method

The review uses the following approach:

1. Identify the requirement or design area.
2. Evaluate the proposed design.
3. Identify gaps, risks, or ambiguity.
4. Record recommendations.
5. Assign a severity.
6. Determine whether the finding must be resolved before implementation.
7. Record the review decision.

## 4.1 Severity Definitions

| Severity | Meaning |
|---|---|
| Critical | Prevents the application or workflow from meeting a fundamental requirement |
| Major | Significant functional, reliability, or maintainability concern |
| Moderate | A concern that should be addressed but does not block the overall design |
| Minor | Small improvement or clarification |
| Informational | Observation or accepted limitation |

# 5. Requirements Coverage Review

## DR-001: Functional Requirement Coverage

**Area:** FR-001 through FR-008

**Observation:** The architecture defines components for task creation, listing, completion, deletion, invalid-ID handling, input validation, and menu interaction.

**Assessment:** The proposed components can support the documented functional requirements.

**Risk:** If implementation responsibilities are not followed, business rules may become duplicated between the UI and service.

**Recommendation:**
- Keep task operations in `TodoService`.
- Keep input prompts and output formatting in `ConsoleUI`.
- Verify every functional requirement against implementation and tests.

**Severity:** Moderate

**Disposition:** Accepted with implementation verification.

---

## DR-002: Invalid Task ID Behavior

**Area:** FR-006

**Observation:** The requirements specify the exact error message `Task ID not found` and require the application to remain operational.

**Risk:** An invalid ID may cause an unhandled exception or terminate the menu loop.

**Recommendation:**
- Define a consistent service outcome for a missing task.
- Ensure `ConsoleUI` displays the required exact message.
- Return to the menu after the handled error.
- Test both completion and deletion with non-existent IDs.

**Severity:** Major

**Disposition:** Must be verified during implementation.

---

## DR-003: Task Title Validation

**Area:** FR-007

**Observation:** The requirements require non-empty task titles after trimming whitespace and a retry prompt after validation failure.

**Risk:** Validation implemented only in the UI could be bypassed by another caller of the service.

**Recommendation:**
- Enforce title validity in `TodoService`.
- Keep the retry loop and user messaging in `ConsoleUI`.
- Ensure invalid input does not create a task.
- Test empty, whitespace-only, and valid titles.

**Severity:** Major

**Disposition:** Accepted as an architectural responsibility split.

---

## DR-004: Interactive Menu Continuity

**Area:** FR-008

**Observation:** The menu must continue until Exit is selected, including after handled errors.

**Risk:** A menu operation may return unexpectedly or an invalid selection may terminate the application.

**Recommendation:**
- Keep the menu loop in `ConsoleUI`.
- Handle unsupported menu selections without terminating.
- Return to the menu after each completed operation.
- Ensure Exit is the normal termination path.

**Severity:** Major

**Disposition:** Must be verified during implementation.

---

# 6. Component Design Review

## DR-005: Main Responsibility

**Observation:** `Main` is responsible for application initialization.

**Assessment:** This is appropriate for a small standalone application.

**Risk:** Business logic may be added to `Main` as the application grows.

**Recommendation:** Keep `Main` limited to component creation and application startup.

**Severity:** Minor

**Disposition:** Accepted.

---

## DR-006: ConsoleUI and TodoService Separation

**Observation:** The architecture separates terminal interaction from task operations.

**Assessment:** This supports testability and maintainability.

**Risk:** The UI could begin directly manipulating task data or duplicating service validation.

**Recommendation:**
- Keep all task collection access in `TodoService`.
- Keep user prompts and messages in `ConsoleUI`.
- Avoid service methods that directly print to the console.

**Severity:** Moderate

**Disposition:** Accepted.

---

## DR-007: Task Model Simplicity

**Observation:** The proposed `Task` model contains ID, title, and completion status.

**Assessment:** These fields are sufficient for the approved requirements.

**Risk:** Additional fields could expand scope without clear value.

**Recommendation:** Keep the model limited to the approved fields unless a requirement change is approved.

**Severity:** Minor

**Disposition:** Accepted.

---

# 7. Data and State Review

## DR-008: In-Memory Storage

**Observation:** The application uses an in-memory collection.

**Assessment:** This is appropriate for the initial scope and avoids database complexity.

**Risk:** Task data is lost after application termination.

**Recommendation:** Record this as an accepted limitation. Do not add persistence unless scope changes.

**Severity:** Informational

**Disposition:** Accepted.

---

## DR-009: Task ID Uniqueness

**Observation:** The architecture proposes a monotonically increasing task ID counter.

**Assessment:** This is suitable for a single-user, in-memory application.

**Risk:** Reusing IDs after deletion could cause a user to act on the wrong task.

**Recommendation:**
- Keep the ID counter in `TodoService`.
- Do not reset the counter when a task is deleted.
- Add tests confirming IDs remain unique during an application run.

**Severity:** Moderate

**Disposition:** Accepted.

---

## DR-010: Task Collection Exposure

**Observation:** The service must provide access to the task list for display.

**Risk:** Returning a directly mutable internal collection could allow callers to modify service state without using business operations.

**Recommendation:** Prefer returning a read-only view or a defensive copy, where practical, while keeping the implementation simple.

**Severity:** Moderate

**Disposition:** Recommendation for implementation.

---

# 8. Error Handling Review

## DR-011: Error Handling Responsibility

**Observation:** The architecture assigns business validation to the service and user-facing messages to the UI.

**Assessment:** This separation is appropriate.

**Risk:** Error messages or validation rules may become inconsistent if implemented in multiple places.

**Recommendation:**
- Define a predictable service error contract.
- Keep presentation messages in the UI.
- Avoid duplicated business rules.
- Do not silently ignore unexpected programming errors.

**Severity:** Moderate

**Disposition:** Accepted.

---

## DR-012: Invalid Numeric Input

**Observation:** Task IDs and menu selections are expected to be entered through the terminal.

**Risk:** A user may enter non-numeric input where an integer is expected.

**Recommendation:**
- Handle non-numeric menu selections gracefully.
- Handle non-numeric task IDs gracefully.
- Do not allow parsing errors to terminate the application.
- Return to the relevant prompt or menu according to the interaction design.

**Severity:** Moderate

**Disposition:** Recommended clarification for implementation.

---

# 9. Testability Review

## DR-013: Service Unit Testing

**Observation:** `TodoService` is designed to operate independently of console input.

**Assessment:** This supports focused unit tests using TestNG.

**Recommendation:** Cover task creation, validation, listing, completion, deletion, invalid IDs, and ID uniqueness through service tests.

**Severity:** Minor

**Disposition:** Accepted.

---

## DR-014: Console Interaction Testing

**Observation:** Console interaction is handled by `ConsoleUI`.

**Risk:** Interactive behavior may be difficult to test if input and output are tightly coupled to global console streams.

**Recommendation:**
- Keep console interaction methods focused.
- Use controlled input and captured output where practical.
- Prioritize service tests if UI tests become disproportionately complex.
- Ensure menu looping and retry behavior are still verified.

**Severity:** Moderate

**Disposition:** Accepted as a testing consideration.

---

# 10. Technology Review

## DR-015: Java 21 and Maven

**Observation:** Java 21 and Maven are selected.

**Assessment:** These meet the project constraints and provide a repeatable build process.

**Recommendation:** Keep Java and Maven configuration consistent across local development and verification.

**Severity:** Informational

**Disposition:** Accepted.

---

## DR-016: TestNG

**Observation:** TestNG is the selected automated testing framework.

**Assessment:** It is suitable for the planned unit tests.

**Recommendation:** Ensure Maven Surefire executes the TestNG suite and that test results are available during verification.

**Severity:** Minor

**Disposition:** Accepted.

---

## DR-017: Lombok

**Observation:** Lombok may be used to reduce repetitive code.

**Risk:** Excessive annotations may make behavior less explicit or complicate IDE configuration.

**Recommendation:**
- Use Lombok selectively.
- Keep business behavior explicit.
- Verify annotation processing is configured.
- Avoid generated methods that are unnecessary for the model.

**Severity:** Minor

**Disposition:** Accepted.

---

# 11. Scope and Complexity Review

## DR-018: Application Scope

**Observation:** The application is intentionally limited to basic task management.

**Assessment:** This supports the capstone's primary objective of demonstrating Agentic SDLC.

**Risk:** Additional features may consume time and Copilot credits without strengthening the workflow demonstration.

**Recommendation:**
- Do not add persistence, a GUI, a database, a web framework, authentication, or unrelated task features.
- Require developer approval for scope changes.
- Prioritize workflow quality and artifact traceability.

**Severity:** Moderate

**Disposition:** Accepted.

---

# 12. Review Findings Summary

| Finding | Area | Severity | Required Action |
|---|---|---|---|
| DR-001 | Functional coverage | Moderate | Verify implementation and tests against requirements |
| DR-002 | Invalid task IDs | Major | Test exact message and continued operation |
| DR-003 | Task title validation | Major | Enforce in service; test retry behavior |
| DR-004 | Menu continuity | Major | Test loop and handled errors |
| DR-005 | Main responsibility | Minor | Keep entry point focused |
| DR-006 | UI/service separation | Moderate | Preserve component boundaries |
| DR-007 | Task model | Minor | Keep model within approved scope |
| DR-008 | In-memory storage | Informational | Accept data-loss limitation |
| DR-009 | Task ID uniqueness | Moderate | Maintain counter and test uniqueness |
| DR-010 | Collection exposure | Moderate | Avoid uncontrolled mutation |
| DR-011 | Error handling | Moderate | Define consistent service outcomes |
| DR-012 | Non-numeric input | Moderate | Handle parsing errors gracefully |
| DR-013 | Service testability | Minor | Add focused TestNG tests |
| DR-014 | UI testability | Moderate | Verify interaction behavior |
| DR-015 | Java/Maven | Informational | Maintain consistent configuration |
| DR-016 | TestNG execution | Minor | Verify Maven test execution |
| DR-017 | Lombok | Minor | Use selectively and configure correctly |
| DR-018 | Scope control | Moderate | Prevent unapproved scope expansion |

---

# 13. Review Decision

## Preliminary Decision

**Approved for implementation planning, subject to verification of the major findings.**

The proposed architecture is appropriate for the current application scope.

The major concerns are implementation-level behaviors that can be verified through focused tests:

- Invalid task ID handling
- Task title validation and retry
- Menu continuity after handled errors

The architecture does not require additional layers or frameworks to address these concerns.

## 13.1 Conditions Before Implementation

Before implementation is considered complete:

- The required error messages shall be implemented.
- Task validation shall be enforced by the service.
- The menu shall continue after handled errors.
- Task IDs shall remain unique during an application run.
- Automated tests shall cover the relevant requirements.

## 13.2 Agentic SDLC Review Requirement

During the capstone demonstration, the Design Review Agent shall independently review the requirements and architecture.

It shall:

- Produce its own findings.
- Assign severity.
- Recommend actions.
- Identify blocking concerns.
- Record a review decision.
- Request human approval where required.

This document is a preliminary baseline and shall not be represented as the output of an already executed agent review.

## 13.3 Approval Record

| Role | Status |
|---|---|
| Developer | Pending |
| Design Review Agent | Pending independent review |
| Architecture baseline | Draft |
| Implementation planning | Awaiting approval |
