package org.capstone;

import org.capstone.service.TodoService;
import org.capstone.ui.ConsoleUI;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TodoService todoService = new TodoService();
        ConsoleUI consoleUI = new ConsoleUI(todoService, new Scanner(System.in), System.out);
        consoleUI.run();
    }
}
