# PR Description Draft: Java To-Do App and Agentic SDLC Evidence

**Status:** TASK-12 completion was explicitly accepted by the human on 2026-11-09. PR documentation is prepared; no PR has been created, and PR creation has not been authorized.

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

The recorded workflow includes role-based requirements and architecture work, design review, incremental authorized implementation, Testing verification, independent Code Review, approved defect corrections, retesting, and Orchestrator assessment. Human decisions authorize stage transitions and corrections; agent recommendations do not replace approval. The design-review artifact remains preliminary and explicitly states that independent Design Review Agent review is pending; this PR does not claim that review was completed or approved.

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

## Evidence Sources

- [Requirements and acceptance criteria](requirements.md)
- [Architecture and approved decisions](architecture.md)
- [Design-review artifact](design-review.md)
- [Implementation plan, verification, findings, and approval records](implementation-plan.md)
- Testing Agent's recorded report of the new post-fix scripted integration run (summarized above)
- `pom.xml`, the `Main`, `Task`, `TodoService`, and `ConsoleUI` implementation, and `TodoServiceTest`/`ConsoleUITest`
- Saved Maven Surefire results under `target\surefire-reports` (generated, not a committed evidence artifact)
