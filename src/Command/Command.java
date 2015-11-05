package Command;

import NexTask.EditSpecification;
import NexTask.Storage;
import NexTask.Task;

/**
 * Represents a command object. Each command object has commandName and one
 * other field initialized. 
 * For add command, the "task" field will be initialized.
 * For delete and complete commands, the taskNumber field will be initialized.
 * For edit command, the editSpecification field will be initialized.
 * For search command, the searchSpecification will be initialized.
 * 
 *@author Jenny
 *
 */

//@@author Jenny
public abstract class Command implements java.io.Serializable{
	private String commandName;
	private String directory;
	private int taskNumber;
	private Task task;
	private EditSpecification editSpecification;
	private String searchSpecification;
	private String errorMessage;
	private String sortField;
	protected Storage storage;
	

	public Command() {
		commandName = "";
		directory = "";
		taskNumber = -1;
		directory = "";
		task = null;
		editSpecification = null;
		searchSpecification = "";
		errorMessage = "";
		sortField = "";
		storage = storage.getInstance();
	}
	
	public String execute(){
		String msg = "";
		return msg;
	}
	
	public String undo(){
		String msg = "";
		return msg;
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
	
	
	public String getErrorMessage() {
		return errorMessage;
	}
	

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getSearchSpecification() {
		return searchSpecification;
	}

	public void setSearchSpecification(String searchSpecification) {
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
			case "search":
				isArgEqual = this.getSearchSpecification().equals(other.getSearchSpecification());
				break;	
			case "sort":
				isArgEqual = this.getSortField().equals(other.getSortField());
				break;	
			case "invalid":
				isArgEqual = isCmdNameEqual;
				break;
			case "display":
				isArgEqual = isCmdNameEqual;
				break;
			case "store":
				isArgEqual = isCmdNameEqual;
				break;
			case "undo":
				isArgEqual = isCmdNameEqual;
				break;
			}
		} else {
			return false;
		}
		return isArgEqual;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	
}
