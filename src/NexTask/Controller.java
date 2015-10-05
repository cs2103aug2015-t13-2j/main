package NexTask;

import java.util.Scanner;


public class Controller {
	private static final int INDEX_OF_CMD_NAME = 0;
	private static final String INVALID_CMD = "invalid";
	private Scanner scanner;
	private Logic logic;
	//private Storage storage;
	private static UI ui;
	private CommandParser parser;
	
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
		ui.displayWelcomeMessage();
		this.run();
	}
	
	
	public void run() {
		while (true) {
			ui.printCommandPrompt();
			Command newCommand = this.getUserCommand();
			if(isValid(newCommand)) {
				logic.executeUserCommand(newCommand);
			} else {
				ui.displayErrorMessage();
			}
				//ui.displayMessage(feedback);
		//	storage.updateOutputFile();
		}
	}
	
	public boolean isValid(Command command) {
		if(command.getCommandName() != "invalid") {
			return true;
		} else {
			return false;
		}
	}
	public Command getUserCommand() {
		String input = this.getUserInput();
		return parser.parse(input);
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
