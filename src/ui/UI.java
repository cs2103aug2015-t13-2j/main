/*
package ui;

import java.util.Scanner;

import NexTask.DisplayManager;
import NexTask.Logic;

/**
 * UI class will display the appropriate print messages required when called
 * upon by other classes.
 * 
 * @author Javan
 *
 */

//@@author A0124710W -unused because GUI has replaced UI functionalities
/*
public class UI {
	private static Scanner scanner;
	private static Logic logic;
	private static DisplayManager display;

	public static void main(String[] args) {
		initialize();
		startProgram();
	}

	public static void initialize() {
		scanner = new Scanner(System.in);
		logic = new Logic();
		display = new DisplayManager();
	}

	public static void startProgram() {
		System.out.println(display.messageSelector(WELCOME_COMMAND, WELCOME_CONDITION));
		run();
	}

	public static void run() {
		while (true) {
			System.out.println(display.messageSelector(WELCOME_COMMAND, PROMPT_CONDITION));
			System.out.println(logic.executeUserCommand(getUserInput()));
		}
	}

	public static String getUserInput() {
		String userInput = "";
		if (scanner.hasNextLine()) {
			userInput = scanner.nextLine();
		} else {
			System.exit(0);
		}
		return userInput;
	}
}
*/