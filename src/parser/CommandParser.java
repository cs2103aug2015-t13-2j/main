package parser;

import java.util.logging.Logger;

//@@author A0145695R

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;

import command.*;
import parser.DateTimeParser;
import NexTask.EditSpecification;
import NexTask.Task;

/**
 * CommandParser parses user input and create Command objects with appropriate
 * fields initialized.
 *
 */
public class CommandParser implements java.io.Serializable {
	// Indices
	private static final int POSITION_OF_CMD = 0;
	private static final int POSITION_OF_CMD_ARGS = 1;

	// Size
	private static final String EMPTY_STRING = "";

	// Command types
	private static final String USER_COMMAND_ADD = "add";
	private static final String USER_COMMAND_DELETE = "delete";
	private static final String USER_COMMAND_VIEW_INCOMPLETE = "view incomplete";
	private static final String USER_COMMAND_EDIT = "edit";
	private static final String USER_COMMAND_EXIT = "exit";
	private static final String USER_COMMAND_STORE = "store";
	private static final String USER_COMMAND_SEARCH = "search";
	private static final String USER_COMMAND_COMPLETE = "complete";
	private static final String USER_COMMAND_UNDO = "undo";
	private static final String USER_COMMAND_SORT = "sort";
	private static final String USER_COMMAND_VIEW_COMPLETED = "view completed";
	private static final String USER_COMMAND_RETRIEVE = "retrieve";
	private static final String INVALID = "invalid";

	// Task types
	private static final String TASK_TYPE_EVENT = "event";
	private static final String TASK_TYPE_DEADLINE = "deadline";
	private static final String TASK_TYPE_TODO = "todo";

	// Keywords
	private static final String KW_START = "start";
	private static final String KW_END = "end";
	private static final String KW_ON = "on";
	private static final String KW_BY = "by";
	private static final String KW_CLEAR = "clear";

	// Patterns
	private static final String PATTERN_DATE = "\"([^\"]*)\"";
	private static final String PATTERN_TASK_NAME = "([\\w\\s]+)";

	// Error Messages
	private static final String ERROR_INVALID_CMD = "Please enter a valid command.";
	private static final String ERROR_NO_NAME_FOUND = "Pleae provide a name for your task.";
	private static final String ERROR_INVALID_DATE_FORMAT = "Invalid date. Make sure your date is valid and enter \"help\" if you need to see accepted date formats.";
	private static final String ERROR_INTEGER_NOT_FOUND = "Please specify task number as an integer.";
	private static final String ERROR_INVALID_NUM_ARGS = "Invalid number of arguments. Enter \"help\" to view command format.";
	private static final String ERROR_NO_TASK_NUM = "Please provide a task number.";
	private static final String ERROR_NO_SORT_FIELD = "Please specify field you wish to sort by.";
	private static final String ERROR_NO_SEARCH_FIELD = "Please specify what you would like to search for.";

	// Log Messages
	private static final String LOG_MSG_INVALID_CMD = "Command Parser: Please enter a valid command.";
	private static final String LOG_MSG_INVALID_DATE_FMT = "Command Parser: Error parsing date.";
	private static final String LOG_MSG_NO_NAME_FOUND = "Command Parser: User did not provide task name.";

	private static Logger logger = Logger.getLogger("CommandParser");

	public void createLog() {

	}

