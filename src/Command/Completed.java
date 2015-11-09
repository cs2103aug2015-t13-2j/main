package command;

//@@author A0124710W

public class Completed extends Command{
	private static final String COMMAND_DONE = "Task has been marked as completed!";
	private static final String INPUT_LARGER_THAN_TASK_NUMBER = "Sorry, the number you have entered is greater than the number of total tasks";
	private static final String INVALID_TASK_NUMBER = "Please specify task number as an integer.";
	
	public Completed(){
		super();
	}
	
	public String execute(){
		try {
			setTask(storage.getTaskObject(getTaskNumber() - 1));
			storage.markComplete(getTaskNumber() - 1);
			storage.addCommand(this);
			return COMMAND_DONE;
		} catch (ArrayIndexOutOfBoundsException e) {
			return INVALID_TASK_NUMBER;
		} catch (IndexOutOfBoundsException e) {
			return INPUT_LARGER_THAN_TASK_NUMBER;
		}
	}
}
