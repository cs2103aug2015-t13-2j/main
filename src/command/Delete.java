package command;

//@@author A0145035N
/**
 * The Delete command that deletes an existing task from storage.
 */
public class Delete extends Command{
	private final String NO_CONTENT_TO_DELETE = "There is no content to delete from!";
	private final String COMMAND_DELETED = "Task has been deleted!";
	private final String UNABLE_TO_DELETE = "Sorry, the number you have entered is greater than the number of total tasks";
	
	public Delete(){
		super();
	}
	
	public String execute(){
		String delMsg = "";
		int taskNum = getTaskNumber();
		int size = storage.getNumberOfTasks();

		if (taskNum > 0 && taskNum <= size) {
			// Store the deleted task in delete command
			setTask(storage.getTaskObject(taskNum - 1));
			storage.deleteIncompleted(taskNum);
			storage.addCommand(this);
			delMsg = COMMAND_DELETED;
		} else if (taskNum > size) {
			delMsg = UNABLE_TO_DELETE;
		} else {
			delMsg = NO_CONTENT_TO_DELETE;
		}
		return delMsg;
	}
}