	public Command parse(String userInput) {
		String userCommand;
		// check if user input empty
		String commandArgs;

		if (userInput.equals(USER_COMMAND_VIEW_COMPLETED)) {
			userCommand = USER_COMMAND_VIEW_COMPLETED;
			commandArgs = EMPTY_STRING;
		} else if (userInput.equals(USER_COMMAND_VIEW_INCOMPLETE)) {
			userCommand = USER_COMMAND_VIEW_INCOMPLETE;
			commandArgs = EMPTY_STRING;
		} else {
			String[] input = userInput.split(" ", 2);
			if (input.length > 1) {
				userCommand = getCommand(input);
				commandArgs = getCommandArgs(input);
			} else {
				userCommand = userInput.trim();
				commandArgs = EMPTY_STRING;
			}
		}

		switch (userCommand.toLowerCase().trim()) {
		case USER_COMMAND_ADD:
			return initAddCommand(commandArgs);
		case USER_COMMAND_EDIT:
			return initEditCommand(commandArgs);
		case USER_COMMAND_DELETE:
			return initDeleteCommand(commandArgs);
		case USER_COMMAND_COMPLETE:
			return initCompleteCommand(commandArgs);
		case USER_COMMAND_SEARCH:
			return initSearchCommand(commandArgs);
		case USER_COMMAND_SORT:
			return initSortCommand(commandArgs);
		case USER_COMMAND_STORE:
			return initStoreCommand(commandArgs);
		case USER_COMMAND_VIEW_COMPLETED:
			return initViewCompletedCommand(USER_COMMAND_VIEW_COMPLETED);
		case USER_COMMAND_VIEW_INCOMPLETE:
			return initViewIncompleteCommand(USER_COMMAND_VIEW_INCOMPLETE);
		/*
		 * case USER_COMMAND_HELP: return initHelpCommand(USER_COMMAND_HELP);
		 */
		case USER_COMMAND_UNDO:
			return initUndoCommand(USER_COMMAND_UNDO);
		case USER_COMMAND_EXIT:
			return initCommand(USER_COMMAND_EXIT);
		case USER_COMMAND_RETRIEVE:
			return initRetrieveCommand(USER_COMMAND_RETRIEVE);
		default:
			return initInvalidCommand(ERROR_INVALID_CMD);
		}
	}

	public String getCommand(String[] input) {
		return input[POSITION_OF_CMD];
	}

	public String getCommandArgs(String[] input) {
		return input[POSITION_OF_CMD_ARGS];
	}

	private Command initAddCommand(String commandArgs) {
		Add cmd = new Add();
		if (isEvent(commandArgs.toLowerCase().trim())) {
			Task newEvent = parseEvent(commandArgs);
			if (newEvent.getName().equals(INVALID)) {
				return initInvalidCommand(ERROR_INVALID_DATE_FORMAT);
			} else if (newEvent.getName().equals(EMPTY_STRING)) {
				return initInvalidCommand(ERROR_NO_NAME_FOUND);
			} else {
				cmd.setCommandName(USER_COMMAND_ADD);
				cmd.setTask(newEvent);
			}
		} else if (isDeadline(commandArgs.toLowerCase().trim())) {
			Task newDeadline = parseDeadline(commandArgs);
			if (newDeadline.getName().equals(INVALID)) {
				return initInvalidCommand(ERROR_INVALID_DATE_FORMAT);
			} else if (newDeadline.getName().equals(EMPTY_STRING)) {
				return initInvalidCommand(ERROR_NO_NAME_FOUND);
			} else {
				cmd.setCommandName(USER_COMMAND_ADD);
				cmd.setTask(newDeadline);
			}
		} else {
			Task newTodo = parseTodo(commandArgs);
			if (newTodo.getName().equals(INVALID)) {
				return initInvalidCommand(ERROR_NO_NAME_FOUND);
			} else {
				cmd.setCommandName(USER_COMMAND_ADD);
				cmd.setTask(newTodo);
			}
		}
		return cmd;
	}

