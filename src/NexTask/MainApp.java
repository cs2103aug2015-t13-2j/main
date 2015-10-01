package NexTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;

public class MainApp {
	
	/*
	 * Print Messages
	 */
	private static final String COMMAND_PROMPT = "Command: ";
	private static final String WELCOME_MSG = "Greetings! NexTask is ready for use.";
	private static final String WELCOME_HELP_MSG = "If you need any help, please type \"help\" to retrieve the help guide!";
	private static final String COMMAND_ADDED = "Task has been added to %1$s!";
	private static final String COMMAND_DONE = "Task has been marked as completed!";
	private static final String COMMAND_DELETED = "Task has been deleted from %1$s!";
	private static final String COMMAND_EDIT = "Task has been editted!";
	private static final String COMMAND_UNDO = "Previous task has been undone!";
	private static final String COMMAND_SEARCH = "Search results: \n";
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
	private static final String COMMAND_ERROR = "Unrecognized command type entered! Please input a correct command type!";
	private static final String UNABLE_TO_DELETE = "Sorry, unable to delete from %1$s!";
	private static final String UNABLE_TO_SEARCH = "Sorry, unable to find %1$s in %2$s!";

	private Scanner scanner;
	private Logic logic;
	//private Storage storage;
	private UI ui;
	private CommandParser parser;
	
	enum OPERATION {
		ADD, COMPLETED, DELETE, EDIT, UNDO, SEARCH, ARCHIVE, DISPLAY, SAVE, INVALID
	};
	
<<<<<<< Updated upstream
	private static boolean isTimeToExit = false;
	
	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		String fileName = args[0];
		launchProgram(args);
		sc.close();
	}

	public static void launchProgram(String[] args) throws IOException {
		File nexTaskFile = initialiseFile(args[0]);
		printWelcomeMsg();
		executeProgram(nexTaskFile);
		
	}

	public static void executeProgram(File nexTaskFile) throws IOException {
		while (isTimeToExit) {
			printCommandPrompt();
			//executeCommand(nexTaskFile, parseCommand());
		}
	}
	
	private static void printCommandPrompt() {
		System.out.println(COMMAND_PROMPT);
	}

	private static File initialiseFile(String fileName) throws IOException {
		File file = new File(fileName);
		if (!file.exists()) {
			file.createNewFile();
		}
		return file;
=======
	public static void main(String[] args) {
		MainApp program = new MainApp();
		program.startProgram();
	}
	
	public MainApp() {
		scanner = new Scanner(System.in);
		logic = new Logic();
	//	storage = new Storage();
		parser = new CommandParser();
		ui = new UI();
	}
