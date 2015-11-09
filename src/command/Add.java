package command;


//@@author A0145035N
import NexTask.Task;
/**
 * The Add command is a Command that is in charge of adding tasks to storage.. 
 *
 */
public class Add extends Command{
	private final String COMMAND_ADDED = "Task has been added!";
	
	public Add (){
		super();
	}
	
	public String execute(){
		Task task = getTask();
		storage.add(task);
		storage.addCommand(this);
		return COMMAND_ADDED;
	}
	
}
