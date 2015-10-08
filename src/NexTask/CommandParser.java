package NexTask;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Command Parser parses user input and create commands with appropriate fields
 * initialized.
 * 
 * Things to note:
 * 
 * For "add" command: If the user does not provide the sufficient number of
 * arguments, a command will be returned with its task's "name" field set to
 * "Invalid Task."
 * 
 * For "edit" command": If the user does not provide a number for taskNumber
 * field, ie edit one name Call Manager instead of edit 1 name Call Manager the
 * taskNumber field of the command's edit specification will be set to -1.
 * 
 * If the user does not provide a sufficient number of arguments, the taskNumber
 * field of the command's edit specification will be set to -2
 * 
 * For "delete" command: If the user does not provide a number for taskNumber
 * field, the command's task number field will be set to -1.
 * 
 * @author Jenny
 *
 */
public class CommandParser {
	private static final String REGEX_WHITESPACES = "[\\s,]+";
	
	private static final int POSITION_OF_COMMAND = 0;
	private static final int POSITION_OF_FIRST_ARGUMENT = 1;
	private static final int POSITION_OF_TASK_TYPE = 0;
	private static final int POSITION_OF_FIRST_TASK_FIELD = 1;
	private static final int POSITION_OF_TASK_NUMBER = 0;
	private static final int POSITION_OF_FIELD_TO_EDIT = 1;
	private static final int POSITION_OF_EDIT = 2;

	private static final int INVALID_TASK_NUMBER = -1;
	private static final int INVALID_NUM_ARGS = -2;
	private static final String INVALID_TASK = "Invalid Task.";

	private static final int NUM_EDIT_ARGS = 3;
	private static final int NUM_TODO_ARGS = 1;
	private static final int NUM_DELETE_ARGS = 1;
	// private static final int NUM_EVENT_ARGS = 3;
	// private static final int NUM_DEADLINE_ARGS = 2;

	private static final String USER_COMMAND_ADD = "add";
	private static final String USER_COMMAND_DELETE = "delete";
	private static final String USER_COMMAND_DISPLAY = "display";
	private static final String USER_COMMAND_EDIT = "edit";
	private static final String USER_COMMAND_EXIT = "exit";
	private static final String USER_COMMAND_STORE = "store";
	
	private static final String USER_COMMAND_INVALID = "invalid";
	// private static final String USER_COMMAND_SEARCH = "search";
	// private static final String USER_COMMAND_COMPLETE = "complete";
	// private static final String USER_COMMAND_ARCHIVE = "archive";
	
	private static final String TASK_TYPE_EVENT = "event";
	private static final String TASK_TYPE_DEADLINE = "deadline";
	private static final String TASK_TYPE_TODO = "todo";

	/**
	 * Parses user input and return command object with appropriate fields 
	 * initialized.
	 * @param userInput
	 * @return instance of Command with appropriate fields intiailized.
	 */
	public Command parse(String userInput) {
		Command command = new Command();
		ArrayList<String> parameters = splitString(userInput);
		String userCommand = getUserCommand(parameters);
		ArrayList<String> arguments = getUserArguments(parameters);

		switch (userCommand.toLowerCase()) {
		case USER_COMMAND_ADD:
			command = initAddCommand(arguments);
			break;
		case USER_COMMAND_DELETE:
			command = initDeleteCommand(arguments);
			break;
		case USER_COMMAND_DISPLAY:
			command = initDisplayCommand();
			break;
		case USER_COMMAND_EDIT:
			command = initEditCommand(arguments);
			break;
		case USER_COMMAND_STORE:
			command = initStoreCommand(arguments);
			break;	
		case USER_COMMAND_EXIT:
			command = initExitCommand();
			break;
		default:
			command = initInvalidCommand();
		}
		return command;
	}

	/**
	 * Splits user input string by white spaces.
	 * 
	 * @param arguments
	 * @return
	 */
	private ArrayList<String> splitString(String arguments) {
		String[] strArray = arguments.trim().split(REGEX_WHITESPACES);
		return new ArrayList<String>(Arrays.asList(strArray));
	}

	/**
	 * Retrieve the command of user input.
	 * 
	 * @param parameters
	 * @return
	 */
	private String getUserCommand(ArrayList<String> parameters) {
		return parameters.get(POSITION_OF_COMMAND);
	}

