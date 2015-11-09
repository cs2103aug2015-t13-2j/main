package NexTask;

//@@author A0145035N

import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Represents a Task in NexTask.
 * 
 * name = name of task
 * taskType = type of task, can be either event, deadline, or todo
 * start = start time for the task
 * end = end time for the task
 * completeBy = the time the task should be completed by
 * 
 * The fields are initialized according to the type of task. 
 * Events will just have name, taskType, start, and end initialized
 * Deadlines will just have name, taskType, and completeBy initialized
 * Todo will just have name and taskType initialized
 *
 */
public class Task implements Cloneable, java.io.Serializable{
	private String name;
	private String taskType;
	private DateTime start;
	private DateTime end;
	private DateTime completeBy;

	public Task() {
		this.name = "";
		this.taskType = "";
		this.start = null;
		this.end = null;
		this.completeBy = null;		
	}
	public Task(String name){
		this.name = name;
	}
	public Object clone()throws CloneNotSupportedException{  
		return super.clone();  
	}
	public String getSearchField() {
		return null;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public DateTime getStart() {
		return start;
	}
	public void setStart(DateTime start) {
		this.start = start;
	}
	public DateTime getEnd() {
		return end;
	}
	public void setEnd(DateTime end) {
		this.end = end;
	}
	public DateTime getCompleteBy() {
		return completeBy;
	}
	public void setCompleteBy(DateTime completeBy) {
		this.completeBy = completeBy;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String toString() {
		DateTimeFormatter formmater = DateTimeFormat.forPattern("dd MMM yyyy hh:mm a");
		if(taskType.trim().equals("event")) {
			return this.getName() + ": start " + formmater.print(this.getStart())
		+ " end " + formmater.print(this.getEnd());
		} else if(taskType.trim().equals("deadline")) {
			return this.getName() + ": " + formmater.print(this.getCompleteBy());
		} else {
			return this.getName();
		}
	}
	
//	@@author A0124710W
	public String startToString() {
		DateTimeFormatter formmater = DateTimeFormat.forPattern("dd MMM yyyy hh:mm a");
		if (taskType.trim().equals("event")) {
			return formmater.print(this.getStart());
		}
		else 
			return null;
	}
	
	public String endToString() {
		DateTimeFormatter formmater = DateTimeFormat.forPattern("dd MMM yyyy hh:mm a");
		if (taskType.trim().equals("event")) {
			return formmater.print(this.getEnd());
		}
		else if (taskType.trim().equals("deadline")) {
			return formmater.print(this.getCompleteBy());
		}
		else 
			return null;
		}
}