	/**
	 * Parses user input, creates an EditSpecification, and initializes an
	 * Command object with editSpecificatoin field initialized accordingly.
	 * 
	 * @param commandArgs
	 *            details for the edit command
	 * @return edit command if user inputs is valid, invalid command otherwise.
	 */
	private Command initEditCommand(String commandArgs) {
		Edit cmd = new Edit();
		EditSpecification edit = new EditSpecification();
		String[] editArgs = commandArgs.trim().split(" ", 3);

		String taskNumber;
		String fieldOrClear;
		String argumentsForEdit;
		try {
			taskNumber = editArgs[0].trim();
			fieldOrClear = editArgs[1].trim();
			argumentsForEdit = editArgs[2].trim();
		} catch (IndexOutOfBoundsException e) {
			return initInvalidCommand(ERROR_INVALID_NUM_ARGS);
		}

		try {
			edit.setTaskNumber(Integer.parseInt(taskNumber));
		} catch (NumberFormatException e) {
			return initInvalidCommand(ERROR_INTEGER_NOT_FOUND);
		}

		if (argumentsForEdit.equals(EMPTY_STRING)) {
			return initInvalidCommand(ERROR_INVALID_NUM_ARGS);
		}

		switch (fieldOrClear) {
		case KW_CLEAR:
			edit.setFieldToClear(argumentsForEdit);
			break;
		default:
			edit.setFieldToEdit(editArgs[1]);
			edit.setTheEdit(argumentsForEdit);
		}

		cmd.setEditSpecification(edit);
		cmd.setCommandName(USER_COMMAND_EDIT);
		return cmd;
	}

	private Command initDeleteCommand(String commandArgs) {
		Delete cmd = new Delete();
		cmd.setCommandName(USER_COMMAND_DELETE);
		if (commandArgs.trim().equals(EMPTY_STRING)) {
			return initInvalidCommand(ERROR_NO_TASK_NUM);
		} else {
			try {
				cmd.setTaskNumber(Integer.parseInt(commandArgs.trim()));
			} catch (NumberFormatException e) {
				return initInvalidCommand(ERROR_INTEGER_NOT_FOUND);
			}
		}
		return cmd;
	}

	private Command initCompleteCommand(String commandArgs) {
		Completed cmd = new Completed();
		cmd.setCommandName(USER_COMMAND_COMPLETE);
		if (commandArgs.trim().equals(EMPTY_STRING)) {
			return initInvalidCommand(ERROR_NO_TASK_NUM);
		} else {
			try {
				cmd.setTaskNumber(Integer.parseInt(commandArgs.trim()));
			} catch (NumberFormatException e) {
				return initInvalidCommand(ERROR_INTEGER_NOT_FOUND);
			}
		}
		return cmd;
	}

	private Command initSearchCommand(String commandArgs) {
		Search cmd = new Search();
		if (commandArgs.trim().equals(EMPTY_STRING)) {
			return initInvalidCommand(ERROR_NO_SEARCH_FIELD);
		} else {
			cmd.setCommandName(USER_COMMAND_SEARCH);
			cmd.setSearchSpecification(commandArgs.trim());
		}
		return cmd;
	}

	private Command initSortCommand(String commandArgs) {
		Sort cmd = new Sort();
		if (commandArgs.trim().equals(EMPTY_STRING)) {
			return initInvalidCommand(ERROR_NO_SORT_FIELD);
		} else {
			cmd.setCommandName(USER_COMMAND_SORT);
			cmd.setSortField(commandArgs.trim());

		}
		return cmd;
	}

	private Command initStoreCommand(String commandArgs) {
		Store cmd = new Store();
		cmd.setCommandName(USER_COMMAND_STORE);
		cmd.setDirectory(commandArgs.trim());
		return cmd;
	}

	private Command initRetrieveCommand(String commandName) {
		Retrieve cmd = new Retrieve();
		cmd.setCommandName(commandName.trim());
		return cmd;
	}

	private Command initCommand(String commandName) {
		InitCommand cmd = new InitCommand();
		cmd.setCommandName(commandName.trim());
		return cmd;
	}

	private Command initViewIncompleteCommand(String commandName) {
		ViewIncomplete cmd = new ViewIncomplete();
		cmd.setCommandName(commandName.trim());
		return cmd;
	}

	private Command initUndoCommand(String commandName) {
		Undo cmd = new Undo();
		cmd.setCommandName(commandName.trim());
		return cmd;
	}

	private Command initViewCompletedCommand(String commandName) {
		ViewCompleted cmd = new ViewCompleted();
		cmd.setCommandName(commandName.trim());
		return cmd;
	}

	private Command initInvalidCommand(String errorMessage) {
		assert (!errorMessage.equals(EMPTY_STRING));
		InitCommand c = new InitCommand();
		c.setCommandName(INVALID);
		c.setErrorMessage(errorMessage);
		return c;
	}