	/**
	 * Retrieve the arguments of the user input.
	 * 
	 * @param parameters
	 * @return
	 */
	private ArrayList<String> getUserArguments(ArrayList<String> parameters) {
		return new ArrayList<String>(parameters.subList(POSITION_OF_FIRST_ARGUMENT, parameters.size()));
	}
	
	private Command initStoreCommand(ArrayList<String> parameters) {
		Command c = new Command();
		//String directory = arguments.get(0);
		c.setCommandName(USER_COMMAND_STORE);
		if(parameters.size() != 0){
			c.setDirectory(parameters.get(0));
		} 
		return c;
	}

	/**
	 * Initializes an invalid command.
	 * 
	 * @return Command object with commandName set to "invalid"
	 */
	private Command initInvalidCommand() {
		Command c = new Command();
		c.setCommandName(USER_COMMAND_INVALID);
		return c;
	}

	/**
	 * Initializes a display command.
	 * 
	 * @return Command object with commandName set to "display"
	 */
	private Command initDisplayCommand() {
		Command c = new Command();
		c.setCommandName(USER_COMMAND_DISPLAY);
		return c;
	}
	
	/**
	 * Initializes an exit command.
	 * 
	 * @return Command object with commandName set to "exit"
	 */
	private Command initExitCommand() {
		Command c = new Command();
		c.setCommandName(USER_COMMAND_EXIT);
		return c;
	}

	/**
	 * Initializes a delete command. 
	 * 
	 * @param arguments The details for the delete command.
	 * @return Command object with taskNumber initialized accordingly
	 * 	if user inputs a valid number, taskNumber will be set to that num
	 * 	if user does not provide valid num (input cannout be parser),
	 * 		taskNumber will be set to -1.
	 * 	if user does not provide any arguments, taskNumber will be set to -2.
	 */
	private Command initDeleteCommand(ArrayList<String> arguments) {
		Command c = new Command();
		int itemToDelete;
		if(isCorrectNumArguments(USER_COMMAND_DELETE, arguments)) {
			itemToDelete = retrieveTaskNumber(arguments);
		} else {
			itemToDelete = INVALID_NUM_ARGS;
		}
		c.setCommandName(USER_COMMAND_DELETE);
		c.setTaskNumber(itemToDelete);
		return c;
	}

	/**
	 * Initializes an add command.
	 * 
	 * @param arguments The details for the add command.
	 * @return Command object with "task" initialized accordingly.
	 * 	if the user does not provide an appropriate command type,
	 * 	an invalid command will be initialized.
	 */
	private Command initAddCommand(ArrayList<String> arguments) {
		Command addCommand = new Command();
		String taskType = arguments.get(POSITION_OF_TASK_TYPE);
		switch (taskType.toLowerCase()) {
		case TASK_TYPE_EVENT:
			break;
		case TASK_TYPE_DEADLINE:
			break;
		case TASK_TYPE_TODO:
			addCommand = initAddTodoCommand(
					new ArrayList<String>(arguments.subList(POSITION_OF_FIRST_TASK_FIELD, arguments.size())));
			break;
		default:
			addCommand = initInvalidCommand();
			break;
		}
		return addCommand;
	}

	/**
	 * Initialize a Todo command. 
	 * 
	 * @param arguments The details for the Floating task.
	 * @return Command object with "task" initialized as a Floating task.
	 * 	if no argument provided, 
	 * 	the task's "name" will be set to "Invalid task."
	 * 	otherwise, "name" will be set according to user input.
	 */
	private Command initAddTodoCommand(ArrayList<String> arguments) {
		Command c = new Command();
		Task newTask;
		if (isCorrectNumArguments(TASK_TYPE_TODO, arguments)) {
			newTask = createNewTodo(arguments);
		} else {
			newTask = new Floating(INVALID_TASK);
		}
		c.setCommandName(USER_COMMAND_ADD);
		c.setTask(newTask);
		return c;
	}

