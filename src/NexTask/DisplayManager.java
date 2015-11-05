package NexTask;

import java.lang.String;
import java.util.ArrayList;

/**
 * GUI class will display the appropriate print messages required when user
 * input is processed and appropriate actions are executed. GUI is the main
 * interface for user interaction.
 * 
 * @@author Javan Huang
 *
 */

public class DisplayManager {
	/*
	 * Print Messages
	 */
	private static final String COMMAND_PROMPT = "Command: ";
	private static final String WELCOME_MSG = "Greetings! NexTask is ready for use.";
	private static final String WELCOME_HELP_MSG = "If you need any help, please type \"help\" to retrieve the help guide!";
	private static final String COMMAND_ADDED = "Task has been added!";
	private static final String COMMAND_DONE = "Task has been marked as completed!";
	private static final String COMMAND_DELETED = "Task has been deleted!";
	private static final String COMMAND_EDIT = "Task has been edited!";
	private static final String COMMAND_UNDO = "Previous task has been undone!";
	private static final String COMMAND_SEARCH = "Search results are as follows: \n";
	private static final String COMMAND_ARCHIVE = "Archive has been retrieved. Here is the list of completed tasks!";
	private static final String COMMAND_SORT = "Tasks has been sorted";
	private static final String COMMAND_STORED = "File has been saved!";
	private static final String COMMAND_HELP = "The following commands are as shown:\n"
			+ "To add an event: add (description of task) start \"date & time\" end \"date & time\".\n"
			+ "To add a task with deadline: add (description of task) on/by \"date & time\".\n"
			+ "To add a task with no deadline: add (description of task).\n"
			+ "To mark a task as completed: complete (task number).\n"
			+ "To delete a task: delete (task number).\n"
			+ "To edit a specific task: please refer to \"EDIT GUIDE\" for more information. \n"
			+ "To undo a certain task: undo"
			+ "To search: search (description of search term).\n"
			+ "To retrieve an archive of completed tasks: archive.\n"
			+ "To display the current list of tasks: display.\n" 
			+ "To save to: store (filename).";
	private static final String EDIT_HELP = "1. edit (task number) clear start [To set only a deadline timing for specified task]\n" 
			+ "2. edit (task number) clear end [To set only a deadline timing for specified task]\n"
			+ "3. edit (task number) clear times [To get rid of all times for specified task]\n"
			+ "4. edit (task number) end (date & time) [To add an end time for specified task]\n"
			+ "5. edit (task number) start (date & time) [To add a start time for specified task]\n"
			+ "8. edit (task number) by (date & time)[To add a deadline timing for specified task]\n";
	private static final String NO_CONTENT_TO_DELETE = "%1$s is empty. There is no content to delete from!";
	private static final String NO_CONTENT_TO_DISPLAY = "Task list is empty. There is no content to display!";
	private static final String NO_ARCHIVE = "There are no completed tasks in archive!";
	private static final String NO_CONTENT = "There is no task available to %1$s.";
	private static final String COMMAND_ERROR = "Unrecognized command type entered! Please input a correct command type!";
	private static final String UNABLE_TO_DELETE = "Sorry, unable to delete from task list!";
	private static final String UNABLE_TO_SEARCH = "Sorry, unable to find any results for the search term!";
	private static final String EXCEED_MAXSIZE_OF_TASK = "Sorry, the number you have entered exceeds the maximum number of tasks!";
	//private static final String WRONG_NUM_FORMAT = "Sorry, the number you put is not in correct format";
	private static final String UNABLE_TO_STORE = "Unable to store in the specified directory, please specify another valid directory!";
	//private static final int INDEX_OF_CMD_NAME = 0;
	//private static final String INVALID_CMD = "invalid";
	private static final String UNABLE_TO_ADD = "Task is unable to add, please follow the format below:"
			+ "To add an event: add event start (date & time) end (date & time) (description of task).\n"
			+ "To add a task with deadline: add deadline due by (date & time) (description of task).\n"
			+ "To add a task with no deadline: add to-do (description of task).\n"
			+ "To add a recurring task: add repeat (description of task).\n";
	private static final String UNABLE_TO_SORT = "Unable to sort.";
	private static final String ERROR_INVALID_FIELD_FOR_EDIT = "Invalid field to edit.";
	private static final String ERROR_INVALID_NUM_ARGS_FOR_EDIT = "Invalid number of arguments for edit.";
	private static final String ERROR_INVALID_TASK_NUMBER = "Please enter an integer as the task number.";
	private static final String ERROR_INVALID_DATE_FORMAT = "Invalid date format.";
	private static final String INVALID_COMMAND = "There is no such command available for usage.";

