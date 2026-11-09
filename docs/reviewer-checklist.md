# TASK-12 Reviewer Checklist

**Status:** TASK-12 completion was accepted by the human on 2026-11-09. This checklist remains a review aid; unchecked verification boxes are pending reviewer actions, not assertions that the implementation failed. Recorded results below are evidence to assess, not new verification or approval. No PR has been created or authorized.

## Requirements

- [ ] **FR-001:** Confirm creation prompts, trimmed titles, unique IDs, initial incomplete status, and success confirmation.
- [ ] **FR-002:** Confirm ID/title/status display for all current tasks, empty-list output, and no mutation from viewing.
- [ ] **FR-003:** Confirm completion targets the correct task, updates its status, and provides confirmation.
- [ ] **FR-004:** Confirm deletion removes only the selected task and provides confirmation.
- [ ] **FR-005:** Confirm Exit stops the menu normally without an unhandled exception.
- [ ] **FR-006:** Confirm exact `Task ID not found` output for nonexistent numeric IDs on Complete and Delete; non-numeric IDs leave data unchanged and return to the menu.
- [ ] **FR-007:** Confirm empty/whitespace-only title rejection, helpful errors, retry prompts before input, and valid-title trimming.
- [ ] **FR-008:** Confirm all five menu operations, routing, invalid-selection recovery, and continuity until Exit, with graceful EOF handling.

## Architecture and Code Quality

- [ ] Confirm `Main` composes the application, `ConsoleUI` owns terminal interaction, `TodoService` owns validation/IDs/task operations, and `Task` represents task data (AD-002, AD-007; NFR-002).
- [ ] Confirm in-memory-only storage and absence of unapproved features, frameworks, databases, or external application services (AD-001, AD-003).
- [ ] Confirm Maven/TestNG configuration, Java 21 release target, and selective Lombok `@Getter` with its explicit processor configuration (AD-004 through AD-006; NFR-003, NFR-007).
- [ ] Confirm focused methods, clear naming, and no unnecessary complexity or duplicated business validation.
- [ ] Confirm approved requirements and architecture were not changed by the TASK-12 corrections.

## Review Findings and Resolutions

Recorded independent follow-up recommendation: **Approved**, with no remaining findings in its scope. See the TASK-12 execution record in [implementation-plan.md](implementation-plan.md).

- [ ] Review **T12-CR-001 (Medium)**: the title prompt now precedes the potentially blocking input check, including retries; EOF remains graceful.
- [ ] Inspect the staged `Readable` test: it asserts prompt availability before supplying each title/retry line, rejection without task creation, and successful trimmed-title creation.
- [ ] Inspect EOF coverage before the initial title and after invalid-title input; confirm existing assertions were not weakened.
- [ ] Review **T12-CR-002 (Low)**: the correction note literally contains `java -cp target\classes org.capstone.Main`; the historical `targetclasses` record and reported exit result are retained without implying another integration run.
- [ ] Confirm both findings were fixed under explicit authorization and independently confirmed resolved.

## Automated and Historical Verification

Recorded final post-fix command: `mvn clean test` on 2026-11-09, **BUILD SUCCESS**, exit code 0, **25 passed, 0 failures, 0 errors, 0 skipped**. This checklist does not claim another execution.

- [ ] Compare the recorded results with available Surefire reports and implementation-plan evidence.
- [ ] Confirm both new tests are included and passed: `addTaskPromptsBeforeReadingTitleAndRetryInput` and `addTaskHandlesEndOfInputBeforeTitleOrRetryWithoutCreatingTask`.
- [ ] Review the pre-fix regression failure demonstrating that the staged test detects the original defect.
- [ ] Review focused post-fix ConsoleUI evidence: 12 passed, 0 failures, 0 errors, 0 skipped.
- [ ] Confirm the recorded documentation whitespace check passed with exit code 0.
- [ ] Distinguish the original scripted flow (not rerun after TASK-11's test-only assertion correction or the TASK-12 prompt fix) from the separate new post-fix scripted run reported by the Testing Agent.
- [ ] Assess the Testing Agent's recorded command `java -cp target/classes org.capstone.Main`: exit code 0, empty stderr, and coverage of empty list, blank-title retry, trimmed creation, nonnumeric and missing numeric IDs for both operations, exact missing-ID errors, menu continuity, completion status, deletion/list-empty behavior, ID 2 following deletion of ID 1, and normal Exit.
- [ ] Confirm the integration harness's expected selection count was corrected from 14 to 17 and the rerun succeeded with 17 menu displays for 17 selections.
- [ ] Keep this reported Testing Agent integration evidence distinct from any verification performed by the Documentation Agent.

## Limitations Requiring Explicit Acknowledgment

- [ ] Acknowledge **JDK 25 with release target 21**, not an installed-JDK-21 build/runtime verification.
- [ ] Acknowledge installed-JDK-21 verification remains unverified.
- [ ] Acknowledge packaging remains unverified after the offline Maven JAR plugin resolution blocker.
- [ ] Acknowledge the original earlier scripted integration flow was not rerun after the test-only correction or the later prompt fix; assess the separate new post-fix Testing Agent run on its own evidence.
- [ ] Confirm in-memory task loss after exit is an approved design limitation.
- [ ] Preserve the existing staged, unstaged, and untracked work; explicitly establish the intended diff before any separately authorized staging or PR action.

## Documentation and Approval Gates

- [ ] Review [pr-description.md](pr-description.md) for evidence-based scope, design decisions, workflow history, actual results, and limitations.
- [ ] Confirm whether a separate changelog is required. None was created because the reviewed plan makes it conditional and no mandatory repository convention was identified.
- [x] Have the Orchestrator reassess AC-T12.1 through AC-T12.6 and the Definition of Done; the implementation plan records TASK-12 complete.
- [x] Obtain explicit human acceptance before recording TASK-12 complete; the human accepted the completion assessment on 2026-11-09. This does not declare overall project completion.
- [ ] Record separate explicit human approval **before PR creation**. Authorization to prepare these drafts is not authorization to create a PR.

No PR is created or authorized by this checklist. Individual checklist review actions and separate PR-creation approval remain pending.
