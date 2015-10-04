package NexTask;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandParser {
	private static final int POSITION_PARAM_COMMAND = 0;
	private static final int POSITION_FIRST_PARAM_ARGUMENT = 1;
	private static final int POSITION_OF_TASK_TYPE = 0;
	private static final int POSITION_FIRST_TASK_FIELD = 1;
	private static final int POSITION_TASK_NUMBER = 0;
	private static final int POSITION_OF_FIELD_TO_EDIT = 1;
	private static final int POSITION_OF_EDIT = 2;
	private static final int SIZE_OF_COMMAND = 2;

	private static final String USER_COMMAND_ADD = "add";
	private static final String USER_COMMAND_DELETE = "delete";
	private static final String USER_COMMAND_DISPLAY = "display";
	private static final String USER_COMMAND_EDIT = "edit";
	//private static final String USER_COMMAND_SEARCH = "search";
	//private static final String USER_COMMAND_SAVE = "save";
	//private static final String USER_COMMAND_COMPLETE = "complete";
	//private static final String USER_COMMAND_ARCHIVE = "archive";
	private static final String USER_COMMAND_EXIT = "exit";

	private static final String TASK_TYPE_EVENT = "event";
	private static final String TASK_TYPE_DEADLINE = "deadline";
	private static final String TASK_TYPE_TODO = "todo";
	
	private static final String MSG_PLEASE_PROVIDE_INT = "Invalid Argument. Please enter integer.";
	private static final String MSG_PLEASE_PROVIDE_VALID_TASK_TYPE = "Task type can either be \"event\" \"deadline\" or \"todo\" ";
	
	private static final String REGEX_WHITESPACES = "[\\s,]+";

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
		case USER_COMMAND_EXIT:
			command = initExitCommand();
			break;
		default:
			command = initInvalidCommand();
		}

		return command;
	}
	
	public ArrayList<String> splitString(String arguments) {
		String[] strArray = arguments.trim().split(REGEX_WHITESPACES);
		return new ArrayList<String>(Arrays.asList(strArray));
	}
	
	private String getUserCommand(ArrayList<String> parameters) {
		return parameters.get(POSITION_PARAM_COMMAND);
	}

	private ArrayList<String> getUserArguments(ArrayList<String> parameters) {
		return new ArrayList<String>(parameters.subList(POSITION_FIRST_PARAM_ARGUMENT, parameters.size()));
	}
	
	private Command initInvalidCommand() {
		Command c = new Command();
		c.setCommandName("invalid");
		return c;
	}
	
	private Command initDisplayCommand() {
		Command c = new Command();
		c.setCommandName(USER_COMMAND_DISPLAY);
		return c;
	}

	private Command initExitCommand() {
		Command c = new Command();
		c.setCommandName(USER_COMMAND_EXIT);
		return c;
	}

	private Command initDeleteCommand(ArrayList<String> arguments) {
		Command c = new Command();
		c.setCommandName(USER_COMMAND_DELETE);
		int itemToDelete = retrieveTaskNumber(arguments);		
		c.setTaskNumber(itemToDelete);
		return c;
	}
	
	public Command initAddCommand(ArrayList<String> arguments) {
		Command c = new Command();
		c.setCommandName(USER_COMMAND_ADD);
		Task newTask = null;
		String taskType = arguments.get(POSITION_OF_TASK_TYPE);
		switch(taskType.toLowerCase()) {
		case TASK_TYPE_EVENT :
			newTask = createNewEvent(new ArrayList<String>(arguments.subList(POSITION_FIRST_TASK_FIELD, arguments.size())));
			break;
		case TASK_TYPE_DEADLINE :
			newTask = createNewDeadline(new ArrayList<String>(arguments.subList(POSITION_FIRST_TASK_FIELD, arguments.size())));
			break;
		case TASK_TYPE_TODO :
			newTask = createNewTodo(new ArrayList<String>(arguments.subList(POSITION_FIRST_TASK_FIELD, arguments.size())));
			break;
		default:
			System.out.println(MSG_PLEASE_PROVIDE_VALID_TASK_TYPE);
			break;	
		}
		c.setTask(newTask);
		return c;
	}
	
	private Command initEditCommand(ArrayList<String> arguments) {
		Command c = new Command();
		c.setCommandName(USER_COMMAND_EDIT);
		EditSpecification edit = new EditSpecification();
		edit.setTaskNumber(retrieveTaskNumber(arguments));
		edit.setFieldToEdit(retrieveFieldToEdit(arguments));
		edit.setTheEdit(retrieveTheEdit(arguments));
		c.setEditSpecification(edit);
		return c;
	}
	
	private Task createNewEvent(ArrayList<String> argument) {
		return new Floating("STUB");
	}
	
	private Task createNewDeadline(ArrayList<String> argument) {
		return new Floating("STUB");
	}
	
	public Task createNewTodo(ArrayList<String> argument) {
		return new Floating(concatenateName(argument));
	}
	
	private int retrieveTaskNumber(ArrayList<String> arguments) {
		int taskNumber = -1;
		try {
			taskNumber = Integer.parseInt(arguments.get(POSITION_TASK_NUMBER));
		} catch (NumberFormatException exception) {
			System.out.println(MSG_PLEASE_PROVIDE_INT);
		}
		return taskNumber;
	}
	private String retrieveFieldToEdit(ArrayList<String> arguments) {
		return arguments.get(POSITION_OF_FIELD_TO_EDIT);	
	}
	
	private String retrieveTheEdit(ArrayList<String> arguments) {
		return concatenateName(new ArrayList<String>(arguments.subList(POSITION_OF_EDIT, arguments.size())));
	}
	
	public String concatenateName(ArrayList<String> nameParts) {
		StringBuilder name = new StringBuilder();
		for(int i = 0; i < nameParts.size(); i++) {
			name.append(" " + nameParts.get(i));
		}
		return name.toString().trim();
	}
}
