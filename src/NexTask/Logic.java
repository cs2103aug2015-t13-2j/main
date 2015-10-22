package NexTask;

import java.util.ArrayList;
import java.util.Collections;

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
	private static final String CMD_COMPLETE= "complete";
	private static final String CMD_HELP = "help";
	private static final String CMD_SORT = "sort";
	private static final String CMD_ARCHIVE = "archive";
	private static final String CMD_SEARCH = "search";

	
	private static final String FIELD_START = "start";
	private static final String FIELD_END = "end";
	private static final String FIELD_NAME = "name";
	private static final String FIELD_DUE = "due";
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


	public Storage taskList = new Storage("", new ArrayList<Task>());
	public Task task;
	public UI ui = new UI();
	public CommandParser parser = new CommandParser();
	public DisplayManager display = new DisplayManager();
	public String printMsg = "";

	public String executeUserCommand(String userInput) {
		Command cmd = getUserCommand(userInput);
		if (isValid(cmd)) {
			printMsg = performCommand(cmd, taskList);
		} else {
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
			messageToPrint = addCommand(cmd, taskList);
		} else if (commandName == CMD_EDIT) {
			messageToPrint = editCommand(cmd, taskList);
		} else if (commandName == CMD_DELETE) {
			messageToPrint = deleteCommand(cmd, taskList);
		} else if (commandName == CMD_DISPLAY) {
			messageToPrint = displayCommand(cmd, taskList);
		} else if (commandName == CMD_STORE) {
			messageToPrint = storeCommand(cmd, taskList);
		} else if (commandName == CMD_EXIT) {
			System.exit(0);
		} else if (commandName == CMD_UNDO) {
			messageToPrint = undoCommand(cmd, taskList);
		} else if (commandName == CMD_COMPLETE) {
			messageToPrint= completeCommand(cmd, taskList);	
		} else if (commandName == CMD_HELP) {
			messageToPrint = display.messageSelector(EXEC_HELP, EXEC_SUCCESSFUL);
		} else if (commandName == CMD_SORT) {
			messageToPrint = sortCommand(cmd, taskList);
		} else if (commandName == CMD_ARCHIVE){
			messageToPrint = archiveCommand(cmd, taskList);
		} else if (commandName == CMD_SEARCH){
			messageToPrint = searchCommand(cmd, taskList);
		}
		return messageToPrint;
	}
	
	

	private String completeCommand(Command cmd, Storage taskList){
		cmd.setTask(taskList.getTaskObject(cmd.getTaskNumber()-1));
		taskList.markComplete(cmd.getTaskNumber() -1);
		return display.messageSelector(EXEC_COMPLETED, EXEC_SUCCESSFUL);
	}
	

	// No undo store for now.
	private String undoCommand(Command cmd, Storage taskList){
		String undoMsg = "";
		if (taskList.getCommandSize() == 0){	
			undoMsg = display.messageSelector(EXEC_UNDO, EXEC_UNSUCCESSFUL);
		} else if (taskList.getLastCommand().getCommandName().equals("edit")){		
			taskList.undoEdit();
		} else if (taskList.getLastCommand().getCommandName().equals("delete")){	
			taskList.undoDelete();
		} else {
			taskList.undoAdd(); 
		}
		undoMsg = display.messageSelector(EXEC_UNDO, EXEC_SUCCESSFUL);
		return undoMsg;
	}
	
	private String deleteCommand(Command cmd, Storage taskList2) {
		String delMsg = "";
		int taskNum = cmd.getTaskNumber();
		int size = taskList2.getSize();
		
		if (taskNum > 0 && taskNum <= size) {
			// Store the deleted task in delete command
			cmd.setTask(taskList2.getTaskObject(taskNum-1));
			taskList2.delete(taskNum);
			taskList2.addCommand(cmd);
			delMsg =  display.messageSelector(EXEC_DELETE, EXEC_SUCCESSFUL);
		} else if (taskNum > size) {
			delMsg = display.messageSelector(EXEC_DELETE, EXEC_UNSUCCESSFUL);
		} else {
			delMsg = display.messageSelector(EXEC_DELETE, EXEC_UNSUCCESSFUL2);
		}
		return delMsg;
	}

	public String addCommand(Command cmd, Storage taskList) {
		// cmd had task alr in add
		task = cmd.getTask();
		taskList.add(task);
		taskList.addCommand(cmd);
		return display.messageSelector(EXEC_ADD, EXEC_SUCCESSFUL);
	}
	
	private String searchCommand(Command cmd, Storage taskList){
		String searchMsg = "";
		String searchSpecification = cmd.getSearchSpecification();
		
		int numOfIncomplete = taskList.getSize();
		int numOfCompleted  = taskList.getCompletedSize();
		int numOfResult = 0;
		
		if (numOfIncomplete > 0){
			System.out.println("Incomplete:");
			for (int i = 0; i < numOfIncomplete; i++){
				Task task = taskList.getTaskArray().get(i);
				
				if (task.getSearchField().contains(searchSpecification)){
					System.out.println(i + 1 + ". " + task.getName());
					numOfResult ++;
				}
			}
		}
		
		if (numOfCompleted > 0){
			System.out.println("Completed:");
			
			for (int i = 0; i < numOfCompleted; i++){
				Task task = taskList.getCompletedTasks().get(i);
				if (task.getSearchField().contains(searchSpecification)){
					System.out.println(i + 1 + ". " + task.getName());
					numOfResult ++;
				}
			}
		}
		
		if (numOfResult == 0){
			searchMsg = display.messageSelector(EXEC_SEARCH, EXEC_UNSUCCESSFUL);
		} 
		
		return searchMsg;
	}

	private String editCommand(Command cmd, Storage taskList) {
		
		String editMsg = "";
		EditSpecification edit = cmd.getEditSpecification(); 
		int taskNumber = edit.getTaskNumber() - 1;
		
		if (isValidTaskNumber(taskNumber)) {
			Task t = taskList.getTaskObject(edit.getTaskNumber() - 1);
			Task temp;
			try {
				temp = (Task) t.clone();
				cmd.setTaskNumber(taskNumber);
				taskList.addCommand(cmd); 
				cmd.setTask(temp);	
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			if (t instanceof Floating) {
				editMsg = editTodo(edit, taskList);
			} else if(t instanceof Deadline) {
				editMsg = editDeadline(edit, taskList);
			} else if(t instanceof Event) {
				editMsg = editEvent(edit, taskList);
			}	
		}
		return editMsg;
	}
	
	private String editTodo(EditSpecification edit, Storage taskLists) {
		String editMsg = "";
		String fieldToEdit = edit.getFieldToEdit();
		String theEdit = edit.getTheEdit();		
		Floating f = (Floating)taskList.getTaskObject(edit.getTaskNumber() - 1);
		if(fieldToEdit.equals(FIELD_NAME)) {
			f.editName(theEdit);
			editMsg = display.messageSelector(EXEC_EDIT, EXEC_SUCCESSFUL);
		} else {
			System.out.println("invalid todo field");
			editMsg = display.messageSelector(EXEC_EDIT, EXEC_UNSUCCESSFUL2);
		}
		return editMsg;
	}
	
	private String editDeadline(EditSpecification edit, Storage taskLists) {
		String editMsg = "";
		String fieldToEdit = edit.getFieldToEdit();
		String theEdit = edit.getTheEdit();
		Deadline d = (Deadline)taskList.getTaskObject(edit.getTaskNumber() - 1);
		switch (fieldToEdit) {
		case FIELD_DUE:
			d.setDueBy(theEdit);
			editMsg = display.messageSelector(EXEC_EDIT, EXEC_SUCCESSFUL);
			break;
		case FIELD_NAME:
			d.editName(theEdit);
			editMsg = display.messageSelector(EXEC_EDIT, EXEC_SUCCESSFUL);
			break;
		default:
			editMsg = display.messageSelector(EXEC_EDIT, EXEC_UNSUCCESSFUL2);	
			System.out.println("invalid deadline field");
		}
		return editMsg;
	}
	
	private String editEvent(EditSpecification edit, Storage taskLists) {
		String editMsg = "";
		String fieldToEdit = edit.getFieldToEdit();
		String theEdit = edit.getTheEdit();
		Event e = (Event)taskList.getTaskObject(edit.getTaskNumber() - 1);
		switch (fieldToEdit.trim()) {
		case FIELD_START:
			editMsg = display.messageSelector(EXEC_EDIT, EXEC_SUCCESSFUL);
			e.setStartDateAndTime(theEdit);
			break;
		case FIELD_END:
			editMsg = display.messageSelector(EXEC_EDIT, EXEC_SUCCESSFUL);
			e.setEndDateAndTime(theEdit);
			break;
		case FIELD_NAME:
			editMsg = display.messageSelector(EXEC_EDIT, EXEC_SUCCESSFUL);
			e.editName(theEdit);
			break;
		default:
			editMsg = display.messageSelector(EXEC_EDIT, EXEC_UNSUCCESSFUL2);	
			System.out.println("invalid event field");
		}
		return editMsg;
	}
	
	private String storeCommand(Command cmd, Storage taskList) {
		Storage storage = new Storage(cmd.getDirectory(), taskList.getTaskArray());
		storage.storeToFile();
		return display.messageSelector(EXEC_STORE, EXEC_SUCCESSFUL);
	}

	private String displayCommand(Command cmd, Storage taskList) {
		String dispMsg = "";
		int numberOfLines = taskList.getNumberOfTasks();
		if (numberOfLines == 0) {
			dispMsg = display.messageSelector(EXEC_DISPLAY, EXEC_UNSUCCESSFUL);
		} else {
			for (int i = 0; i < numberOfLines; i++) {
				String taskToDisplay = taskList.getTask(i);
				String lineToDisplay = (i + 1) + ". " + taskToDisplay;
				System.out.println(lineToDisplay);
			}
		}
		return dispMsg;
	}
	
	
	private String archiveCommand(Command cmd, Storage taskList) {
		String archMsg = "";
		int numberOfCompleted = taskList.getCompletedSize();
		if (numberOfCompleted == 0){
			archMsg = display.messageSelector(EXEC_ARCHIVE, EXEC_UNSUCCESSFUL);
		} else {
			for (int i = 0; i < numberOfCompleted; i++) {
				String taskToDisplay = taskList.getCompletedName(i);
				String lineToDisplay = (i + 1) + ". " + taskToDisplay;
				System.out.println(lineToDisplay);
			}
		}
		
		return archMsg;
	}
	
	private String sortCommand(Command cmd, Storage taskList) {
		String sortMsg = "";
		switch(cmd.getSortField()) {
		case FIELD_NAME:
			sortByName(taskList);
			sortMsg = display.messageSelector(EXEC_SORT, EXEC_SUCCESSFUL);
			break;
		case FIELD_DATE:
			sortByDate(taskList);
			sortMsg = display.messageSelector(EXEC_SORT, EXEC_SUCCESSFUL);
			break;
		default:
			sortMsg = display.messageSelector(EXEC_SORT, EXEC_UNSUCCESSFUL);
			System.out.println("invalid sort field");
		} 
		return sortMsg;
		
	}
	
	private void sortByName(Storage taskList){
		Collections.sort(taskList.getTaskArray(), new NameSorter());
	}
	
	private void sortByDate(Storage taskList){
		Collections.sort(taskList.getTaskArray(), new DateSorter());
	}
	
	
	
	private boolean isValidTaskNumber(int taskNumber) {
		if (taskNumber < 0) {
			return false;
		} else if (taskNumber >= taskList.getNumberOfTasks()) {
			return false;
		} else {
			return true;
		}
	}

}