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

public class Controller {
	
	private Scanner scanner;
	private Logic logic;
	//private Storage storage;
	private UI ui;
	private CommandParser parser;
	
	enum OPERATION {
		ADD, COMPLETED, DELETE, EDIT, UNDO, SEARCH, ARCHIVE, DISPLAY, SAVE, INVALID
	};
	
	public static void main(String[] args) {
		Controller program = new Controller();
		program.startProgram();
	}
	
	public Controller() {
		scanner = new Scanner(System.in);
		logic = new Logic();
	//	storage = new Storage();
		parser = new CommandParser();
		ui = new UI();
	}
	
	public void startProgram() {
		ui.displayMessage("WELCOME_MSG");
		ui.displayMessage("WELCOME_HELP_MSG");
		this.run();
	}
	
	
	public void run() {
		while (true) {
			ui.displayMessage("COMMAND_PROMPT");
			Command newCommand = this.getUserCommand();
			String feedback = logic.executeUserCommand(newCommand);
			ui.displayMessage(feedback);
		//	storage.updateOutputFile();
		}
	}
	
	public Command getUserCommand() {
		String input = this.getUserInput();
		return parser.parseUserInput(input);
	}

	public String getUserInput() {
		String userInput = "";

		if (this.scanner.hasNextLine()) {
			userInput = this.scanner.nextLine();
		} else {
			System.exit(0);
		}
		return userInput;
	}

}
