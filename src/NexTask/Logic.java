package NexTask;

/**
* Logic is the class where the user commands, once parsed by CommandParser, will be passed to this component
* where Logic will streamline what the user plans to do through his inputs.
* 
* @author 
*
*/
public class Logic {
	
	public MemoryManager taskList = new MemoryManager();
	public Task task;
	public UI ui;
	
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
		} else if (taskNum > size){
			ui.printExceedSize();
		} else {
			ui.printWrongFormat();
		}
	}

	private void editCommand(Command cmd, MemoryManager taskList2) {
	// TODO Auto-generated method stub
	}

	public void addCommand(Command cmd, MemoryManager taskList) {
		task = cmd.getTask();
		taskList.add(task);
	}
}
