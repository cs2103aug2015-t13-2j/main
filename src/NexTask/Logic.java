package NexTask;

/**
* Logic is the class where the user commands, once parsed by CommandParser, will be passed to this component
* where Logic will streamline what the user plans to do through his inputs.
* 
* @author 
*
*/
public class Logic {
	
	private static final String TODO_FIELD_NAME = "name";
	private static final String CMD_EDIT = "edit";
	private static final String CMD_ADD = "add";
	private static final String CMD_DISPLAY = "display";
	private static final String CMD_DELETE = "delete";
	private static final String CMD_STORE = "store";
	private static final String CMD_EXIT = "exit";
	private static final String CMD_UNDO = "undo";
	
	private static final String ERROR_NOTHING_TO_UNDO = "There is nothing to undo";
	private static final String ERROR_INVALID_NUM_ARGS_FOR_EDIT = "Invalid number of arguments for edit.";
	private static final String ERROR_INVALID_TASK_NUMBER = "Please enter a valid task number.";
	private static final String ERROR_PLS_ENTER_INT = "Please enter an integer as the task number.";
	
	public MemoryManager taskList = new MemoryManager();
	public Task task;
	public UI ui = new UI();
	public CommandParser parser = new CommandParser();
	
	
	public void executeUserCommand(String userInput) {
		Command cmd = getUserCommand(userInput);
		if(isValid(cmd)) {
			performCommand(cmd, taskList);
		} else {
			UI.displayErrorMessage();
		}	
	}
	
	public boolean isValid(Command command) {
		if(command.getCommandName() != "invalid") {
			return true;
		} else {
			return false;
		}
	}
	
	public Command getUserCommand(String userInput) {
		return parser.parse(userInput);
	}

	private void performCommand(Command cmd, MemoryManager taskList) {
		String commandName = cmd.getCommandName();
		if (commandName == CMD_ADD) {
			addCommand(cmd, taskList);
		}
		else if (commandName == CMD_EDIT) {
			editCommand(cmd, taskList);
		}
		else if (commandName == CMD_DELETE) {
			deleteCommand(cmd, taskList);
		}
		else if (commandName == CMD_DISPLAY) {
			displayCommand(cmd, taskList);
		}
		else if (commandName == CMD_STORE) {
			storeCommand(cmd, taskList);
		}
		else if (commandName == CMD_EXIT) {
			System.exit(0);
		}
		else if (commandName == CMD_UNDO) {
			undoCommand(cmd, taskList);
		}
	}
	
	
	private void undoCommand(Command cmd, MemoryManager taskList){
		if (taskList.getNumberOfTasks() == 0){
			ui.printMessage(ERROR_NOTHING_TO_UNDO);
		} else{
			taskList.updatePreviousTask();
			taskList.undoTaskArray();
			taskList.updatePreviousTask();
		}
	}
	
	private void storeCommand(Command cmd, MemoryManager taskList){
		Storage storage = new Storage(cmd.getDirectory(), taskList.getTaskArray());
		storage.storeToFile();
	}
	
	private void displayCommand(Command cmd, MemoryManager taskList) {
		int numberOfLines = taskList.getNumberOfTasks();
		if (numberOfLines == 0) {
			ui.printEmptyList();
		} else {
			for (int i=0; i<numberOfLines; i++) {
				String taskToDisplay = taskList.getTask(i);
				String lineToDisplay = (i+1) + ". " + taskToDisplay;
				System.out.println(lineToDisplay);
			}
		}
	} 


	private void deleteCommand(Command cmd, MemoryManager taskList2) {
		int taskNum = cmd.getTaskNumber();
		int size = taskList2.getSize();
		if (taskNum > 0 && taskNum <= size){
			taskList2.delete(taskNum);
			ui.printDelMessage();
		} else if (taskNum > size){
			ui.printExceedSize();
		} else {
			ui.printWrongFormat();
		}
	}

	private void editCommand(Command cmd, MemoryManager taskList) {
		EditSpecification edit = cmd.getEditSpecification();
		
		if(isEditSpecHasNoErrors(edit)) {
			int taskNumber = edit.getTaskNumber()-1;
			String fieldToEdit = edit.getFieldToEdit();
			String theEdit = edit.getTheEdit();
			
			if(isValidTaskNumber(taskNumber)) {
				if(taskList.getTaskArray().get(taskNumber) instanceof Floating) {
					Floating newTask = (Floating)taskList.getTaskArray().get(taskNumber);
					switch(fieldToEdit) {
						case TODO_FIELD_NAME :
							newTask.editName(theEdit);
							taskList.edit(taskNumber, newTask);
							ui.printEditMessage();
							break;
						default : 
							System.out.println("Invalid field to edit.");
					}
				}	
			} else {
				ui.printMessage(ERROR_INVALID_TASK_NUMBER);
			}
		} else {
			determineEditError(edit);
		}
	}

	public boolean isEditSpecHasNoErrors(EditSpecification editSpec) {
		if(editSpec.getTaskNumber() == -1 || editSpec.getTaskNumber() == -2) {
			return false;
		} else {
			return true;
		}
	}
	
	private void determineEditError(EditSpecification editSpec) {
		if(editSpec.getTaskNumber() == -1) {
			ui.printMessage(ERROR_PLS_ENTER_INT);
		} else if(editSpec.getTaskNumber() == -2) {
			ui.printMessage(ERROR_INVALID_NUM_ARGS_FOR_EDIT);
		}
	}
	
	public void addCommand(Command cmd, MemoryManager taskList) {
		task = cmd.getTask();
		taskList.add(task);
		ui.printAddMessage();
	}
	

	
	private boolean isValidTaskNumber(int taskNumber) {
		if(taskNumber < 0) {
			return false;
		} else if(taskNumber >= taskList.getNumberOfTasks()) {
			return false;
		} else {
			return true;
		}
	}
	
}
