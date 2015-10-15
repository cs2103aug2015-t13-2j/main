
package NexTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * This class is to save tasks into txt file.
 * 
 * @author Jingjing Wang
 *
 */
public class Storage {
    
    
    private static final String FILE_NAME = "NexTask.txt";
    private static final String USER_FILE_NAME = "\\NexTask.txt";
    private static final String PREV_ADD = "add";
    private static final String PREV_DELETE = "delete";
    private static final String PREV_EDIT = "edit";
    private static String userPath = "";
    private static int taskNum = 1;
    public ArrayList<Task> taskArray;
	public ArrayList<Task> previousTasks;
	public ArrayList<Command> prevCommands;
    
    
    public Storage(String directory, ArrayList<Task> taskArray) {
        this.userPath = directory;
        this.taskArray = taskArray;
        this.previousTasks = new ArrayList<Task>();
        this.prevCommands = new ArrayList<Command>();
    }
    
    public String getPath(){
            String dir = "";
            if (userPath.equals("")){
                dir = FILE_NAME;
            } else{
                dir = userPath + USER_FILE_NAME;
            }        
            return dir;
    }
    

    public void storeToFile() {
        String savePath = getPath();
        try (PrintWriter writer = new PrintWriter(savePath)) {
            for (Task line : taskArray) {
                    
                writer.println(taskNum + ". " + line.getName());
                taskNum ++;
            }
          } catch (FileNotFoundException e) {
              System.out.println(String.format(FILE_NAME));
          }
      }
    
    public String retrive(String fileName){   // Use this later.        
            return fileName;
    }
    
    /* Memory Manager */
    
    public void updatePreviousTask(){
    	if (taskArray.size() == 0){
    		return;
    	}
    	else if (getPrevCommand().getCommandName().equals(PREV_ADD)){
    		//System.out.println(getPrevCommand().getCommandName());
			previousTasks.add(taskArray.get(taskArray.size()-1));
		} 
    	else if (getPrevCommand().getCommandName().equals(PREV_DELETE)){
			previousTasks.remove(getPrevCommand().getTaskNumber());
		} 
    	else if (getPrevCommand().getCommandName().equals(PREV_EDIT)){
			previousTasks.set(getPrevCommand().getTaskNumber(), getPrevCommand().getTask());
		}
	}
    
    public void addCommand(Command cmd){
    	prevCommands.add(cmd);
    }
    
    public Command getLastCommand(){
    	return prevCommands.get(prevCommands.size()-1);
    }
    
    public int getCommandSize(){
    	return prevCommands.size();
    }
    
    public Command getPrevCommand(){
    	return prevCommands.get(prevCommands.size()-1);
    }
    
    public void undoPrevCommand(){
    	prevCommands.remove(prevCommands.size()-1);
    }
    
    public void undoPrevTask(){ // This is not the way to do undoPrevTask...
    	Command c = prevCommands.get(prevCommands.size()-1);
    	if (c.getCommandName().equals(PREV_ADD) ){
    		previousTasks.remove(previousTasks.size()-1);
    	} else if (c.getCommandName().equals(PREV_DELETE)){
    		previousTasks.add(c.getTaskNumber(), c.getTask());
    	} else{
    		previousTasks.set(c.getTaskNumber(), c.getTask());
    	}
    	
    	
    }
    
	public void undoTaskArray(){
		taskArray = new ArrayList<Task>();
		for (Task task: previousTasks){
			taskArray.add(task);
		}
	}
	
	public void add(Task task){
		taskArray.add(task);
	}
	
	public Task getTaskObject(int num){
		return taskArray.get(num);
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
	
	public int getPreviousTasksSize(){
		return previousTasks.size();
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