	/**
	 * Initialize an edit command.
	 * 
	 * @param arguments
	 * @return Command object with "editSpecification" initialized accordingly.
	 * 	if correct number of arguments provided and task number can be parsed, 
	 * 		editSpecification will be initialized according to user input.
	 * 	if taskNumber cannot be parsed, editSpecification's taskNumber 
	 * 		will be set to -1. Everything else will be default.
	 * 	if an insufficient arguments provided, editSpecification's taskNumber
	 * 		will be set to -2. Everything else will be default.
	 */
	private Command initEditCommand(ArrayList<String> arguments) {
		Command c = new Command();
		EditSpecification edit = new EditSpecification();
		if (isCorrectNumArguments(USER_COMMAND_EDIT, arguments)) {
			int taskNumber = retrieveTaskNumber(arguments);
			if (taskNumber == INVALID_TASK_NUMBER) {
				edit.setTaskNumber(INVALID_TASK_NUMBER);
			} else {
				edit.setTaskNumber(taskNumber);
				edit.setFieldToEdit(retrieveFieldToEdit(arguments));
				edit.setTheEdit(retrieveTheEdit(arguments));
			}
		} else {
			edit.setTaskNumber(INVALID_NUM_ARGS);
		}
		c.setCommandName(USER_COMMAND_EDIT);
		c.setEditSpecification(edit);
		return c;
	}

	/**
	 * Create new todo task.
	 * 
	 * @param argument The details for the Floating task.
	 * @return Task object with name initialized according to input.
	 */
	private Task createNewTodo(ArrayList<String> argument) {
		return new Floating(concatenateName(argument));
	}

	/**
	 * Helper method to retrieve the task number.
	 * 
	 * @param arguments The details for the user command.
	 * @return The appropriate task number if parse is successful, -1 otherwise.
	 */
	private int retrieveTaskNumber(ArrayList<String> arguments) {
		int taskNumber;
		try {
			taskNumber = Integer.parseInt(arguments.get(POSITION_OF_TASK_NUMBER));
		} catch (NumberFormatException exception) {
			taskNumber = INVALID_TASK_NUMBER;
		}
		return taskNumber;
	}

	/**
	 * Retrieve the field the user would like to edit. 
	 * e.g. edit 2 name Call home. This method will retrieve "name"
	 * @param arguments The details for the edit command.
	 * @return The field specified by user.
	 */
	private String retrieveFieldToEdit(ArrayList<String> arguments) {
		return arguments.get(POSITION_OF_FIELD_TO_EDIT);
	}

	/**
	 * Retrieve the last argument of edit command. 
	 * e.g. edit 2 name Call home.
	 * This method will retrieve "Call home."
	 * 
	 * @param arguments The details for the edit command.
	 * @return The edit specified by user.
	 */
	private String retrieveTheEdit(ArrayList<String> arguments) {
		return concatenateName(new ArrayList<String>(arguments.subList(POSITION_OF_EDIT, arguments.size())));
	}

	/**
	 * Helper method to concatenate the Strings of an ArrayList together.
	 * Initially each parts of a name are different elements of an ArrayList
	 * because of splitString. This method concatenates them to a single string.
	 * @param nameParts An ArrayList<String> where each element is a word in
	 * 	the name.
	 * @return The result string after concatenation.
	 */
	private String concatenateName(ArrayList<String> nameParts) {
		StringBuilder name = new StringBuilder();
		for (int i = 0; i < nameParts.size(); i++) {
			name.append(" " + nameParts.get(i));
		}
		return name.toString().trim();
	}

	/**
	 * Helper method that checks if the correct number of arguments are entered.
	 * 
	 * @return If at least minimum number of arguments provided return true, 
	 * otherwise return false.
	 */
	private boolean isCorrectNumArguments(String command, ArrayList<String> arguments) {
		boolean isCorrectNumArgs = false;
		switch (command) {
		case USER_COMMAND_EDIT:
			isCorrectNumArgs = arguments.size() >= NUM_EDIT_ARGS;
			break;
		case USER_COMMAND_DELETE:
			isCorrectNumArgs = arguments.size() >= NUM_DELETE_ARGS;
			break;	
		case TASK_TYPE_TODO:
			isCorrectNumArgs = arguments.size() >= NUM_TODO_ARGS;
			break;
		default:
			System.out.println("What are you checking?");
		}
		return isCorrectNumArgs;
	}
	/*
	 * private Task createNewEvent(ArrayList<String> argument) { return new
	 * Floating("STUB"); }
	 * 
	 * private Task createNewDeadline(ArrayList<String> argument) { return new
	 * Floating("STUB"); }
	 */
}
