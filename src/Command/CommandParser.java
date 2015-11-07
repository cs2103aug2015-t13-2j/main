package Command;

//@@author A0145695R

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import Command.*;
import NexTask.EditSpecification;
import NexTask.Task;

public class CommandParser implements java.io.Serializable {
	// Date time format
	private static final String DATE_TIME_FMT_1 = "dd/MM/yy";
	private static final String DATE_TIME_FMT_2 = "dd/MM/yy hh:mma";
	private static final String DATE_TIME_FMT_3 = "dd/MM/yy hh:mm a";

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
	private static final String USER_COMMAND_HELP = "help";
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

	private static String userCommand;

	public Command parse(String userInput) {
		// check if user input empty
		String commandArgs;
		
		if (userInput.equals(USER_COMMAND_VIEW_COMPLETED)){
			userCommand = USER_COMMAND_VIEW_COMPLETED;
			commandArgs = EMPTY_STRING;
		} else if (userInput.equals(USER_COMMAND_VIEW_INCOMPLETE)){
			userCommand = USER_COMMAND_VIEW_INCOMPLETE;
			commandArgs = EMPTY_STRING;
		} else{
			String[] input = userInput.split(" ", 2);
			if (input.length > 1) {
				userCommand = getCommand(input);
				commandArgs = getCommandArgs(input);
			} else {
				userCommand = userInput.trim();
				commandArgs = EMPTY_STRING;
			}
		}
		
		switch (userCommand.toLowerCase()) {
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
			return initInvalidCommand("Please enter a command");
		}
	}

	public String getUserCommand() {
		return userCommand;
	}

	public String getCommand(String[] input) {
		return input[POSITION_OF_CMD];
	}

	public String getCommandArgs(String[] input) {
		return input[POSITION_OF_CMD_ARGS];
	}

	private Command initAddCommand(String commandArgs) {
		Add cmd = new Add();
		if (isEvent(commandArgs.toLowerCase())) {
			Task newEvent = parseEvent(commandArgs);
			if (newEvent.getName().equals(INVALID)) {
				return initInvalidCommand("Error parsing date.");
			} else if (newEvent.getName().equals(EMPTY_STRING)) {
				return initInvalidCommand("Pleae provide a name for your task.");
			} else {
				cmd.setCommandName(USER_COMMAND_ADD);
				cmd.setTask(newEvent);
			}
		} else if (isDeadline(commandArgs.toLowerCase())) {
			Task newDeadline = parseDeadline(commandArgs);
			if (newDeadline.getName().equals(INVALID)) {
				return initInvalidCommand("Error parsing date");
			} else {
				cmd.setCommandName(USER_COMMAND_ADD);
				cmd.setTask(newDeadline);
			}
		} else {
			Task newTodo = parseTodo(commandArgs);
			if (newTodo.getName().equals(INVALID)) {
				return initInvalidCommand("Error parsing date");
			} else {
				cmd.setCommandName(USER_COMMAND_ADD);
				cmd.setTask(newTodo);
			}
		}
		return cmd;
	}

	private Command initEditCommand(String commandArgs) {
		Edit cmd = new Edit();
		EditSpecification edit = new EditSpecification();
		String[] editArgs = commandArgs.split(" ", 3);
		try {
			edit.setTaskNumber(Integer.parseInt(editArgs[0]));
		} catch (NumberFormatException e) {
			return initInvalidCommand("Please specify an integer for task number.");
		}

		String fieldOrClear;
		String argumentsForEdit;
		try {
			fieldOrClear = editArgs[1];
			argumentsForEdit = editArgs[2];
		} catch (IndexOutOfBoundsException e) {
			return initInvalidCommand("Invalid number of arguments");
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
		if (commandArgs.equals(EMPTY_STRING)) {
			return initInvalidCommand("Please provide a task number.");
		} else {
			try {
				cmd.setTaskNumber(Integer.parseInt(commandArgs));
			} catch (NumberFormatException e) {
				return initInvalidCommand("Please specify an integer for task number.");
			}
		}
		return cmd;
	}

	private Command initCompleteCommand(String commandArgs) {
		Completed cmd = new Completed();
		cmd.setCommandName(USER_COMMAND_COMPLETE);
		if (commandArgs.equals(EMPTY_STRING)) {
			return initInvalidCommand("Please provide a task number.");
		} else {
			try {
				cmd.setTaskNumber(Integer.parseInt(commandArgs));
			} catch (NumberFormatException e) {
				return initInvalidCommand("Please specify an integer for task number.");
			}
		}
		return cmd;
	}

	private Command initSearchCommand(String commandArgs) {
		Search cmd = new Search();
		if (commandArgs.equals(EMPTY_STRING)) {
			return initInvalidCommand("Please provide a search keyword.");
		} else {
			cmd.setCommandName(USER_COMMAND_SEARCH);
			cmd.setSearchSpecification(commandArgs.trim());
		}
		return cmd;
	}

	private Command initSortCommand(String commandArgs) {
		Sort cmd = new Sort();
		if (commandArgs.equals(EMPTY_STRING)) {
			return initInvalidCommand("Please specify field you wish to sort by.");
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
		cmd.setCommandName(commandName);
		return cmd;
	}

	private Command initCommand(String commandName) {
		InitCommand cmd = new InitCommand();
		cmd.setCommandName(commandName);
		return cmd;
	}

	private Command initViewIncompleteCommand(String commandName) {
		ViewIncomplete cmd = new ViewIncomplete();
		cmd.setCommandName(commandName);
		return cmd;
	}

	private Command initUndoCommand(String commandName) {
		Undo cmd = new Undo();
		cmd.setCommandName(commandName);
		return cmd;
	}

	private Command initViewCompletedCommand(String commandName) {
		ViewCompleted cmd = new ViewCompleted();
		cmd.setCommandName(commandName);
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
			try {
				newEvent.setStart(parseDateTime(getDateTime(args, KW_START)));
				newEvent.setName(getTaskName(args, KW_START));
				hasStart = true;
			} catch (IllegalArgumentException e) {
				return new Task(INVALID);
			}
		}
		if (args.contains(KW_END)) {
			try {
				if (!hasStart) {
					newEvent.setName(getTaskName(args, KW_END));
				}
				newEvent.setEnd(parseDateTime(getDateTime(args, KW_END)));
				hasEnd = true;
			} catch (IllegalArgumentException e) {
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
			try {
				newDeadline.setCompleteBy(parseDateTime(getDateTime(args, getKeyword(args))));
				newDeadline.setName(getTaskName(args, getKeyword(args)));
			} catch (IllegalArgumentException e) {
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
		Matcher m = taskNamePattern.matcher(args);
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

	public DateTime parseDateTime(String dateTimeString) {
		DateTimeFormatter formatter;
		int numParams = getNumDateTimeParam(dateTimeString);
		switch (numParams) {
		case 1:
			formatter = DateTimeFormat.forPattern(DATE_TIME_FMT_1);
			return formatter.parseDateTime(dateTimeString);
		case 2:
			formatter = DateTimeFormat.forPattern(DATE_TIME_FMT_2);
			return formatter.parseDateTime(dateTimeString);
		default:
			formatter = DateTimeFormat.forPattern(DATE_TIME_FMT_3);
			return formatter.parseDateTime(dateTimeString);
		}
	}

	/**
	 * Helper method to determine how many parts did the user input date in.
	 */
	private int getNumDateTimeParam(String dateTimeString) {
		return dateTimeString.split(" ").length;
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
