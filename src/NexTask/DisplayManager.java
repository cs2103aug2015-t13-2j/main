package NexTask;

import java.lang.String;

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
	private static final String COMMAND_EDIT = "Task has been editted!";
	private static final String COMMAND_UNDO = "Previous task has been undone!";
	private static final String COMMAND_SEARCH = "Search results in %1$s: \n";
	private static final String COMMAND_ARCHIVE = "Archive has been retrieved. Here is the list of completed tasks!";
	private static final String COMMAND_STORED = "File has been saved!";
	private static final String COMMAND_HELP = "The following commands are as shown:\n"
			+ "To add an event: add event start (date & time) end (date & time) (description of task).\n"
			+ "To add a task with deadline: add deadline due by (date & time) (description of task).\n"
			+ "To add a task with no deadline: add todo (description of task).\n"
			+ "To add a recurring task: add repeat (description of task).\n"
			+ "To mark a task as completed: finished/completed (task number).\n"
			+ "To delete a task: delete (task number).\n"
			+ "To edit a specific task: edit (task number) (name/date/start/end) (the edit).\n"
			+ "To undo a certain task: undo"
			+ "To search: search (field you want to search) (description of search term).\n"
			+ "To retrieve an archive of completed tasks: archive.\n"
			+ "To display the current list of tasks: display.\n" 
			+ "To save to: save to (filename).";
	private static final String NO_CONTENT_TO_DELETE = "%1$s is empty. There is no content to delete from!";
	private static final String NO_CONTENT_TO_DISPLAY = "Task list is empty. There is no content to display!";
	private static final String NO_ARCHIVE = "There are no completed tasks in archive!";
	private static final String NO_CONTENT = "There is no task available to %1$s.";
	private static final String COMMAND_ERROR = "Unrecognized command type entered! Please input a correct command type!";
	private static final String UNABLE_TO_DELETE = "Sorry, unable to delete from %1$s!";
	private static final String UNABLE_TO_SEARCH = "Sorry, unable to find %1$s in %2$s!";
	private static final String EXCEED_MAXSIZE_OF_TASK = "Sorry, the number you have entered exceeds the maximum number of tasks!";
	private static final String WRONG_NUM_FORMAT = "Sorry, the number you put is not in correct format";
	private static final String UNABLE_TO_STORE = "Unable to store in the specified directory, please specify another valid directory!";
	private static final int INDEX_OF_CMD_NAME = 0;
	private static final String INVALID_CMD = "invalid";
	private static final String UNABLE_TO_ADD = "Task is unable to add, please follow the format below:"
			+ "To add an event: add event start (date & time) end (date & time) (description of task).\n"
			+ "To add a task with deadline: add deadline due by (date & time) (description of task).\n"
			+ "To add a task with no deadline: add to-do (description of task).\n"
			+ "To add a recurring task: add repeat (description of task).\n";
	
	private static final String ERROR_INVALID_FIELD_FOR_EDIT = "Invalid field to edit.";
	private static final String ERROR_INVALID_NUM_ARGS_FOR_EDIT = "Invalid number of arguments for edit.";
	private static final String ERROR_INVALID_TASK_NUMBER = "Please enter an integer as the task number.";

	// commandAssigned = specified numbering of commands
	// 1. welcome message, 2. help, 3. add, 4. delete, 5. edit, 6. completed, 7.
	// undo. 8. search, 9. error, 10. display, 11. store, 12. archive

	public void printer(int commandAssigned, int conditionOfExecution) {
		switch (commandAssigned) {
		case 1: // welcome message
			if (conditionOfExecution == 1) {
				System.out.println(WELCOME_MSG);
				System.out.println(WELCOME_HELP_MSG);
				break;
			} else {
				System.out.println(COMMAND_PROMPT);
				break;
			}
		case 2: // help message
			if (conditionOfExecution == 1) {
				System.out.println(COMMAND_HELP);
				break;
			}
		case 3: // add message
			if (conditionOfExecution == 1) {
				// successful add
				System.out.println(COMMAND_ADDED);
				break;
			} else {
				// unsuccessful add
				System.out.println(UNABLE_TO_ADD);
				break;
			}
		case 4: // delete message
			if (conditionOfExecution == 1) {
				System.out.println(COMMAND_DELETED);
				break;
			} else if (conditionOfExecution == 2) {
				System.out.println(UNABLE_TO_DELETE);
				System.out.println(EXCEED_MAXSIZE_OF_TASK);
				break;
			} else {
				System.out.println(UNABLE_TO_DELETE);
				System.out.println(NO_CONTENT_TO_DELETE);
				break;
			}
		case 5: // edit message
			if (conditionOfExecution == 1) {
				System.out.println(COMMAND_EDIT);
				break;
			} else if (conditionOfExecution == 2) {
				System.out.println(String.format(NO_CONTENT, "edit."));
				System.out.println(ERROR_INVALID_NUM_ARGS_FOR_EDIT);
				break;
			} else {
				System.out.println(String.format(NO_CONTENT, "edit."));
				System.out.println(ERROR_INVALID_TASK_NUMBER);
				break;
			}
		case 6: // completed message
			if (conditionOfExecution == 1) {
				System.out.println(COMMAND_DONE);
				break;
			} else {
				System.out.println(String.format(NO_CONTENT, "mark as done."));
				break;
			}
		case 7: // undo message
			if (conditionOfExecution == 1) {
				System.out.println(COMMAND_UNDO);
				break;
			} else {
				System.out.println(String.format(NO_CONTENT, "undo"));
				break;
			}
		case 8: // search message
			if (conditionOfExecution == 1) {
				System.out.println(COMMAND_SEARCH);
				break;
			} else {
				System.out.println(UNABLE_TO_SEARCH);
				break;
			}
		case 9: // error message
			if (conditionOfExecution == 1) {
				System.out.println(COMMAND_ERROR);
				break;
			}
		case 10: // display message
			if (conditionOfExecution == 2) {
				System.out.println(NO_CONTENT_TO_DISPLAY);
				break;
			}
		case 11: // store message
			if (conditionOfExecution == 1) {
				System.out.println(COMMAND_STORED);
				break;
			} else {
				System.out.println(UNABLE_TO_STORE);
				break;
			}
		case 12: // archive message
			if (conditionOfExecution == 1) {
				System.out.println(COMMAND_ARCHIVE);
				break;
			} else {
				System.out.println(NO_ARCHIVE);
				break;
			}
		}
	}
}
