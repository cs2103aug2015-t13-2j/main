package NexTask;

import java.util.ArrayList;
import java.util.Collections;

import org.joda.time.DateTime;

/**
 * Logic is the class where the user commands, once parsed by CommandParser,
 * will be passed to this component where Logic will streamline what the user
 * plans to do through his inputs.
 * 
 * @author
 *
 */
public class Logic {
	private static final String CMD_EDIT = "edit";
	private static final String CMD_ADD = "add";
	private static final String CMD_DISPLAY = "display";
	private static final String CMD_DELETE = "delete";
	private static final String CMD_STORE = "store";
	private static final String CMD_EXIT = "exit";
	private static final String CMD_UNDO = "undo";
	private static final String CMD_COMPLETE = "complete";
	private static final String CMD_HELP = "help";
	private static final String CMD_SORT = "sort";
	private static final String CMD_ARCHIVE = "archive";
	private static final String CMD_SEARCH = "search";

	private static final String FIELD_START = "start";
	private static final String FIELD_END = "end";
	private static final String FIELD_NAME = "name";
	private static final String FIELD_DATE = "date";

	private static final String ERROR_NOTHING_TO_UNDO = "There is nothing to undo";
	private static final String ERROR_INVALID_NUM_ARGS_FOR_EDIT = "Invalid number of arguments for edit.";
	private static final String ERROR_INVALID_TASK_NUMBER = "Please enter a valid task number.";
	private static final String ERROR_PLS_ENTER_INT = "Please enter an integer as the task number.";

	private static final int EXEC_SUCCESSFUL = 1;
	private static final int EXEC_UNSUCCESSFUL = 2;
	private static final int EXEC_UNSUCCESSFUL2 = 3;

	private static final int EXEC_HELP = 2;
	private static final int EXEC_ADD = 3;
	private static final int EXEC_DELETE = 4;
	private static final int EXEC_EDIT = 5;
	private static final int EXEC_COMPLETED = 6;
	private static final int EXEC_UNDO = 7;
	private static final int EXEC_SEARCH = 8;
	private static final int EXEC_ERROR = 9;
	private static final int EXEC_DISPLAY = 10;
	private static final int EXEC_STORE = 11;
	private static final int EXEC_ARCHIVE = 12;
	private static final int EXEC_SORT = 13;

	private Storage storage;
	private CommandParser parser;
	private DisplayManager display;

	public Logic() {
		storage = Storage.getInstance();
		parser = new CommandParser();
		display = new DisplayManager();
	}
	/**
	 * Takes user input and determines if valid. If it is valid, will perform,
	 * otherwise return error message.
	 * 
	 * @param userInput
	 * @return either a message specifying the result of executing command or an
	 *         error message.
	 */
	public String executeUserCommand(String userInput) {
		Command cmd = getUserCommand(userInput);
		String printMsg = "";
		if (isValid(cmd)) {
			printMsg = performCommand(cmd, storage);
		} else {
			System.out.println(cmd.getErrorMessage());
			printMsg = display.messageSelector(EXEC_ERROR, EXEC_SUCCESSFUL);
		}
		return printMsg;
	}

	public boolean isValid(Command command) {
		if (command.getCommandName() != "invalid") {
			return true;
		} else {
			return false;
		}
	}

	public Command getUserCommand(String userInput) {
		return parser.parse(userInput);
	}

	private String performCommand(Command cmd, Storage taskList) {
		String messageToPrint = "";
		String commandName = cmd.getCommandName();
		if (commandName == CMD_ADD) {
			messageToPrint = addCommand(cmd);
		} else if (commandName == CMD_EDIT) {
			messageToPrint = editCommand(cmd);
		} else if (commandName == CMD_DELETE) {
			messageToPrint = deleteCommand(cmd);
		} else if (commandName == CMD_DISPLAY) {
			messageToPrint = displayCommand(cmd);
		} else if (commandName == CMD_STORE) {
			messageToPrint = storeCommand(cmd);
		} else if (commandName == CMD_EXIT) {
			System.exit(0);
		} else if (commandName == CMD_UNDO) {
			messageToPrint = undoCommand(cmd);
		} else if (commandName == CMD_COMPLETE) {
			messageToPrint = completeCommand(cmd);
		} else if (commandName == CMD_HELP) {
			messageToPrint = display.messageSelector(EXEC_HELP, EXEC_SUCCESSFUL);
		} else if (commandName == CMD_SORT) {
			messageToPrint = sortCommand(cmd);
		} else if (commandName == CMD_ARCHIVE) {
			messageToPrint = archiveCommand(cmd);
		} else if (commandName == CMD_SEARCH){
			messageToPrint = searchCommand(cmd);
		}
		return messageToPrint;
	}
	
	