	// commandAssigned = specified numbering of commands
	// 1. welcome message, 2. help, 3. add, 4. delete, 5. edit, 6. completed, 7.
	// undo. 8. search, 9. error, 10. display, 11. store, 12. archive, 13. sort, 14. edit help

	public String messageSelector(int commandAssigned, int conditionOfExecution) {
		String returnMessage = "";
		switch (commandAssigned) {
		case 1: // welcome message
			if (conditionOfExecution == 1) {
				returnMessage = WELCOME_MSG + "\n" + WELCOME_HELP_MSG;
				break;
			} else {
				returnMessage = COMMAND_PROMPT;
				break;
			}
		case 2: // help message
			if (conditionOfExecution == 1) {
				returnMessage = COMMAND_HELP;
				break;
			}
		case 3: // add message
			if (conditionOfExecution == 1) {
				// successful add
				returnMessage = COMMAND_ADDED;
				break;
			} else {
				// unsuccessful add
				returnMessage = UNABLE_TO_ADD;
				break;
			}
		case 4: // delete message
			if (conditionOfExecution == 1) {
				returnMessage = COMMAND_DELETED;
				break;
			} else if (conditionOfExecution == 2) {
				returnMessage = UNABLE_TO_DELETE + "\n" + EXCEED_MAXSIZE_OF_TASK;
				break;
			} else {
				returnMessage = UNABLE_TO_DELETE + "\n" + NO_CONTENT_TO_DELETE;
				break;
			}
		case 5: // edit message
			if (conditionOfExecution == 1) {
				returnMessage = COMMAND_EDIT;
				break;
			} else if (conditionOfExecution == 2) {
				returnMessage = ERROR_INVALID_NUM_ARGS_FOR_EDIT;
				break;
			} else if (conditionOfExecution == 3) {
				returnMessage = ERROR_INVALID_TASK_NUMBER;
				break;
			}else if (conditionOfExecution == 4) {
				returnMessage = ERROR_INVALID_DATE_FORMAT;
				break;
			} else {
				returnMessage = ERROR_INVALID_FIELD_FOR_EDIT;
				break;
			}
		case 6: // completed message
			if (conditionOfExecution == 1) {
				returnMessage = COMMAND_DONE;
				break;
			} else {
				returnMessage = (String.format(NO_CONTENT, "mark as done."));
				break;
			}
		case 7: // undo message
			if (conditionOfExecution == 1) {
				returnMessage = COMMAND_UNDO;
				break;
			} else {
				returnMessage = (String.format(NO_CONTENT, "undo"));
				break;
			}
		case 8: // search message
			if (conditionOfExecution == 1) {
				returnMessage = COMMAND_SEARCH;
				break;
			} else {
				returnMessage = UNABLE_TO_SEARCH;
				break;
			}
		case 9: // error message
			if (conditionOfExecution == 1) {
				returnMessage = COMMAND_ERROR;
				break;
			}
		case 10: // display message
			if (conditionOfExecution == 2) {
				returnMessage = NO_CONTENT_TO_DISPLAY;
				break;
			}
		case 11: // store message
			if (conditionOfExecution == 1) {
				returnMessage = COMMAND_STORED;
				break;
			} else {
				returnMessage = UNABLE_TO_STORE;
				break;
			}
		case 12: // archive message
			if (conditionOfExecution == 1) {
				returnMessage = COMMAND_ARCHIVE;
				break;
			} else {
				returnMessage = NO_ARCHIVE;
				break;
			}
		case 13: // sort message
			if (conditionOfExecution == 1) {
				returnMessage = COMMAND_SORT;
				break;
			} else {
				returnMessage = UNABLE_TO_SORT;
				break;
			}	
		case 14: //edit help message
			returnMessage = EDIT_HELP;
			break;
		default: returnMessage = INVALID_COMMAND;
				 break;
		}
		return returnMessage;
	}
	
	/*public ArrayList<String> displayList(ArrayList<String> listOfTasks) {
		return null;
	}
	*/
}
