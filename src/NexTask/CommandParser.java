package NexTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.*;

/**
 * Command Parser parses user input and create commands with appropriate fields
 * initialized.
 * 
 * @author Jenny
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
	private static final int POSITION_OF_DEADLINE_START = 0;
	private static final int POSITION_OF_DEADLINE_END = 3;
	private static final int POSITION_OF_EVENT_NAME = 8;
	private static final int POSITION_OF_FIELD_TO_SORT = 0;


	private static final int NUM_EDIT_ARGS = 3;
	private static final int NUM_TODO_ARGS = 1;
	private static final int NUM_DELETE_ARGS = 1;
	private static final int NUM_COMPLETE_ARGS = 1;
	private static final int NUM_EVENT_ARGS = 9;
	private static final int NUM_DEADLINE_ARGS = 4;
	private static final int NUM_SORT_ARGS = 1;
	private static final int NUM_SEARCH_ARGS = 1;


	private static final int INVALID_TASK_NUMBER = -1;
	private static final String INVALID_EVENT_FORMAT = "Please input event according to format. Type help to view manual.";
	private static final String INVALID_DATE_FORMAT = "Error parsing date.";
	private static final String INVALID_NUM_ARGUMENTS = "Not enough arguments. Type help to refer to manual.";
	private static final String INVALID_COMMAND = "Invalid Command";
	private static final String INVALID_TASK_TYPE = "Invalid task type";
	private static final String INVALID_TASK_NUM = "Please provide a valid task number.";

	private static final String USER_COMMAND_ADD = "add";
	private static final String USER_COMMAND_DELETE = "delete";
	private static final String USER_COMMAND_DISPLAY = "display";
	private static final String USER_COMMAND_EDIT = "edit";
	private static final String USER_COMMAND_EXIT = "exit";
	private static final String USER_COMMAND_STORE = "store";
	private static final String USER_COMMAND_INVALID = "invalid";
	private static final String USER_COMMAND_SEARCH = "search";
	private static final String USER_COMMAND_COMPLETE = "complete";
	private static final String USER_COMMAND_HELP = "help";
	private static final String USER_COMMAND_UNDO = "undo";
	private static final String USER_COMMAND_SORT = "sort";
	private static final String USER_COMMAND_ARCHIVE = "archive";

	private static final String TASK_TYPE_EVENT = "event";
	private static final String TASK_TYPE_DEADLINE = "deadline";
	private static final String TASK_TYPE_TODO = "todo";

	private static final String EVENT_KEYWORD_START = "start";
	private static final String EVENT_KEYWORD_END = "end";

	private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd/MM/yy h:mm a");

	private static Logger logger = Logger.getLogger("CommandParser");

	/**
	 * Parses user input and returns a command object with appropriate fields
	 * initialized.
	 * 
	 * @param userInput
	 * @return instance of Command with appropriate fields initialized.
	 */
	public Command parse(String userInput) {
		// logger.log(Level.INFO, "going to start parsing");
		ArrayList<String> parameters = splitString(userInput);
		String userCommand = getCommand(parameters);
		ArrayList<String> arguments = getCommandDetails(parameters);
		Command command = null;
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
			command = initStoreCommand();
			break;
		case USER_COMMAND_SEARCH:
			command = initSearchCommand(arguments);
			break;
		case USER_COMMAND_UNDO:
			command = initUndoCommand();
			break;
		case USER_COMMAND_SORT:
			command = initSortCommand(arguments);
			break;
		case USER_COMMAND_HELP:
			command = initHelpCommand();
			break;
		case USER_COMMAND_COMPLETE:
			command = initCompleteCommand(arguments);
			break;
		case USER_COMMAND_ARCHIVE:
			command = initArchiveCommand();
			break;
		case USER_COMMAND_EXIT:
			command = initExitCommand();
			break;
		default:
			command = initInvalidCommand(INVALID_COMMAND);
		}
		// logger.log(Level.INFO, "end of parsing");
		return command;
	}

	/**
	 * Initializes an add command.
	 * 
	 * @param commandDetails
	 *            The details for the add command.
	 * @return Command object with "task" initialized accordingly. if the user
	 *         does not provide an appropriate command type, an invalid command
	 *         will be initialized.
	 */
	private Command initAddCommand(ArrayList<String> commandDetails) {
		// logger.log(Level.INFO, "going to start parse add command");
		Command addCommand;
		String taskType = commandDetails.get(POSITION_OF_TASK_TYPE);
		ArrayList<String> taskDetails = new ArrayList<String>(
				commandDetails.subList(POSITION_OF_FIRST_TASK_FIELD, commandDetails.size()));
		switch (taskType.toLowerCase()) {
		case TASK_TYPE_EVENT:
			addCommand = initAddEventCommand(taskDetails);
			break;
		case TASK_TYPE_DEADLINE:
			addCommand = initAddDeadlineCommand(taskDetails);
			break;
		case TASK_TYPE_TODO:
			addCommand = initAddTodoCommand(taskDetails);
			break;
		default:
			addCommand = initInvalidCommand(INVALID_TASK_TYPE);
			break;
		}
		// logger.log(Level.INFO, "end of parsing add command");
		return addCommand;
	}

	/**
	 * Initializes an add command for events.
	 * 
	 * @param eventDetails
	 *            The details regarding the event.
	 * @return Command object with "task" initialized as a new event. If the
	 *         user does not enter event details in the appropriate format, an
	 *         invalid command will be initialized.
	 */
	private Command initAddEventCommand(ArrayList<String> eventDetails) {
		Command cmd = new Command();
		if (isCorrectNumArguments(TASK_TYPE_EVENT, eventDetails)) {
			Event e = parseEvent(eventDetails);
			if (e.getName().equals(INVALID_EVENT_FORMAT)) {
				// logger.log(Level.INFO, "invalid event format, initializing
				// invalid command");
				cmd = initInvalidCommand(INVALID_EVENT_FORMAT);
			} else if (e.getName().equals(INVALID_DATE_FORMAT)) {
				// logger.log(Level.INFO, "invalid data format, initializing
				// invalid command");
				cmd = initInvalidCommand(INVALID_DATE_FORMAT);
			} else {
				assert(e != null);
				cmd.setCommandName(USER_COMMAND_ADD);
				cmd.setTask(e);
			}
		} else {
			// logger.log(Level.INFO, "not enough arguments provided,
			// initializing invalid command");
			cmd = initInvalidCommand(INVALID_NUM_ARGUMENTS);
		}
		return cmd;
	}

	/**
	 * Parses event details and creates an Event object.
	 * 
	 * @param eventDetails
	 * @return An event object with fields initialized according. If the user
	 *         does not enter in event details in the correct format, an invalid
	 *         event will be created.
	 */
	private Event parseEvent(ArrayList<String> eventDetails) {
		assert(eventDetails.size() > 0);
		// logger.log(Level.INFO, "going to start parse event");

		if (eventDetails.contains(EVENT_KEYWORD_START) && eventDetails.contains(EVENT_KEYWORD_END)) {
			// Get start date from input
			int indexOfStart = eventDetails.indexOf(EVENT_KEYWORD_START);
			ArrayList<String> startDateParts = new ArrayList<String>(
					eventDetails.subList(indexOfStart + 1, indexOfStart + 4));
			String startDateString = concatenate(startDateParts);
			// Get end date from input
			int indexOfEnd = eventDetails.indexOf(EVENT_KEYWORD_END);
			ArrayList<String> endDateParts = new ArrayList<String>(
					eventDetails.subList(indexOfEnd + 1, indexOfEnd + 4));
			String endDateString = concatenate(endDateParts);
			// Get name from input
			ArrayList<String> nameParts = new ArrayList<String>(
					eventDetails.subList(POSITION_OF_EVENT_NAME, eventDetails.size()));
			String name = concatenate(nameParts);

			Date startDate = new Date();
			Date endDate = new Date();

			try {
				startDate = DATE_TIME_FORMAT.parse(startDateString);
				endDate = DATE_TIME_FORMAT.parse(endDateString);
			} catch (ParseException e) {
				logger.log(Level.WARNING, "error parsing event date");
				return new Event(INVALID_DATE_FORMAT);
			}
			// logger.log(Level.INFO, "done parsing event");
			return new Event(startDate, endDate, name);
		} else {
			return new Event(INVALID_EVENT_FORMAT);
		}
	}

	/**
	 * Initializes an add command for deadlines.
	 * 
	 * @param deadlineDetails
	 *            The details regarding the deadline.
	 * @return Command object with "task" initialized as a new deadline. If the
	 *         user does not enter deadline details in the appropriate format,
	 *         an invalid command will be initialized.
	 */
	private Command initAddDeadlineCommand(ArrayList<String> deadlineDetails) {
		Command cmd = new Command();
		if (isCorrectNumArguments(TASK_TYPE_DEADLINE, deadlineDetails)) {
			Deadline d = parseDeadline(deadlineDetails);
			if (d.getName().equals(INVALID_DATE_FORMAT)) {
				cmd = initInvalidCommand(INVALID_DATE_FORMAT);
			} else {
				cmd.setCommandName(USER_COMMAND_ADD);
				cmd.setTask(d);
			}
		} else {
			cmd = initInvalidCommand(INVALID_NUM_ARGUMENTS);
		}
		return cmd;
	}

	/**
	 * Parses deadline details and creates an Deadline object.
	 * 
	 * @param deadlineDetails
	 * @return An deadline object with fields initialized according. If the user
	 *         does not enter in deadline details in the correct format, an
	 *         invalid deadline will be created.
	 */
	private Deadline parseDeadline(ArrayList<String> deadlineDetails) {
		assert(deadlineDetails.size() > 0);
		ArrayList<String> dateParts = new ArrayList<String>(
				deadlineDetails.subList(POSITION_OF_DEADLINE_START, POSITION_OF_DEADLINE_END));
		String date = concatenate(dateParts);

		ArrayList<String> nameParts = new ArrayList<String>(
				deadlineDetails.subList(POSITION_OF_DEADLINE_END, deadlineDetails.size()));
		String name = concatenate(nameParts);

		Date dueBy = new Date();
		try {
			dueBy = DATE_TIME_FORMAT.parse(date);
		} catch (ParseException e) {
			return new Deadline(INVALID_DATE_FORMAT);
		}
		return new Deadline(name, dueBy);
	}

	/**
	 * Initializes an add command for todo tasks.
	 * 
	 * @param todoDetails
	 *            The details for the Todo task.
	 * @return Command object with "task" initialized as a Floating task. If the
	 *         user does not enter a name for todo task, an invalid command will
	 *         be initialized.
	 */
	private Command initAddTodoCommand(ArrayList<String> todoDetails) {
		Command cmd = new Command();
		if (isCorrectNumArguments(TASK_TYPE_TODO, todoDetails)) {
			cmd.setCommandName(USER_COMMAND_ADD);
			cmd.setTask(parseTodo(todoDetails));
		} else {
			cmd = initInvalidCommand(INVALID_NUM_ARGUMENTS);
		}
		return cmd;
	}

	/**
	 * Create new todo task.
	 * 
	 * @param todoDetails
	 *            The details for the Todo task.
	 * @return Task object with name initialized according to input.
	 */
	private Floating parseTodo(ArrayList<String> todoDetails) {
		return new Floating(concatenate(todoDetails));
	}

	/**
	 * Initialize an edit command.
	 * 
	 * @param arguments
	 * @return Command object with "editSpecification" initialized accordingly.
	 *         If user does not input command details in the correct format, an
	 *         invalid command will be initialzed.
	 * 
	 */
	private Command initEditCommand(ArrayList<String> arguments) {
		Command cmd = new Command();
		EditSpecification edit = new EditSpecification();
		if (isCorrectNumArguments(USER_COMMAND_EDIT, arguments)) {
			int taskNumber = retrieveTaskNumber(arguments);
			if (taskNumber == INVALID_TASK_NUMBER) {
				cmd = initInvalidCommand(INVALID_TASK_NUM);
			} else {
				edit.setTaskNumber(taskNumber);
				edit.setFieldToEdit(retrieveFieldToEdit(arguments));
				edit.setTheEdit(retrieveTheEdit(arguments));

				cmd.setCommandName(USER_COMMAND_EDIT);
				cmd.setEditSpecification(edit);
			}
		} else {
			cmd = initInvalidCommand(INVALID_NUM_ARGUMENTS);
		}

		return cmd;
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
	 * Initializes an archive command.
	 * 
	 * @return Command object with commandName set to "archive"
	 */
	private Command initArchiveCommand() {
		Command c = new Command();
		c.setCommandName(USER_COMMAND_ARCHIVE);
		return c;
	}

	/**
	 * Initializes an undo command.
	 * 
	 * @return Command object with commandName set to "archive"
	 */
	private Command initUndoCommand() {
		Command c = new Command();
		c.setCommandName(USER_COMMAND_UNDO);
		return c;
	}

	/**
	 * Initializes a sort command.
	 * 
	 * @return Command object with sortField set accordingly. If the user does
	 *         not enter a field, an invalid command will be initialized.
	 */
	private Command initSortCommand(ArrayList<String> arguments) {
		Command cmd = new Command();
		String sortField;
		if (isCorrectNumArguments(USER_COMMAND_SORT, arguments)) {
			sortField = arguments.get(POSITION_OF_FIELD_TO_SORT);
			cmd.setCommandName(USER_COMMAND_SORT);
			cmd.setSortField(sortField);
		} else {
			cmd = initInvalidCommand(INVALID_NUM_ARGUMENTS);
		}
		return cmd;
	}
	

	/**
	 * Initializes a delete command.
	 * 
	 * @param arguments
	 *            The details for the delete command.
	 * @return Command object with taskNumber initialized accordingly. If the
	 *         user does not enter a valid task number, or if the user does not
	 *         provide a task number, an invalid command will be initialized.
	 */
	private Command initDeleteCommand(ArrayList<String> arguments) {
		Command cmd = new Command();
		int itemToDelete;
		if (isCorrectNumArguments(USER_COMMAND_DELETE, arguments)) {
			itemToDelete = retrieveTaskNumber(arguments);
			if (itemToDelete == INVALID_TASK_NUMBER) {
				cmd = initInvalidCommand(INVALID_TASK_NUM);
			} else {
				cmd.setCommandName(USER_COMMAND_DELETE);
				cmd.setTaskNumber(itemToDelete);
			}
		} else {
			cmd = initInvalidCommand(INVALID_NUM_ARGUMENTS);
		}
		return cmd;
	}

	/**
	 * Initializes a complete command.
	 * 
	 * @param arguments
	 *            The details for the complete command.
	 * @return Command object with taskNumber initialized accordingly. If the
	 *         user does not enter a valid task number, or if the user does not
	 *         provide a task number, an invalid command will be initialized.
	 */
	private Command initCompleteCommand(ArrayList<String> arguments) {
		Command cmd = new Command();
		int itemCompleted;
		if (isCorrectNumArguments(USER_COMMAND_COMPLETE, arguments)) {
			itemCompleted = retrieveTaskNumber(arguments);
			if (itemCompleted == INVALID_TASK_NUMBER) {
				cmd = initInvalidCommand(INVALID_TASK_NUM);
			} else {
				cmd.setCommandName(USER_COMMAND_COMPLETE);
				cmd.setTaskNumber(itemCompleted);
			}
		} else {
			cmd = initInvalidCommand(INVALID_NUM_ARGUMENTS);
		}
		return cmd;
	}

	/**
	 * Initializes a search command.
	 * 
	 * @param arguments
	 *            The details for the search command.
	 * @return Command object with searchSpecification initialized accordingly.
	 *         If user does not enter a term to search for, an invalid argument
	 *         will be initialized.
	 * 
	 */
	private Command initSearchCommand(ArrayList<String> arguments) {
		Command cmd = new Command();
		if (isCorrectNumArguments(USER_COMMAND_SEARCH, arguments)) {
			cmd.setSearchSpecification(concatenate(arguments));
			cmd.setCommandName(USER_COMMAND_SEARCH);
			
		} else {
			cmd = initInvalidCommand(INVALID_NUM_ARGUMENTS);
		}
		return cmd;
	}

	/**
	 * Initialize a store command.
	 * 
	 * @return Command object with commandName set to "store"
	 */
	private Command initStoreCommand() {
		Command c = new Command();
		c.setCommandName(USER_COMMAND_STORE);
		return c;
	}

	/**
	 * Initialize a help command.
	 * 
	 * @return Command object with commandName set to "help"
	 */
	private Command initHelpCommand() {
		Command c = new Command();
		c.setCommandName(USER_COMMAND_HELP);
		return c;
	}

	/**
	 * Initializes an invalid command.
	 * 
	 * @return Command object with commandName set to "invalid" and error
	 *         message initialized accordingly.
	 */
	private Command initInvalidCommand(String errorMessage) {
		Command c = new Command();
		c.setCommandName(USER_COMMAND_INVALID);
		c.setErrorMessage(errorMessage);
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

	// ------------------ HELPER METHODS -------------------//
	/**
	 * Helper method to retrieve the task number from arguments.
	 * 
	 * @param arguments
	 *            The details for the user command.
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
	 * Retrieve the field the user would like to edit. e.g. edit 2 name Call
	 * home. This method will retrieve "name"
	 * 
	 * @param arguments
	 *            The details for the edit command.
	 * @return The field specified by user.
	 */
	private String retrieveFieldToEdit(ArrayList<String> arguments) {
		return arguments.get(POSITION_OF_FIELD_TO_EDIT);
	}

	/**
	 * Retrieve the last argument of edit command. e.g. edit 2 name Call home.
	 * This method will retrieve "Call home."
	 * 
	 * @param arguments
	 *            The details for the edit command.
	 * @return The edit specified by user.
	 */
	private String retrieveTheEdit(ArrayList<String> arguments) {
		return concatenate(new ArrayList<String>(arguments.subList(POSITION_OF_EDIT, arguments.size())));
	}

	/**
	 * Helper method to concatenate the Strings of an ArrayList together.
	 * Initially each parts of a name are different elements of an ArrayList
	 * because of splitString. This method concatenates them to a single string.
	 * 
	 * @param nameParts
	 *            An ArrayList<String> where each element is a word in the name.
	 * @return The result string after concatenation.
	 */
	private String concatenate(ArrayList<String> nameParts) {
		StringBuilder name = new StringBuilder();
		for (int i = 0; i < nameParts.size(); i++) {
			name.append(" " + nameParts.get(i));
		}
		return name.toString().trim();
	}

	/**
	 * Helper method for parse that splits user input string by white spaces.
	 * 
	 * @param arguments
	 * @return
	 */
	private ArrayList<String> splitString(String arguments) {
		String[] strArray = arguments.trim().split(REGEX_WHITESPACES);
		return new ArrayList<String>(Arrays.asList(strArray));
	}

	/**
	 * Helper method to retrieve the command of user input.
	 * 
	 * @param parameters
	 * @return
	 */
	private String getCommand(ArrayList<String> parameters) {
		return parameters.get(POSITION_OF_COMMAND);
	}

	/**
	 * Helper method to retrieve the commandDetails entered with command.
	 * 
	 * @param commandDetails
	 * @return
	 */
	private ArrayList<String> getCommandDetails(ArrayList<String> commandDetails) {
		return new ArrayList<String>(commandDetails.subList(POSITION_OF_FIRST_ARGUMENT, commandDetails.size()));
	}

	/**
	 * Helper method that checks if the correct number of arguments are entered.
	 * 
	 * @return If at least minimum number of arguments provided return true,
	 *         otherwise return false.
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
		case USER_COMMAND_COMPLETE:
			isCorrectNumArgs = arguments.size() >= NUM_COMPLETE_ARGS;
			break;
		case USER_COMMAND_SORT:
			isCorrectNumArgs = arguments.size() >= NUM_SORT_ARGS;
			break;	
		case TASK_TYPE_TODO:
			isCorrectNumArgs = arguments.size() >= NUM_TODO_ARGS;
			break;
		case TASK_TYPE_DEADLINE:
			isCorrectNumArgs = arguments.size() >= NUM_DEADLINE_ARGS;
			break;
		case TASK_TYPE_EVENT:
			isCorrectNumArgs = arguments.size() >= NUM_EVENT_ARGS;
			break;
		case USER_COMMAND_SEARCH:
			isCorrectNumArgs = arguments.size() >= NUM_SEARCH_ARGS;
		default:
			break;
		}
		return isCorrectNumArgs;
	}
}
