# Java To-Do App

A small, terminal-based task manager created as a capstone for demonstrating an Agentic Software Development Life Cycle. The project emphasizes requirements-driven development, specialized AI-agent roles, and human approval at key stages.

## Features

- Add tasks, view the task list, mark tasks complete, and delete tasks.
- Reject blank task names and handle invalid task IDs without ending the session.
- Keep tasks in memory for the duration of the application session.

## Technology

Java 21, Maven, TestNG, and Lombok. The application uses a simple layered design: the console interface handles user interaction, while the task service manages task operations and in-memory data.

## Build and test

Run the automated tests with:

```bash
mvn test
```

## Project documents

Requirements, architecture, user stories, and implementation planning are available in the [`docs`](docs/) directory.
