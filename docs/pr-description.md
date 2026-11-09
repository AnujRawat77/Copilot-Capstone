# PR Description Draft: Java To-Do App and Agentic SDLC Evidence

**Status:** TASK-12 completion was explicitly accepted by the human on 2026-11-09. At the time this description was drafted, PR documentation was prepared but no PR had been created and PR creation had not been authorized.

**Historical pre-merge PR state:** PR #1, “Implement Java console todo application,” had been created and was open against `main`.

**Current repository-draft state (2026-11-09):** PR #1 subsequently merged into `main` at `951f3d45dec757103e3d1a8a00a659d63c61b1d7`, as verified in the prior read-only closure review. No formal GitHub approval review was recorded; the human Design Review approval is separate. The merge event does not establish a separately documented prior merge authorization. This update changes only the repository draft; the live PR description was not accessed or edited.

## Purpose

Deliver a deliberately small terminal-based To-Do application and demonstrate an Agentic SDLC with requirements, architecture, design review, implementation, testing, independent code review, documentation, and human approval checkpoints.

## Implementation

The application supports adding tasks, viewing all tasks, marking tasks complete, deleting tasks, and exiting through a repeating menu (FR-001 through FR-008).

- Titles are trimmed and rejected when empty after trimming. The UI displays an error and prompts for retry.
- Tasks start incomplete and receive increasing IDs that are not reused after deletion.
- Nonexistent numeric IDs produce exactly `Task ID not found`.
- Non-numeric IDs produce a helpful error without changing task data, then return to the menu.
- Listings show task IDs, titles, and completion status, or an empty-list message.
- Add Task prompts precede input reads, including retries; EOF is handled without an unhandled exception.
- Task data is stored in memory only and is lost when the process exits.

## Key Design Decisions

The approved architecture decisions AD-001 through AD-008 are documented in [architecture.md](architecture.md).

| Component | Responsibility |
|---|---|
| `Main` | Compose the service and UI and start the menu. |
| `ConsoleUI` | Read input, present prompts/results, route operations, and handle retries and menu continuity. |
| `TodoService` | Validate titles, assign IDs, and manage task operations and the in-memory collection. |
| `Task` | Represent ID, title, and completion state, using selective Lombok `@Getter`. |

Java 21 is the configured release target. Maven manages the build, TestNG runs automated tests, and Lombok 1.18.46 is explicitly configured as an annotation processor. No database, web framework, persistence, or external application service is introduced.

## Agentic SDLC Evidence

The approved requirements and architecture baselines, design-review artifact, and implementation plan provide the project context. TASK-01 through TASK-12 are recorded complete. The human explicitly accepted the TASK-12 completion assessment on 2026-11-09. This does not declare overall project completion or authorize PR creation.

The recorded workflow includes role-based requirements and architecture work, design review, incremental authorized implementation, Testing verification, independent Code Review, approved defect corrections, retesting, and Orchestrator assessment. Human decisions authorize stage transitions and corrections; agent recommendations do not replace approval. Historically, when this draft was prepared, the design-review artifact was preliminary and independent Design Review remained pending.

**Subsequent Design Review (2026-11-09 reconciliation):** The independent review assessed implemented source, requirements, and tests. Its focused follow-up recommended DR-019 (immutable tasks and service-state isolation) and DR-020 (explicit Java `String.trim()` semantics and coverage) resolved. DR-021 was addressed by updating the review artifact while preserving history. The human formally approved the design on 2026-11-09, accepted these dispositions, and authorized PR review. That decision was not GitHub PR approval, merge authorization, or formal project closure.

Notable review and correction history:

- TASK-11's Lombok usage finding and missing-accessor compile blocker were addressed through authorized model and annotation-processor corrections, followed by testing.
- TASK-11's exact missing-ID assertion finding was corrected and independently confirmed resolved.
- **T12-CR-001 (Medium):** Add Task waited for input before printing its prompt. The authorized correction moves the prompt before the input check, with staged-input and EOF regression tests.
- **T12-CR-002 (Low):** The integration command's documented classpath notation was incorrect. A transparent correction note gives `java -cp target\classes org.capstone.Main`, preserves the originally recorded command and result, and claims no new integration run.
- Independent TASK-12 follow-up review recommended **Approved**, with both findings resolved and no remaining findings in that follow-up scope.

Full records and approval history: [implementation-plan.md](implementation-plan.md). Reviewer actions: [reviewer-checklist.md](reviewer-checklist.md).

