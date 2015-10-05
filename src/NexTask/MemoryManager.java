package NexTask;

import java.util.ArrayList;

public class MemoryManager {
	public ArrayList<Task> taskArray;
	
	public MemoryManager(){
		taskArray = new ArrayList<Task>();
		
	}
	
	public void add(Task task){
		taskArray.add(task);
	}
	
	public void delete(int num){
		taskArray.remove(num-1);
	}
	
	
	public void edit(int num, Task task){
		// Task only has name?
		taskArray.get(num-1).editName(task.getName());
	}

	public ArrayList<Task> getTaskArray() {
		return taskArray;
	}
	
	
	// Do we really need set method?
	// in case there is a need for us to use this? (Javan)
	public void setTaskArray(ArrayList<Task> taskArray) {
		this.taskArray = taskArray;
	}
	
	
	
}