	private String completeCommand(Command cmd) {
		cmd.setTask(storage.getTaskObject(cmd.getTaskNumber() - 1));
		storage.markComplete(cmd.getTaskNumber() - 1);
		return display.messageSelector(EXEC_COMPLETED, EXEC_SUCCESSFUL);
	}

	// No undo store for now.
	private String undoCommand(Command cmd) {
		String undoMsg = "";
		if (storage.getCommandSize() == 0) {
			undoMsg = display.messageSelector(EXEC_UNDO, EXEC_UNSUCCESSFUL);
		} else if (storage.getLastCommand().getCommandName().equals("edit")) {
			storage.undoEdit();
		} else if (storage.getLastCommand().getCommandName().equals("delete")) {
			storage.undoDelete();
		} else {
			storage.undoAdd();
		}
		undoMsg = display.messageSelector(EXEC_UNDO, EXEC_SUCCESSFUL);
		return undoMsg;
	}

	private String deleteCommand(Command cmd) {
		String delMsg = "";
		int taskNum = cmd.getTaskNumber();
		int size = storage.getSize();

		if (taskNum > 0 && taskNum <= size) {
			// Store the deleted task in delete command
			cmd.setTask(storage.getTaskObject(taskNum - 1));
			storage.delete(taskNum);
			storage.addCommand(cmd);
			delMsg = display.messageSelector(EXEC_DELETE, EXEC_SUCCESSFUL);
		} else if (taskNum > size) {
			delMsg = display.messageSelector(EXEC_DELETE, EXEC_UNSUCCESSFUL);
		} else {
			delMsg = display.messageSelector(EXEC_DELETE, EXEC_UNSUCCESSFUL2);
		}
		return delMsg;
	}

	public String addCommand(Command cmd) {
		// cmd had task alr in add
		Task task = cmd.getTask();
		storage.add(task);
		storage.addCommand(cmd);
		return display.messageSelector(EXEC_ADD, EXEC_SUCCESSFUL);
	}
	
	private String searchCommand(Command cmd){
		String searchMsg = "";
		String[] searchSpecification = cmd.getSearchSpecification().split(" ");
		
		int numOfIncomplete = storage.getSize();
		int numOfCompleted  = storage.getCompletedSize();
		int numOfResult = 0;
		
		if (numOfIncomplete > 0){
			System.out.println("Incomplete:");
			for (int i = 0; i < numOfIncomplete; i++){
				Task task = storage.getTaskArray().get(i);
				boolean match = false;
				String [] searchField = task.toString().split("[ :]+");
				for (String search: searchField){
					for (String specification: searchSpecification){
						if (search.equals(specification)){
							match = true;
							numOfResult ++;
						}
					}
				}
				if (match){
					System.out.println(i + 1 + ". " + task.toString());
				}	
			}
		}
		if (numOfCompleted > 0){
			System.out.println("Completed:");
			for (int i = 0; i < numOfCompleted; i++){
				Task task = storage.getCompletedTasks().get(i);
				boolean match = false;
				String [] searchField = task.toString().split(" :");
				for (String search: searchField){
					for (String specification: searchSpecification){
						if (search.equals(specification)){
							match = true;
							numOfResult ++;
						}
					}
				}	
				if (match){
					System.out.println(i + 1 + ". " + task.toString());
				}
			}
		}
		if (numOfResult == 0){
			searchMsg = display.messageSelector(EXEC_SEARCH, EXEC_UNSUCCESSFUL);
		} 
		return searchMsg;
	}

