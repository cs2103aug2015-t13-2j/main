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
	
	public MemoryManager taskList = new MemoryManager();
	public Task task;
	public UI ui = new UI();
	
	public void executeUserCommand(Command cmd) {
		performCommand(cmd, taskList);
	}

	private void performCommand(Command cmd, MemoryManager taskList) {
		String commandName = cmd.getCommandName();
		if (commandName == "add") {
			addCommand(cmd, taskList);
			ui.printAddMessage();
		}
		else if (commandName == "edit") {
			editCommand(cmd, taskList);
			ui.printEditMessage();
		}
		else if (commandName == "delete") {
			deleteCommand(cmd, taskList);
			ui.printDelMessage();
		}
		else if (commandName == "display") {
			displayCommand(cmd, taskList);
		}
	}
	
	private void displayCommand(Command cmd, MemoryManager taskList2) {
		for(int i = 0; i < taskList.getNumberOfTasks(); i++) {
			System.out.println(taskList.taskArray.get(i).getName());
		}
	}
	
	private boolean isValidTaskNumber(int taskNumber) {
		if(taskNumber - 1 < 0) {
			return false;
		} else if(taskNumber > taskList.getNumberOfTasks()) {
			return false;
		} else {
			return true;
		}
	}
	
	private void deleteCommand(Command cmd, MemoryManager taskList2) {
	// TODO Auto-generated method stub
	}

	private void editCommand(Command cmd, MemoryManager taskList) {
		int taskNumber = cmd.getEditSpecification().getTaskNumber() - 1;
		String fieldToEdit = cmd.getEditSpecification().getFieldToEdit();
		String theEdit = cmd.getEditSpecification().getTheEdit();
		
		if(isValidTaskNumber(taskNumber)) {
			if(taskList.getTaskArray().get(taskNumber) instanceof Floating) {
				Floating newTask = (Floating)taskList.getTaskArray().get(taskNumber);
				switch(fieldToEdit) {
					case TODO_FIELD_NAME :
						newTask.editName(theEdit);
						taskList.edit(taskNumber, newTask);
						break;
					default : 
						System.out.println("Invalid field to edit.");
				}
			}	
		}
		
	}

	public void addCommand(Command cmd, MemoryManager taskList) {
		task = cmd.getTask();
		taskList.add(task);
	}
}
