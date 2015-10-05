package NexTask;

import java.util.ArrayList;

public class MemoryManager {
	private ArrayList<Task> taskArray;
	
	public MemoryManager(){
		taskArray = new ArrayList<Task>();
		
	}
	
	public void add(Task task){
		taskArray.add(task);
	}
	
	public void delete(int num){
		taskArray.remove(num);
	}
	
	
	public void edit(int num, Task task){
		// Task only has name?
		taskArray.get(num).editName(task.getName());
	}

	public ArrayList<Task> getTaskArray() {
		return taskArray;
	}
	
	
	// Do we really need set method?
	public void setTaskArray(ArrayList<Task> taskArray) {
		this.taskArray = taskArray;
	}
	
	
	
}