	private String editCommand(Command cmd) {
		String editMsg = "";
		EditSpecification edit = cmd.getEditSpecification();
		int taskNumber = edit.getTaskNumber() - 1;
		String fieldToClear = edit.getFieldToClear();

		if (isValidTaskNumber(taskNumber)) {
			Task t = storage.getTaskObject(edit.getTaskNumber() - 1);
			Task temp;
			try {
				temp = (Task) t.clone();
				cmd.setTaskNumber(taskNumber);
				storage.addCommand(cmd);
				cmd.setTask(temp);
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			
			if(!fieldToClear.equals("")) {
				editMsg = clearField(edit);
			} else {
				editMsg = editAppropriateField(edit);
			}
		} else {
			editMsg = display.messageSelector(EXEC_EDIT, EXEC_UNSUCCESSFUL2);
		}
		return editMsg;
	}

	private String editAppropriateField(EditSpecification edit) {
		String editMsg = "";
		String fieldToEdit = edit.getFieldToEdit().trim().toLowerCase();
		String theEdit = edit.getTheEdit().trim().toLowerCase();
		Task t = storage.getTaskObject(edit.getTaskNumber() - 1);
		if(fieldToEdit.equals(FIELD_START)) {
			try{ 
				t.setStart(parser.parseDateTime(parser.getDateTime(theEdit)));
			} catch(IllegalArgumentException e) {
				return display.messageSelector(EXEC_EDIT, 4);
			}
			if(t.getTaskType().equals("deadline")) {
				t.setEnd(t.getCompleteBy());
				t.setCompleteBy(null);
				t.setTaskType("event");
			} else if(t.getTaskType().equals("todo")) {
				t.setEnd(t.getStart().plusHours(1));
				t.setTaskType("event");
			}
			editMsg = display.messageSelector(EXEC_EDIT, EXEC_SUCCESSFUL);
		} else if(fieldToEdit.equals(FIELD_END)) {
			try{ 
				t.setEnd(parser.parseDateTime(parser.getDateTime(theEdit)));
			} catch(IllegalArgumentException e) {
				return display.messageSelector(EXEC_EDIT, 4);
			}
			if(t.getTaskType().equals("deadline")) {
				t.setStart(t.getCompleteBy());
				t.setCompleteBy(null);
				t.setTaskType("event");
			} else if(t.getTaskType().equals("todo")) {
				t.setStart(t.getEnd().minusHours(1));
				t.setTaskType("event");
			}
			editMsg = display.messageSelector(EXEC_EDIT, EXEC_SUCCESSFUL);
		} else if(fieldToEdit.equals("by")) {
			try{ 
				t.setCompleteBy(parser.parseDateTime(parser.getDateTime(theEdit)));
			} catch(IllegalArgumentException e) {
				return display.messageSelector(EXEC_EDIT, 4);
			}
			if(t.getTaskType().equals("event")) {
				t.setStart(null);
				t.setEnd(null);
			}
			t.setTaskType("deadline");
			editMsg = display.messageSelector(EXEC_EDIT, EXEC_SUCCESSFUL);
		} else if(fieldToEdit.equals("on")) {
			try{ 
				t.setCompleteBy(parser.parseDateTime(parser.getDateTime(theEdit)));
			} catch(IllegalArgumentException e) {
				return display.messageSelector(EXEC_EDIT, 4);
			}
			if(t.getTaskType().equals("event")) {
				t.setStart(null);
				t.setEnd(null);
			}
			t.setTaskType("deadline");
			editMsg = display.messageSelector(EXEC_EDIT, EXEC_SUCCESSFUL);
		} else if(fieldToEdit.equals("name")) {
			editMsg = display.messageSelector(EXEC_EDIT, EXEC_SUCCESSFUL);
			t.setName(theEdit);
			editMsg = display.messageSelector(EXEC_EDIT, EXEC_SUCCESSFUL);
		} else {
			editMsg = display.messageSelector(EXEC_EDIT, 4);
		}
		return editMsg;
	}

	private String clearField(EditSpecification edit) {
		String editMsg = "";
		String fieldToClear = edit.getFieldToClear().trim().toLowerCase();
		Task t = storage.getTaskObject(edit.getTaskNumber() - 1);
		if(fieldToClear.equals(FIELD_START)) {
			if(t.getTaskType().equals("event")) {
				t.setCompleteBy(t.getEnd());
				t.setStart(null);
				t.setEnd(null);
				t.setTaskType("deadline");
				editMsg = display.messageSelector(EXEC_EDIT, EXEC_SUCCESSFUL);
			}
		} else if (fieldToClear.equals(FIELD_END)) {
			if(t.getTaskType().equals("event")) {
				t.setCompleteBy(t.getStart());
				t.setStart(null);
				t.setEnd(null);
				t.setTaskType("deadline");
				editMsg = display.messageSelector(EXEC_EDIT, EXEC_SUCCESSFUL);
			}
		} else if (fieldToClear.equals("times")) {
			if(t.getTaskType().equals("event")) {
				t.setStart(null);
				t.setEnd(null);	
				editMsg = display.messageSelector(EXEC_EDIT, EXEC_SUCCESSFUL);
			} else if(t.getTaskType().equals("deadline")) {
				t.setCompleteBy(null);
				editMsg = display.messageSelector(EXEC_EDIT, EXEC_SUCCESSFUL);
			}
			t.setTaskType("todo");
		} else if (fieldToClear.equals("by")) {
			if(t.getTaskType().equals("deadline")) {
				t.setCompleteBy(null);
				t.setTaskType("todo");
				editMsg = display.messageSelector(EXEC_EDIT, EXEC_SUCCESSFUL);
			}
		} else if (fieldToClear.equals("on")) {
			if(t.getTaskType().equals("deadline")) {
				t.setCompleteBy(null);
				t.setTaskType("todo");
				editMsg = display.messageSelector(EXEC_EDIT, EXEC_SUCCESSFUL);
			}
		} else {
			editMsg = display.messageSelector(EXEC_EDIT, 4);
		}
		return editMsg;
	}

	private String storeCommand(Command cmd) {
		storage.setPath(cmd.getDirectory());
		storage.storeToFile();
		return display.messageSelector(EXEC_STORE, EXEC_SUCCESSFUL);
	}

	private String displayCommand(Command cmd) {
		String dispMsg = "";
		int numberOfLines = storage.getNumberOfTasks();
		if (numberOfLines == 0) {
			dispMsg = display.messageSelector(EXEC_DISPLAY, EXEC_UNSUCCESSFUL);
		} else {
			for (int i = 0; i < numberOfLines; i++) {
				String lineToDisplay = (i + 1) + ". " + storage.getTaskObject(i).toString();
				System.out.println(lineToDisplay);
			}
		}
		return dispMsg;
	}

	
	private String archiveCommand(Command cmd) {
		String archMsg = "";
		int numberOfCompleted = storage.getCompletedSize();
		if (numberOfCompleted == 0) {
			archMsg = display.messageSelector(EXEC_ARCHIVE, EXEC_UNSUCCESSFUL);
		} else {
			for (int i = 0; i < numberOfCompleted; i++) {
				String taskToDisplay = storage.getCompletedName(i);
				String lineToDisplay = (i + 1) + ". " + taskToDisplay;
				System.out.println(lineToDisplay);
			}
		}
		return archMsg;
	}

	private String sortCommand(Command cmd) {
		String sortMsg = "";
		switch (cmd.getSortField()) {
		case FIELD_NAME:
			sortByName();
			sortMsg = display.messageSelector(EXEC_SORT, EXEC_SUCCESSFUL);
			break;
		case FIELD_DATE:
			sortByDate();
			sortMsg = display.messageSelector(EXEC_SORT, EXEC_SUCCESSFUL);
			break;
		default:
			sortMsg = display.messageSelector(EXEC_SORT, EXEC_UNSUCCESSFUL);
		}
		return sortMsg;

	}

	private void sortByName() {
		Collections.sort(storage.getTaskArray(), new NameSorter());
	}

	private void sortByDate() {
		Collections.sort(storage.getTaskArray(), new DateSorter());
	}

	private boolean isValidTaskNumber(int taskNumber) {
		if (taskNumber < 0) {
			return false;
		} else if (taskNumber >= storage.getNumberOfTasks()) {
			return false;
		} else {
			return true;
		}
	}

	public Storage getStorage() {
		return storage;
	}

}