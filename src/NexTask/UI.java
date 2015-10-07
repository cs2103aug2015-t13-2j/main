package NexTask;

import java.util.Scanner;

/**
 * UI class will display the appropriate print messages required when called upon by other classes.
 * 
 * @author Javan
 *
 */

public class UI {
	/*
	 * Print Messages
	 */
	private static final String COMMAND_PROMPT = "Command: ";
	private static final String WELCOME_MSG = "Greetings! NexTask is ready for use.";
	private static final String WELCOME_HELP_MSG = "If you need any help, please type \"help\" to retrieve the help guide!";
	private static final String COMMAND_ADDED = "Task has been added to %1$s!";
	private static final String COMMAND_DONE = "Task has been marked as completed!";
	private static final String COMMAND_DELETED = "Task has been deleted!";
	private static final String COMMAND_EDIT = "Task has been editted!";
	private static final String COMMAND_UNDO = "Previous task has been undone!";
	private static final String COMMAND_SEARCH = "Search results in %1$s: \n";
	private static final String COMMAND_ARCHIVE = "Archive has been retrieved. Here is the list of completed tasks!";
	private static final String COMMAND_SAVED = "File has been saved to %1$s!";
	private static final String COMMAND_HELP = "The following commands are as shown:\n"
			+ "To add an event: add event start (date & time) end (date & time) (description of task).\n"
			+ "To add a task with deadline: add deadline due by (date & time) (description of task).\n"
			+ "To add a task with no deadline: add to-do (description of task).\n"
			+ "To add a recurring task: add repeat (description of task).\n"
			+ "To mark a task as completed: finished/completed (task number).\n"
			+ "To delete a task: delete (task number).\n"
			+ "To edit a specific task: edit (task number) (name/date/start/end) (the edit).\n"
			+ "To undo a certain task: undo"
			+ "To search: search (field you want to search) (description of search term).\n"
			+ "To retrieve an archive of completed tasks: archive.\n"
			+ "To display the current list of tasks: display.\n"
			+ "To save to: save to (filename).\n";
	private static final String NO_CONTENT = "%1$s is empty. There is no content to delete from!";
	private static final String NO_CONTENT_TO_DISPLAY = "Task list is empty. There is no content to display!";
	private static final String COMMAND_ERROR = "Unrecognized command type entered! Please input a correct command type!";
	private static final String UNABLE_TO_DELETE = "Sorry, unable to delete from %1$s!";
	private static final String UNABLE_TO_SEARCH = "Sorry, unable to find %1$s in %2$s!";
	private static final String EXCEED_MAXSIZE_OF_TASK = "Sorry, the number you put exceeds the maximum number of tasks!";
	private static final String WRONG_NUM_FORMAT = "Sorry, the number you put is not in correct format";
	private static final int INDEX_OF_CMD_NAME = 0;
	private static final String INVALID_CMD = "invalid";
	private static Scanner scanner;
	private static Logic logic;
	//private Storage storage;
	private static CommandParser parser;
	
	public static void main(String[] args) {
		initialize();
		startProgram();
	}
	
	public static void initialize() {
		scanner = new Scanner(System.in);
		logic = new Logic();
	//	storage = new Storage();
		parser = new CommandParser();
	}
	
	public static void startProgram() {
		displayWelcomeMessage();
		run();
	}
	
	public static void run() {
		while (true) {
			printCommandPrompt();
			Command newCommand = getUserCommand();
			if(isValid(newCommand)) {
				logic.executeUserCommand(newCommand);
			} else {
				displayErrorMessage();
			}
				//ui.displayMessage(feedback);
		//	storage.updateOutputFile();
		}
	}
	
	public static boolean isValid(Command command) {
		if(command.getCommandName() != "invalid") {
			return true;
		} else {
			return false;
		}
	}
	
	public static Command getUserCommand() {
		String input = getUserInput();
		return parser.parse(input);
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
	
	public void displayMessage(String feedback) {
		System.out.println(feedback);
	}
	
	public static void displayWelcomeMessage() {
		System.out.println(WELCOME_MSG);
		System.out.println(WELCOME_HELP_MSG);
	}
	
	public static void printCommandPrompt() {
		System.out.println(COMMAND_PROMPT);
	}
	
	public void displayHelpMessage() {
		System.out.println(COMMAND_HELP);
	}
	
	public String printAddMessage() {
		return COMMAND_ADDED;
	}
	
	public void printDoneMessage() {
		System.out.println(COMMAND_DONE);
	}
	
	public void printDelMessage() {
		System.out.println(COMMAND_DELETED);
	}
	
	public void printEditMessage() {
		System.out.println(COMMAND_EDIT);
	}
	
	public void printUndoMessage() {
		System.out.println(COMMAND_UNDO);
	}
	
	public String printSearchMessage() {
		return COMMAND_SEARCH;
	}
	
	public void printArchiveMessage() {
		System.out.println(COMMAND_ARCHIVE);
	}
	
	public String printSaveMessage() {
		return COMMAND_SAVED;
	}
	
	public void printNoContent() {
		System.out.println(NO_CONTENT);
	}
	
	public void printExceedSize(){
		System.out.println(EXCEED_MAXSIZE_OF_TASK);
	}
	
	public void printWrongFormat(){
		System.out.println(WRONG_NUM_FORMAT);
	}
	
	public void printEmptyList() {
		System.out.println(NO_CONTENT_TO_DISPLAY);
	}
	
	public static void displayErrorMessage() {
		System.out.println(COMMAND_ERROR);
	}
	
	public String printUnableToDelete() {
		return UNABLE_TO_DELETE;
	}
	
	public String printUnableToSearch() {
		return UNABLE_TO_SEARCH;
	}

}
