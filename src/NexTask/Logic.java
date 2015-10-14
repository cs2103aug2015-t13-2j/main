package NexTask;

import java.util.ArrayList;

/**
 * Logic is the class where the user commands, once parsed by CommandParser,
 * will be passed to this component where Logic will streamline what the user
 * plans to do through his inputs.
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

	private static final int EXEC_SUCCESSFUL = 1;
	private static final int EXEC_UNSUCCESSFUL = 2;
	private static final int EXEC_UNSUCCESSFUL2 = 3;

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

	public Storage taskList = new Storage("", new ArrayList<Task>());
	public Task task;
	public UI ui = new UI();
	public CommandParser parser = new CommandParser();
	public DisplayManager display = new DisplayManager();

	public void executeUserCommand(String userInput) {
		Command cmd = getUserCommand(userInput);
		if (isValid(cmd)) {
			performCommand(cmd, taskList);
		} else {
			display.printer(EXEC_ERROR, EXEC_SUCCESSFUL);
		}
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

	private void performCommand(Command cmd, Storage taskList) {
		String commandName = cmd.getCommandName();
		if (commandName == CMD_ADD) {
			addCommand(cmd, taskList);
		} else if (commandName == CMD_EDIT) {
			editCommand(cmd, taskList);
		} else if (commandName == CMD_DELETE) {
			deleteCommand(cmd, taskList);
		} else if (commandName == CMD_DISPLAY) {
			displayCommand(cmd, taskList);
		} else if (commandName == CMD_STORE) {
			storeCommand(cmd, taskList);
		} else if (commandName == CMD_EXIT) {
			System.exit(0);
		} else if (commandName == CMD_UNDO) {
			undoCommand(cmd, taskList);
		}
	}

	// No undo store for now.
	private void undoCommand(Command cmd, Storage taskList) {
		if (taskList.getPreviousTasksSize() == 0) {
			display.printer(EXEC_UNDO, EXEC_UNSUCCESSFUL);
		} else {
			// This is for testing
			System.out.println(taskList.getPreviousTasksSize());
			taskList.undoTaskArray();
			taskList.undoPrevCommand();
			taskList.undoPrevTask();
		}
	}

	private void storeCommand(Command cmd, Storage taskList) {
		Storage storage = new Storage(cmd.getDirectory(), taskList.getTaskArray());
		storage.storeToFile();
		display.printer(EXEC_STORE, EXEC_SUCCESSFUL);
	}

	private void displayCommand(Command cmd, Storage taskList) {
		int numberOfLines = taskList.getNumberOfTasks();
		if (numberOfLines == 0) {
			display.printer(EXEC_DISPLAY, EXEC_UNSUCCESSFUL);
		} else {
			for (int i = 0; i < numberOfLines; i++) {
				String taskToDisplay = taskList.getTask(i);
				String lineToDisplay = (i + 1) + ". " + taskToDisplay;
				System.out.println(lineToDisplay);
			}
		}
	}

	private void deleteCommand(Command cmd, Storage taskList2) {
		int taskNum = cmd.getTaskNumber();
		int size = taskList2.getSize();
		if (taskNum > 0 && taskNum <= size) {

			taskList2.updatePreviousTask();
			taskList2.delete(taskNum);
			taskList2.addCommand(cmd);

			display.printer(EXEC_DELETE, EXEC_SUCCESSFUL);
		} else if (taskNum > size) {
			display.printer(EXEC_DELETE, EXEC_UNSUCCESSFUL);
		} else {
			display.printer(EXEC_DELETE, EXEC_UNSUCCESSFUL2);
		}
	}

	public void addCommand(Command cmd, Storage taskList) {

		taskList.updatePreviousTask();
		task = cmd.getTask();
		taskList.add(task);
		 display.printer(EXEC_ADD, EXEC_SUCCESSFUL);
		taskList.addCommand(cmd);
	}

	private void editCommand(Command cmd, Storage taskList) {
		EditSpecification edit = cmd.getEditSpecification();

		if (isEditSpecHasNoErrors(edit)) {
			int taskNumber = edit.getTaskNumber() - 1;
			String fieldToEdit = edit.getFieldToEdit();
			String theEdit = edit.getTheEdit();

			if (isValidTaskNumber(taskNumber)) {
				if (taskList.getTaskArray().get(taskNumber) instanceof Floating) {
					Floating newTask = (Floating) taskList.getTaskArray().get(taskNumber);
					switch (fieldToEdit) {
					case TODO_FIELD_NAME:
						// Store task and task number in command for storage
						cmd.setTask(taskList.getTaskObject(taskNumber));
						cmd.setTaskNumber(taskNumber);

						taskList.updatePreviousTask();
						newTask.editName(theEdit);
						taskList.edit(taskNumber, newTask);

						taskList.addCommand(cmd);
						display.printer(EXEC_EDIT, EXEC_SUCCESSFUL);
						break;
					default:
						display.printer(EXEC_EDIT, EXEC_UNSUCCESSFUL);
					}
				}
			} else {
				display.printer(EXEC_EDIT, EXEC_UNSUCCESSFUL2);
			}
		} else {
			determineEditError(edit);
		}
	}

	public boolean isEditSpecHasNoErrors(EditSpecification editSpec) {
		if (editSpec.getTaskNumber() == -1 || editSpec.getTaskNumber() == -2) {
			return false;
		} else {
			return true;
		}
	}

	private void determineEditError(EditSpecification editSpec) {
		if (editSpec.getTaskNumber() == -1) {
			System.out.println(ERROR_PLS_ENTER_INT);
		} else if (editSpec.getTaskNumber() == -2) {
			 System.out.println(ERROR_INVALID_NUM_ARGS_FOR_EDIT);
		}
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
