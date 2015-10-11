package NexTask;

import java.util.ArrayList;

public class MemoryManager {
	public ArrayList<Task> taskArray;
	public ArrayList<Task> previousTasks;
	
	public MemoryManager(){
		taskArray = new ArrayList<Task>();
		
	}
	
	public void updatePreviousTask(){
		this.previousTasks = (ArrayList<Task>) taskArray.subList(0, taskArray.size()-2);
		
		System.out.println("number of previousTasks" + previousTasks.get(previousTasks.size()));
		
	}
	
	public void undoTaskArray(){
		this.taskArray = previousTasks;
	}
	
	public void add(Task task){
		taskArray.add(task);
	}
	
	public void delete(int num){
		taskArray.remove(num-1);
	}
	
	public void edit(int num, Task task){
		// Task only has name?
		taskArray.set(num,task);
	}

	public ArrayList<Task> getTaskArray() {
		return taskArray;
	}
	
	public int getNumberOfTasks() {
		int numberOfTasks = taskArray.size();
		return numberOfTasks;
	}
	
	public String getTask(int index) {
		Task task = taskArray.get(index);
		String taskName = task.getName();
		return taskName;
	}
	
	public int getSize(){
		return taskArray.size();
	}
	
	// Do we really need set method?
	// in case there is a need for us to use this? (Javan)
	public void setTaskArray(ArrayList<Task> taskArray) {
		this.taskArray = taskArray;
	}
	
	
	
}
