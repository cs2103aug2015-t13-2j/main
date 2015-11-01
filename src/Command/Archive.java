package Command;

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
				String taskToDisplay = storage.getCompletedName(i);
				String lineToDisplay = (i + 1) + ". " + taskToDisplay;
				System.out.println(lineToDisplay);
			}
		}
		return archMsg;
	}
}
