package Command;

//@@author A0145035N

import java.util.ArrayList;
import NexTask.Task;

public class ViewCompleted extends Command{
	private final String NO_COMPLETED = "There is no completed task to show!";
	
	public ViewCompleted (){
		super();
	}
	
	public String execute(){
		String archMsg = "";
		int numberOfCompleted = storage.getCompletedSize();
		if (numberOfCompleted == 0) {
			archMsg = NO_COMPLETED;
		} else {
			for (int i = 0; i < numberOfCompleted; i++) {
					ArrayList<Task> CompletedArray = storage.getCompletedTasks();
					archMsg += i + 1 + ". " + CompletedArray.get(i).toString() + "\n";
			}
		}
		return archMsg;
	}
}
