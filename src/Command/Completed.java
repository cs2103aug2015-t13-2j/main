package Command;

//@@author A0145035N

public class Completed extends Command{
	private final String COMMAND_DONE = "Task has been marked as completed!";
	
	public Completed(){
		super();
	}
	
	public String execute(){
		setTask(storage.getTaskObject(getTaskNumber() - 1));
		storage.markComplete(getTaskNumber() - 1);
		storage.addCommand(this);
		return COMMAND_DONE;
	}
}