## Verification Evidence

These are existing execution records, not tests performed while drafting this description.

| Check | Recorded result |
|---|---|
| Staged-input regression before the prompt fix | 1 test, 1 failure, 0 errors, 0 skipped; detected the original prompt-order defect. |
| `mvn -o -Dtest=ConsoleUITest test` after the prompt fix | BUILD SUCCESS; 12 passed, 0 failures, 0 errors, 0 skipped. |
| `mvn clean test` after the prompt fix | BUILD SUCCESS, exit code 0; 25 passed, 0 failures, 0 errors, 0 skipped. |
| Later local DR-019/DR-020 verification: `mvn -o "-Dtest=TodoServiceTest,ConsoleUITest" test` | BUILD SUCCESS; 32 passed, 0 failures, 0 errors, 0 skipped (20 service and 12 UI). Recorded in `design-review.md`, section 14.3; not GitHub-hosted CI. |
| New regression tests | `addTaskPromptsBeforeReadingTitleAndRetryInput` and `addTaskHandlesEndOfInputBeforeTitleOrRetryWithoutCreatingTask` both passed. |
| New scripted integration run reported by the Testing Agent | `java -cp target/classes org.capstone.Main` succeeded with exit code 0 and empty stderr. The report records 17 menu displays for 17 selections after correcting the harness's mistaken expected count of 14 and rerunning successfully. Covered empty list, blank-title retry, trimmed creation, nonnumeric and nonexistent numeric IDs on Complete and Delete (including exact missing-ID errors), menu continuity, completion status, deletion/list-empty behavior, ID 2 after deleting ID 1, and normal Exit. This is reported Testing Agent evidence, not a run by Documentation. |
| Documentation correction whitespace check | `git diff --check -- docs/implementation-plan.md` passed, exit code 0. |

The full suite covers service operations, title validation, task-ID uniqueness/non-reuse, menu behavior, invalid titles and IDs, exact missing-ID messages, empty/populated listings, Exit, staged title prompts, and EOF handling. No regressions were detected in that suite.

## Limitations and Review Gates

- Verification used **JDK 25 with release target 21**. Build and runtime verification on an installed JDK 21 remain unverified.
- Packaging remains unverified; the earlier offline packaging attempt could not resolve the uncached Maven JAR plugin.
- The original earlier scripted integration flow was not rerun after TASK-11's test-only assertion correction or the later TASK-12 prompt fix. Separately, the Testing Agent reported a new scripted integration run after the prompt fix; its command, coverage, corrected 17-selection expectation, and result are recorded above. The new run does not change the historical status of the earlier flow.
- Existing staged, unstaged, and untracked work requires explicit scope review before any future staging or PR creation. This draft is not an authoritative list of a committed PR diff.
- A changelog is conditional in the plan; no mandatory standalone changelog convention was identified, so none was prepared.
- Individual checklist review actions may remain pending. The human accepted TASK-12 completion on 2026-11-09; this is not overall project completion. **PR creation requires separate explicit human approval.**

**Human limitation decisions - 2026-11-09:** The human explicitly accepted installed-JDK-21 verification and packaging verification as unverified limitations, and accepted that no integration rerun was performed after DR-019/DR-020. The earlier separate post-prompt-fix integration report is not verification of the later corrective changes. The human also acknowledged that no GitHub-hosted CI checks were available. Acceptance is not evidence that any of these checks passed.

**Approval reconciliation - 2026-11-09:** Separate human PR-creation authorization is recorded in `reviewer-checklist.md`; the earlier no-authorization statements above describe the draft's original approval boundary, not the current PR state. Formal capstone closure remains pending human approval, and direct reviewer actions remain identified in the checklist. See the supplemental human decision record in `implementation-plan.md`.

## Evidence Sources

- [Requirements and acceptance criteria](requirements.md)
- [Architecture and approved decisions](architecture.md)
- [Design-review artifact](design-review.md)
- [Implementation plan, verification, findings, and approval records](implementation-plan.md)
- Testing Agent's recorded report of the new post-fix scripted integration run (summarized above)
- `pom.xml`, the `Main`, `Task`, `TodoService`, and `ConsoleUI` implementation, and `TodoServiceTest`/`ConsoleUITest`
- Saved Maven Surefire results under `target\surefire-reports` (generated, not a committed evidence artifact)
