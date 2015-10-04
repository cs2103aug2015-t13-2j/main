package NexTask;

public class Command {
	private String commandName;
	private int taskNumber;
	private Task task;
	private EditSpecification editSpecification;
	private SearchSpecification searchSpecification;

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
			}
		} else {
			return false;
		}
		return isArgEqual;
	}

}
