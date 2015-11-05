package Command;

import java.util.ArrayList;
import NexTask.Task;

public class Archive extends Command{
	private final String NO_ARCHIVE = "There are no completed tasks in archive!";
	
	public Archive (){
		super();
	}
	
	public String execute(){
		String archMsg = "";
		int numberOfCompleted = storage.getCompletedSize();
		if (numberOfCompleted == 0) {
			archMsg = NO_ARCHIVE;
		} else {
			for (int i = 0; i < numberOfCompleted; i++) {
					ArrayList<Task> archiveArray = storage.getCompletedTasks();
					archMsg += i + 1 + ". " + archiveArray.get(i).toString() + "\n";
			}
		}
		return archMsg;
	}
}
