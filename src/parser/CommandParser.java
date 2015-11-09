package parser;

import java.util.logging.Logger;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;

import command.*;
import parser.DateTimeParser;
import NexTask.EditSpecification;
import NexTask.Task;

//@@author A0145695R

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
	private static final String LOG_PROCESS = "going to start processing";
	private static final String LOG_END = "end of processing";
	private static final String LOG_END_1 = "end of processong parser add";
	private static final String LOG_END_2 = "end of processong parser edit";
	private static final String LOG_END_3 = "end of processong parser delete";
	private static final String LOG_END_4 = "end of processong parser complete";
	private static final String LOG_END_5 = "end of processong parser search";
	private static final String LOG_END_6 = "end of processong parser sort";
	private static final String LOG_END_7 = "end of processong parser store";
	private static final String LOG_END_8 = "end of processong parser view completed";
	private static final String LOG_END_9 = "end of processong parser view incomplete";
	private static final String LOG_END_10 = "end of processong parser undo";
	private static final String LOG_END_11 = "end of processong parser exit";
	private static final String LOG_END_12 = "end of processong parser retrieve";
	private static final String LOG_END_13 = "end of processong parser invalid";
	private static final String LOG_ERROR = "processing error";
	private static final String LOG_FILE_NAME = "CommandParserLogFile.log";
	private static final String LOG_ERROR_INITIALIZE = "Cannot intialize log file!";
	private static Logger logger = Logger.getLogger("CommandParser");
	private static FileHandler fh;
	private static SimpleFormatter formatter;

	public void initializeLogger() {
		try {
			this.fh = new FileHandler(LOG_FILE_NAME);
		} catch (SecurityException | IOException e) {
			System.out.println(LOG_ERROR_INITIALIZE);
			System.exit(1);
		}
		this.logger.addHandler(fh);
		formatter = new SimpleFormatter();
		this.fh.setFormatter(formatter);
	}

	public Command parse(String userInput) {
		logger.log(Level.INFO, LOG_PROCESS);
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
			logger.log(Level.INFO, LOG_END_1);
			return initAddCommand(commandArgs);
		case USER_COMMAND_EDIT:
			logger.log(Level.INFO, LOG_END_2);
			return initEditCommand(commandArgs);
		case USER_COMMAND_DELETE:
			logger.log(Level.INFO, LOG_END_3);
			return initDeleteCommand(commandArgs);
		case USER_COMMAND_COMPLETE:
			logger.log(Level.INFO, LOG_END_4);
			return initCompleteCommand(commandArgs);
		case USER_COMMAND_SEARCH:
			logger.log(Level.INFO, LOG_END_5);
			return initSearchCommand(commandArgs);
		case USER_COMMAND_SORT:
			logger.log(Level.INFO, LOG_END_6);
			return initSortCommand(commandArgs);
		case USER_COMMAND_STORE:
			logger.log(Level.INFO, LOG_END_7);
			return initStoreCommand(commandArgs);
		case USER_COMMAND_VIEW_COMPLETED:
			logger.log(Level.INFO, LOG_END_8);
			return initViewCompletedCommand(USER_COMMAND_VIEW_COMPLETED);
		case USER_COMMAND_VIEW_INCOMPLETE:
			logger.log(Level.INFO, LOG_END_9);
			return initViewIncompleteCommand(USER_COMMAND_VIEW_INCOMPLETE);
		/*
		 * case USER_COMMAND_HELP: return initHelpCommand(USER_COMMAND_HELP);
		 */
		case USER_COMMAND_UNDO:
			logger.log(Level.INFO, LOG_END_10);
			return initUndoCommand(USER_COMMAND_UNDO);
		case USER_COMMAND_EXIT:
			logger.log(Level.INFO, LOG_END_11);
			return initCommand(USER_COMMAND_EXIT);
		case USER_COMMAND_RETRIEVE:
			logger.log(Level.INFO, LOG_END_12);
			return initRetrieveCommand(USER_COMMAND_RETRIEVE);
		default:
			logger.log(Level.INFO, LOG_END_13);
			return initInvalidCommand(ERROR_INVALID_CMD);
		}
	}

	public String getCommand(String[] input) {
		return input[POSITION_OF_CMD];
	}

	public String getCommandArgs(String[] input) {
		return input[POSITION_OF_CMD_ARGS];
	}

	/**
	 * @param commandArgs
	 *            details regarding the task the user wants to add
	 * @return This method will return an add command if the user input is in
	 *         correct format, otherwise it will return an invalid command with
	 *         error message initialized accordingly.
	 */
	private Command initAddCommand(String commandArgs) {
		logger.log(Level.INFO, LOG_PROCESS);
		Add cmd = new Add();
		if (isEvent(commandArgs.toLowerCase().trim())) {
			Task newEvent = parseEvent(commandArgs);
			if (newEvent.getName().equals(INVALID)) {
				logger.log(Level.INFO, LOG_END);
				return initInvalidCommand(ERROR_INVALID_DATE_FORMAT);
			} else if (newEvent.getName().equals(EMPTY_STRING)) {
				logger.log(Level.INFO, LOG_END);
				return initInvalidCommand(ERROR_NO_NAME_FOUND);
			} else {
				cmd.setCommandName(USER_COMMAND_ADD);
				cmd.setTask(newEvent);
			}
		} else if (isDeadline(commandArgs.toLowerCase().trim())) {
			Task newDeadline = parseDeadline(commandArgs);
			if (newDeadline.getName().equals(INVALID)) {
				logger.log(Level.INFO, LOG_END);
				return initInvalidCommand(ERROR_INVALID_DATE_FORMAT);
			} else if (newDeadline.getName().equals(EMPTY_STRING)) {
				logger.log(Level.INFO, LOG_END);
				return initInvalidCommand(ERROR_NO_NAME_FOUND);
			} else {
				cmd.setCommandName(USER_COMMAND_ADD);
				cmd.setTask(newDeadline);
			}
		} else {
			Task newTodo = parseTodo(commandArgs);
			if (newTodo.getName().equals(INVALID)) {
				logger.log(Level.INFO, LOG_END);
				return initInvalidCommand(ERROR_NO_NAME_FOUND);
			} else {
				cmd.setCommandName(USER_COMMAND_ADD);
				cmd.setTask(newTodo);
			}
		}
		logger.log(Level.INFO, LOG_END);
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
		logger.log(Level.INFO, LOG_PROCESS);
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
			logger.log(Level.WARNING, LOG_ERROR, e);
			return initInvalidCommand(ERROR_INVALID_NUM_ARGS);
		}

		try {
			edit.setTaskNumber(Integer.parseInt(taskNumber));
		} catch (NumberFormatException e) {
			logger.log(Level.WARNING, LOG_ERROR, e);
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
		logger.log(Level.INFO, LOG_END);
		return cmd;
	}

	/**
	 * @param commandArgs
	 *            details regarding the task the user wants to delete.
	 * @return a Delete command if the user provides an integer as a task
	 *         number, an invalid command otherwise.
	 */
	private Command initDeleteCommand(String commandArgs) {
		logger.log(Level.INFO, LOG_PROCESS);
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
		logger.log(Level.INFO, LOG_END);
		return cmd;
	}

	/**
	 * @param commandArgs
	 *            details regarding the task the user wants to mark as complete.
	 * @return a Complete command if the user provides an integer as a task
	 *         number, an invalid command otherwise.
	 */
	private Command initCompleteCommand(String commandArgs) {
		logger.log(Level.INFO, LOG_PROCESS);
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
		logger.log(Level.INFO, LOG_END);
		return cmd;
	}

	/**
	 * @param commandArgs
	 *            specification of what the user would like to search for.
	 * @return a Search command if the user provides keywords.
	 */
	private Command initSearchCommand(String commandArgs) {
		logger.log(Level.INFO, LOG_PROCESS);
		Search cmd = new Search();
		if (commandArgs.trim().equals(EMPTY_STRING)) {
			return initInvalidCommand(ERROR_NO_SEARCH_FIELD);
		} else {
			cmd.setCommandName(USER_COMMAND_SEARCH);
			cmd.setSearchSpecification(commandArgs.trim());
		}
		logger.log(Level.INFO, LOG_END);
		return cmd;
	}

	/**
	 * @param commandArgs
	 *            specification of how the user want to sort tasks.
	 * @return a sort command if the user specifies how he or she want tasks
	 *         sorted , an invalid command otherwise.
	 */
	private Command initSortCommand(String commandArgs) {
		logger.log(Level.INFO, LOG_PROCESS);
		Sort cmd = new Sort();
		if (commandArgs.trim().equals(EMPTY_STRING)) {
			logger.log(Level.INFO, LOG_END);
			return initInvalidCommand(ERROR_NO_SORT_FIELD);
		} else {
			cmd.setCommandName(USER_COMMAND_SORT);
			cmd.setSortField(commandArgs.trim());

		}
		logger.log(Level.INFO, LOG_END);
		return cmd;
	}

	/**
	 * @param commandArgs
	 *            details regarding where the user wants to save the tasks.
	 * @return a store command initialized accordingly.
	 */
	private Command initStoreCommand(String commandArgs) {
		logger.log(Level.INFO, LOG_PROCESS);
		Store cmd = new Store();
		cmd.setCommandName(USER_COMMAND_STORE);
		cmd.setDirectory(commandArgs.trim());
		logger.log(Level.INFO, LOG_END);
		return cmd;
	}

	// The folllowing init commands initializes the commandNamea of the commands
	// accordingly
	private Command initRetrieveCommand(String commandName) {
		logger.log(Level.INFO, LOG_PROCESS);
		Retrieve cmd = new Retrieve();
		cmd.setCommandName(commandName.trim());
		logger.log(Level.INFO, LOG_END);
		return cmd;
	}

	private Command initCommand(String commandName) {
		logger.log(Level.INFO, LOG_PROCESS);
		InitCommand cmd = new InitCommand();
		cmd.setCommandName(commandName.trim());
		logger.log(Level.INFO, LOG_END);
		return cmd;
	}

	private Command initViewIncompleteCommand(String commandName) {
		logger.log(Level.INFO, LOG_PROCESS);
		ViewIncomplete cmd = new ViewIncomplete();
		cmd.setCommandName(commandName.trim());
		logger.log(Level.INFO, LOG_END);
		return cmd;
	}

	private Command initUndoCommand(String commandName) {
		logger.log(Level.INFO, LOG_PROCESS);
		Undo cmd = new Undo();
		cmd.setCommandName(commandName.trim());
		logger.log(Level.INFO, LOG_END);
		return cmd;
	}

	private Command initViewCompletedCommand(String commandName) {
		logger.log(Level.INFO, LOG_PROCESS);
		ViewCompleted cmd = new ViewCompleted();
		cmd.setCommandName(commandName.trim());
		logger.log(Level.INFO, LOG_END);
		return cmd;
	}

	private Command initInvalidCommand(String errorMessage) {
		logger.log(Level.INFO, LOG_PROCESS);
		assert (!errorMessage.equals(EMPTY_STRING));
		InitCommand c = new InitCommand();
		c.setCommandName(INVALID);
		c.setErrorMessage(errorMessage);
		logger.log(Level.INFO, LOG_END);
		return c;
	}

	// Helper methods
	/**
	 * @param args
	 *            details regarding the event
	 * @return task object initialized as an event if the event was able to me
	 *         parsed. otherwise, it will return a task object with name
	 *         initialized to invalid.
	 */
	public Task parseEvent(String args) {
		logger.log(Level.INFO, LOG_PROCESS);
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
				logger.log(Level.INFO, LOG_END);
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
				logger.log(Level.INFO, LOG_END);
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
		logger.log(Level.INFO, LOG_END);
		return newEvent;
	}

	/**
	 * @param args
	 *            details regarding the deadline
	 * @return task object initialized as an deadline if the deadline was able
	 *         to me parsed. otherwise, it will return a task object with name
	 *         initialized to invalid.
	 */
	public Task parseDeadline(String args) {
		logger.log(Level.INFO, LOG_PROCESS);
		Task newDeadline = new Task();
		newDeadline.setTaskType(TASK_TYPE_DEADLINE);
		if (hasDateTime(args)) {
			DateTime completeBy = DateTimeParser.parse(getDateTime(args, getKeyword(args)));
			if (completeBy != null) {
				newDeadline.setCompleteBy(completeBy);
				newDeadline.setName(getTaskName(args, getKeyword(args)));
			} else {
				logger.log(Level.INFO, LOG_END);
				return new Task(INVALID);
			}
		} else {
			logger.log(Level.INFO, LOG_END);
			return parseTodo(args);
		}
		logger.log(Level.INFO, LOG_END);
		return newDeadline;
	}

	/**
	 * @param args
	 *            details regarding the todo task
	 * @return task object initialized as a todo a name or description is
	 *         provided. otherwise, it will return a task object with name
	 *         initialized to invalid.
	 */
	public Task parseTodo(String args) {
		logger.log(Level.INFO, LOG_PROCESS);
		Task newTodo = new Task();
		newTodo.setTaskType(TASK_TYPE_TODO);
		if ((args.trim()).equals(EMPTY_STRING)) {
			logger.log(Level.INFO, LOG_END);
			return new Task(INVALID);
		} else {
			newTodo.setName(args);
		}
		logger.log(Level.INFO, LOG_END);
		return newTodo;
	}

	/**
	 * Helper method for parsing deadline. Determine which keyword is in the
	 * deadline specification.
	 * 
	 * @param args string that entails the deadline details
	 * @return the appropriate keyword -- "on" or "by"
	 */
	public String getKeyword(String args) {
		logger.log(Level.INFO, LOG_PROCESS);
		if (args.contains(KW_ON)) {
			logger.log(Level.INFO, LOG_END);
			return KW_ON;
		} else {
			logger.log(Level.INFO, LOG_END);
			return KW_BY;
		}
	}

	/**
	 * Helper method to get task name. Task name appears before first keyword.
	 */
	private String getTaskName(String args, String keyword) {
		logger.log(Level.INFO, LOG_PROCESS);
		Pattern taskNamePattern = Pattern.compile(PATTERN_TASK_NAME + keyword);
		Matcher m = taskNamePattern.matcher(args.trim());
		if (m.find()) {
			logger.log(Level.INFO, LOG_END);
			return m.group(1).trim();
		} else {
			logger.log(Level.INFO, LOG_END);
			return "";
		}
	}

	/**
	 * Given a string, determine if it has a datetime.
	 */
	public boolean hasDateTime(String args) {
		logger.log(Level.INFO, LOG_PROCESS);
		Pattern dateTimePattern = Pattern.compile(PATTERN_DATE);
		Matcher m = dateTimePattern.matcher(args);
		if (m.find()) {
			logger.log(Level.INFO, LOG_END);
			return true;
		} else {
			logger.log(Level.INFO, LOG_END);
			return false;
		}
	}

	/**
	 * Helper method to get the DateTime from user input. Date time are wrapped
	 * in quotes.
	 */
	public String getDateTime(String args) {
		logger.log(Level.INFO, LOG_PROCESS);
		Pattern dateTimePattern = Pattern.compile(PATTERN_DATE);
		Matcher m = dateTimePattern.matcher(args);
		if (m.find()) {
			logger.log(Level.INFO, LOG_END);
			return m.group(1);
		} else {
			logger.log(Level.INFO, LOG_END);
			return "";
		}
	}

	/**
	 * Helper method to get the DateTime from user input. Date time are wrapped
	 * in quotes.
	 */
	public String getDateTime(String args, String keyword) {
		logger.log(Level.INFO, LOG_PROCESS);
		Pattern dateTimePattern = Pattern.compile(keyword + " " + PATTERN_DATE);
		Matcher m = dateTimePattern.matcher(args);
		if (m.find()) {
			logger.log(Level.INFO, LOG_END);
			return m.group(1);
		} else {
			logger.log(Level.INFO, LOG_END);
			return "";
		}
	}

	private boolean isEvent(String args) {
		logger.log(Level.INFO, LOG_PROCESS);
		String start = " " + KW_START + " ";
		String end = " " + KW_END + " ";
		if (args.contains(start) || args.contains(end)) {
			logger.log(Level.INFO, LOG_END);
			return true;
		} else {
			logger.log(Level.INFO, LOG_END);
			return false;
		}
	}

	private boolean isDeadline(String addCmdArgs) {
		logger.log(Level.INFO, LOG_PROCESS);
		if (hasDateTime(addCmdArgs)) {
			if (addCmdArgs.contains(KW_ON) || addCmdArgs.contains(KW_BY)) {
				logger.log(Level.INFO, LOG_END);
				return true;
			} else {
				logger.log(Level.INFO, LOG_END);
				return false;
			}
		} else {
			logger.log(Level.INFO, LOG_END);
			return false;
		}
	}
}
