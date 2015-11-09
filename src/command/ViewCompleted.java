package command;

//@@author A0145035N

import java.util.ArrayList;
import NexTask.Task;

public class ViewCompleted extends Command{
	private final String NO_COMPLETED = "There are no completed tasks to show!";
	private static final String NO_COMPLETED_TASKS = "no completed tasks available";
	private static final String COMPLETED_TASKS = "completed tasks available";
	
	public ViewCompleted (){ 
		super();
	}
	
	public String execute(){
		String archMsg = "";
		int numberOfCompleted = storage.getCompletedSize();
			if (numberOfCompleted == 0) {
				archMsg = NO_COMPLETED_TASKS;
			}
			else {
				archMsg = COMPLETED_TASKS;
			}
		return archMsg;
	}
}
