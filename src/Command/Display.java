package Command;

public class Display extends Command {
	private final String NO_CONTENT_TO_DISPLAY = "Task list is empty. There is no content to display!";
	
	public Display(){
		super();
	}
	
	public String execute(){
		String dispMsg = "";
		int numberOfLines = storage.getNumberOfTasks();
		if (numberOfLines == 0) {
			dispMsg = NO_CONTENT_TO_DISPLAY;
		} else {
			for (int i = 0; i < numberOfLines; i++) {
				String lineToDisplay = (i + 1) + ". " + storage.getTaskObject(i).toString();
				System.out.println(lineToDisplay);
			}
		}
		return dispMsg;
	}
}