	public Task parseEvent(String args) {
		Task newEvent = new Task();
		newEvent.setTaskType(TASK_TYPE_EVENT);
		boolean hasStart = false;
		boolean hasEnd = false;
		if (args.contains(KW_START)) {
			DateTime start = DateTimeParser.parse(getDateTime(args, KW_START));
			if (start != null) {
				newEvent.setStart(start);
				newEvent.setName(getTaskName(args, KW_START));
				hasStart = true;
			} else {
				return new Task(INVALID);
			}
		}
		if (args.contains(KW_END)) {
			DateTime end = DateTimeParser.parse(getDateTime(args, KW_END));
			if (end != null) {
				if (!hasStart) {
					newEvent.setName(getTaskName(args, KW_END));
				}
				newEvent.setEnd(end);
				hasEnd = true;
			} else {
				return new Task(INVALID);
			}
		}
		if (!hasStart) {
			DateTime start = newEvent.getEnd().minusHours(1);
			newEvent.setStart(start);
		}
		if (!hasEnd) {
			DateTime end = newEvent.getStart().plusHours(1);
			newEvent.setEnd(end);
		}
		return newEvent;
	}

	public Task parseDeadline(String args) {
		Task newDeadline = new Task();
		newDeadline.setTaskType(TASK_TYPE_DEADLINE);
		if (hasDateTime(args)) {
			DateTime completeBy = DateTimeParser.parse(getDateTime(args, getKeyword(args)));
			if (completeBy != null) {
				newDeadline.setCompleteBy(completeBy);
				newDeadline.setName(getTaskName(args, getKeyword(args)));
			} else {
				return new Task(INVALID);
			}
		} else {
			return parseTodo(args);
		}
		return newDeadline;
	}

	public String getKeyword(String args) {
		if (args.contains(KW_ON)) {
			return KW_ON;
		} else {
			return KW_BY;
		}
	}

	public Task parseTodo(String args) {
		Task newTodo = new Task();
		newTodo.setTaskType(TASK_TYPE_TODO);
		if ((args.trim()).equals(EMPTY_STRING)) {
			return new Task(INVALID);
		} else {
			newTodo.setName(args);
		}
		return newTodo;
	}

	/**
	 * Helper method to get task name. Task name appears before first keyword.
	 */
	private String getTaskName(String args, String keyword) {
		Pattern taskNamePattern = Pattern.compile(PATTERN_TASK_NAME + keyword);
		Matcher m = taskNamePattern.matcher(args.trim());
		if (m.find()) {
			return m.group(1).trim();
		} else {
			return "";
		}
	}

	/**
	 * Given a string, determine if it has date time.
	 */
	public boolean hasDateTime(String args) {
		Pattern dateTimePattern = Pattern.compile(PATTERN_DATE);
		Matcher m = dateTimePattern.matcher(args);
		if (m.find()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Helper method to get the date time from user input. Date time are wrapped
	 * in quotes.
	 */
	public String getDateTime(String args) {
		Pattern dateTimePattern = Pattern.compile(PATTERN_DATE);
		Matcher m = dateTimePattern.matcher(args);
		if (m.find()) {
			return m.group(1);
		} else {
			return "";
		}
	}

	/**
	 * Helper method to get the date time from user input. Date time are wrapped
	 * in quotes.
	 */
	public String getDateTime(String args, String keyword) {
		Pattern dateTimePattern = Pattern.compile(keyword + " " + PATTERN_DATE);
		Matcher m = dateTimePattern.matcher(args);
		if (m.find()) {
			return m.group(1);
		} else {
			return "";
		}
	}

	private boolean isEvent(String args) {
		String start = " " + KW_START + " ";
		String end = " " + KW_END + " ";
		if (args.contains(start) || args.contains(end)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isDeadline(String addCmdArgs) {
		if (hasDateTime(addCmdArgs)) {
			if (addCmdArgs.contains(KW_ON) || addCmdArgs.contains(KW_BY)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
