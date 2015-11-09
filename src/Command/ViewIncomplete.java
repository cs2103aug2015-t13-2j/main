package command;

//@@author A0145035N

public class ViewIncomplete extends Command {
	private final String NO_CONTENT_TO_DISPLAY = "Incomplete task list is empty. There is no content to display!";
	private static final String NO_INCOMPLETE_TASKS = "no incomplete tasks available";
	private static final String INCOMPLETE_TASKS = "incomplete tasks available";
	
	public ViewIncomplete(){
		super();
	}
	
	public String execute(){
		String dispMsg = "";
		int numberOfLines = storage.getNumberOfTasks();
		if (numberOfLines == 0) {
			dispMsg = NO_INCOMPLETE_TASKS;
		} else {
			dispMsg = INCOMPLETE_TASKS;
		}
		return dispMsg;
	}
}
