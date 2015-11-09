package test;

import NexTask.*;
import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import command.Command;
import parser.CommandParser;

public class CommandParserTest {
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

	// Error Messages
	private static final String ERROR_NO_NAME_FOUND = "Pleae provide a name for your task.";
	private static final String ERROR_INVALID_DATE_FORMAT = "Invalid date. Make sure your date is valid and enter \"help\" if you need to see accepted date formats.";
	private static final String ERROR_INTEGER_NOT_FOUND = "Please specify task number as an integer.";
	private static final String ERROR_INVALID_NUM_ARGS = "Invalid number of arguments. Enter \"help\" to view command format.";
	private static final String ERROR_NO_TASK_NUM = "Please provide a task number.";
	private static final String ERROR_NO_SORT_FIELD = "Please specify field you wish to sort by.";
	private static final String ERROR_NO_SEARCH_FIELD = "Please specify what you would like to search for.";

	@Test
	/**
	 * Equivalence partitions: Inputs for which parse will return edit command
	 * in this format "edit [integer] [String] [String]" Must have at least 3
	 * parts or more. 
	 *
	 */
	public void parseEditGood() {
		CommandParser cp = new CommandParser();
		String str1 = "edit 1 clear start"; // boundary case
		String str2 = "edit 2 name sad sad"; // boundary case theEdit > 1 word		
		
		Command res1 = cp.parse(str1);
		Command res2 = cp.parse(str2);
		
		assertEquals(USER_COMMAND_EDIT, res1.getCommandName());
		assertEquals(KW_START, res1.getEditSpecification().getFieldToClear());
		assertEquals(1, res1.getEditSpecification().getTaskNumber());
		
		assertEquals(USER_COMMAND_EDIT, res2.getCommandName());
		assertEquals("name", res2.getEditSpecification().getFieldToEdit());
		assertEquals("sad sad", res2.getEditSpecification().getTheEdit());
	}

	@Test
	/**
	 * Equivalence partition: Inputs for which parse will return invalid command
	 * is in this format: 
	 * 1. "edit [not an integer] [String] [String]" 
	 * 2. "edit [< 3 parts], i.e. 
	 * 		"edit [integer] [String]" // Boundary case
	 * 		"edit [integer]" 
	 * 		"edit" // Boundary case 
	 * String != whitespace
	 */
	public void parseEditBad() {
		CommandParser cp = new CommandParser();
		String str1 = "edit one start \"11/11/11\"";
		String str2 = "edit 1 start   ";
		String str3 = "edit";

		Command res1 = cp.parse(str1);
		Command res2 = cp.parse(str2);
		Command res3 = cp.parse(str3);

		assertEquals(INVALID, res1.getCommandName());
		assertEquals(ERROR_INTEGER_NOT_FOUND, res1.getErrorMessage());

		assertEquals(INVALID, res2.getCommandName());
		assertEquals(ERROR_INVALID_NUM_ARGS, res2.getErrorMessage());

		assertEquals(INVALID, res3.getCommandName());
		assertEquals(ERROR_INVALID_NUM_ARGS, res3.getErrorMessage());
	}

	@Test
	public void parseAddGood() {
		CommandParser cp = new CommandParser();
		// Three ways to create an event
		String str1 = "add       meeting start \"10/10/10 1:00 pm \" end \"10/10/10 2:00 pm\""; //boundary name 1 word
		String str2 = "add start laughing start     start \"10/10/10\""; // name more than 1 word 
		String str3 = "add meeting by the bay end \"10/10/10\"";

		// 2 ways to create deadline tasks
		String str4 = "add start assn1 by \"10/10/10 1:00 pm\"";
		String str5 = "add bday on \"10/10/10\"";
		
		// Create todos
		String str6 = "add laundry"; // boundary
		String str7 = "add laundry man"; // boundary
		
		Command res1 = cp.parse(str1);
		Command res2 = cp.parse(str2);
		Command res3 = cp.parse(str3);
		Command res4 = cp.parse(str4);
		Command res5 = cp.parse(str5);
		Command res6 = cp.parse(str6);
		Command res7 = cp.parse(str7);
		
		DateTime expected1 = new DateTime(2010, 10, 10, 00, 00);
		DateTime expected2 = new DateTime(2010, 10, 9, 23, 00);
		DateTime expected3 = new DateTime(2010, 10, 10, 1, 00);
		DateTime expected4 = new DateTime(2010, 10, 10, 13, 00);
		DateTime expected5 = new DateTime(2010, 10, 10, 14, 00);
		
		// Event assertions
		assertEquals(true, res1.getTask().getTaskType().equals(TASK_TYPE_EVENT));
		assertEquals(true, res2.getTask().getTaskType().equals(TASK_TYPE_EVENT));
		assertEquals(true, res3.getTask().getTaskType().equals(TASK_TYPE_EVENT));
		
		assertEquals(true, res1.getTask().getName().equals("meeting"));
		assertEquals(true, res2.getTask().getName().equals("start laughing start"));
		assertEquals(true, res3.getTask().getName().equals("meeting by the bay"));
		
		assertEquals(true, res1.getTask().getStart().equals(expected4));
		assertEquals(true, res2.getTask().getStart().equals(expected1));
		assertEquals(true, res3.getTask().getStart().equals(expected2));
		
		assertEquals(true, res1.getTask().getEnd().equals(expected5));
		assertEquals(true, res2.getTask().getEnd().equals(expected3));
		assertEquals(true, res3.getTask().getEnd().equals(expected1));
		
		// Deadline assertions
		assertEquals(true, res4.getTask().getTaskType().equals(TASK_TYPE_DEADLINE));
		assertEquals(true, res5.getTask().getTaskType().equals(TASK_TYPE_DEADLINE));

		assertEquals(true, res4.getTask().getName().equals("start assn1"));
		assertEquals(true, res5.getTask().getName().equals("bday"));
		
		assertEquals(true, res4.getTask().getCompleteBy().equals(expected4));
		assertEquals(true, res5.getTask().getCompleteBy().equals(expected1));
		
		// Todo assertions
		assertEquals(true, res6.getTask().getTaskType().equals(TASK_TYPE_TODO));
		assertEquals(true, res7.getTask().getTaskType().equals(TASK_TYPE_TODO));

		assertEquals(true, res6.getTask().getName().equals("laundry"));
		assertEquals(true, res7.getTask().getName().equals("laundry man"));
	}
	
