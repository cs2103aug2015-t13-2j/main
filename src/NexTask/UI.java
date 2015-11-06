package NexTask;

import java.util.Scanner;

/**
 * UI class will display the appropriate print messages required when called
 * upon by other classes.
 * 
 * @@author Javan
 *
 */

//@@author A0124710W
public class UI {

	private static final int WELCOME_COMMAND = 1;
	private static final int WELCOME_CONDITION = 1;
	private static final int PROMPT_CONDITION = 2;
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