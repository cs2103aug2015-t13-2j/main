package command;

import NexTask.EditSpecification;
import NexTask.Task;
import storage.Storage;

/**
 * Represents a Command in NexTask. Each Command object has commandName and one
 * of the following field initialized if needed. 
 * For add command, the task field will be initialized.
 * For delete and complete commands, the taskNumber field will be initialized.
 * For edit command, the editSpecification field will be initialized.
 * For search command, the searchSpecification field will be initialized.
 * For sort command, the sortField field will be initialized.
 * For store command, the directory field will be initialized.
 * For invalid command, the errorMessage will be initialized.
 *
 */

//@@author A0145695R
public class Command implements java.io.Serializable{
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

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	
}