	@Test
	public void parseAddBad() {
		CommandParser cp = new CommandParser();

		// No task name
		String str1 = "add  start \"10/10/10 1:00 pm \" end \"10/10/10 2:00 pm\""; 
		String str2 = "add      by \"10/10/10 1:00 pm\"";
		String str3 = "add          ";
	
		// Invalid Dates
		String str4 = "add meeting by the bay end \"35/10/11\"";
		String str5 = "add meeting by the bay end \"\"";
		
		Command res1 = cp.parse(str1);
		Command res2 = cp.parse(str2);
		Command res3 = cp.parse(str3);
		Command res4 = cp.parse(str4);
		Command res5 = cp.parse(str5);
		
		assertEquals(INVALID, res1.getCommandName());
		assertEquals(INVALID, res2.getCommandName());
		assertEquals(INVALID, res3.getCommandName());
		assertEquals(INVALID, res4.getCommandName());
		assertEquals(INVALID, res5.getCommandName());
		
		assertEquals(ERROR_NO_NAME_FOUND, res1.getErrorMessage());
		assertEquals(ERROR_NO_NAME_FOUND, res2.getErrorMessage());
		assertEquals(ERROR_NO_NAME_FOUND, res3.getErrorMessage());
		assertEquals(ERROR_INVALID_DATE_FORMAT, res4.getErrorMessage());
		assertEquals(ERROR_INVALID_DATE_FORMAT, res5.getErrorMessage());
	
	}

	@Test
	public void parseDeleteTest() {
		CommandParser cp = new CommandParser();
		String str1 = "delete 1";
		String str2 = "delete ";
		String str3 = "delete one";

		Command res1 = cp.parse(str1);
		Command res2 = cp.parse(str2);
		Command res3 = cp.parse(str3);

		assertEquals(USER_COMMAND_DELETE, res1.getCommandName());
		assertEquals(1, res1.getTaskNumber());

		assertEquals(INVALID, res2.getCommandName());
		assertEquals(ERROR_NO_TASK_NUM, res2.getErrorMessage());

		assertEquals(INVALID, res3.getCommandName());
		assertEquals(ERROR_INTEGER_NOT_FOUND, res3.getErrorMessage());
	}

	@Test
	public void parseCompleteTest() {
		CommandParser cp = new CommandParser();
		String str1 = "complete 1";
		String str2 = "complete ";
		String str3 = "complete one";

		Command res1 = cp.parse(str1);
		Command res2 = cp.parse(str2);
		Command res3 = cp.parse(str3);

		assertEquals(USER_COMMAND_COMPLETE, res1.getCommandName());
		assertEquals(1, res1.getTaskNumber());

		assertEquals(INVALID, res2.getCommandName());
		assertEquals(ERROR_NO_TASK_NUM, res2.getErrorMessage());

		assertEquals(INVALID, res3.getCommandName());
		assertEquals(ERROR_INTEGER_NOT_FOUND, res3.getErrorMessage());
	}

	
	@Test
	public void parseSort() {
		CommandParser cp = new CommandParser();
		String str1 = "sort name";
		String str2 = "sort ";

		Command res1 = cp.parse(str1);
		Command res2 = cp.parse(str2);

		assertEquals("sort", res1.getCommandName());
		assertEquals("name", res1.getSortField());

		assertEquals(INVALID, res2.getCommandName());
		assertEquals(ERROR_NO_SORT_FIELD, res2.getErrorMessage());
	}
	
	@Test
	public void parseSearch() {
		CommandParser cp = new CommandParser();
		String str1 = "search name";
		String str2 = "search ";

		Command res1 = cp.parse(str1);
		Command res2 = cp.parse(str2);

		assertEquals(USER_COMMAND_SEARCH, res1.getCommandName());
		assertEquals("name", res1.getSearchSpecification());

		assertEquals(INVALID, res2.getCommandName());
		assertEquals(ERROR_NO_SEARCH_FIELD, res2.getErrorMessage());
	}
}
