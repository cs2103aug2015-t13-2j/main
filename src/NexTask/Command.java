package NexTask;

/**
 * Represents a command object. Each command object has commandName and one
 * other field initialized. 
 * For add command, the "task" field will be initialized.
 * For delete and complete commands, the taskNumber field will be initialized.
 * For edit command, the editSpecification field will be initialized.
 * For search command, the searchSpecification will be initialized.
 * 
 * @author Jenny
 *
 */
public class Command {
	private String commandName;
	private String directory;
	private int taskNumber;
	private Task task;
	private EditSpecification editSpecification;
	private SearchSpecification searchSpecification;

	public Command() {
		commandName = "";
		directory = "";
		taskNumber = -3;
		directory = "";
		task = null;
		editSpecification = null;
		searchSpecification = null;
	}
	
	public void setDirectory(String directory){
		this.directory = directory;
	}
	
	public String getDirectory(){
		return directory;
	}
	
	public String getCommandName() {
		return commandName;
	}

	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}

	public int getTaskNumber() {
		return taskNumber;
	}

	public void setTaskNumber(int taskNumber) {
		this.taskNumber = taskNumber;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public EditSpecification getEditSpecification() {
		return editSpecification;
	}

	public void setEditSpecification(EditSpecification editSpecification) {
		this.editSpecification = editSpecification;
	}

	public SearchSpecification getSearchSpecification() {
		return searchSpecification;
	}

	public void setSearchSpecification(SearchSpecification searchSpecification) {
		this.searchSpecification = searchSpecification;
	}

	public boolean equals(Command other) {
		boolean isArgEqual = false;
		boolean isCmdNameEqual = (this.getCommandName().equals(other.getCommandName()));
		if (isCmdNameEqual) {
			String cmdName = this.getCommandName();
			switch (cmdName) {
			case "add":
				isArgEqual = this.getTask().equals(other.getTask());
				break;
			case "edit":
				isArgEqual = this.getEditSpecification().equals(other.getEditSpecification());
				break;
			case "delete":
				isArgEqual = this.getTaskNumber() == other.getTaskNumber();
				break;
			case "complete":
				isArgEqual = this.getTaskNumber() == other.getTaskNumber();
				break;
			case "display":
				isArgEqual = isCmdNameEqual;
				break;
			case "store":
				isArgEqual = isCmdNameEqual;
			}
		} else {
			return false;
		}
		return isArgEqual;
	}

}
