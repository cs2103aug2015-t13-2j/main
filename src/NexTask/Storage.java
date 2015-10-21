
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
    public Task original;
    public ArrayList<Task> taskArray;
	public ArrayList<Command> prevCommands;
    
    
    public Storage(String directory, ArrayList<Task> taskArray) {
        this.userPath = directory;
        this.taskArray = taskArray;
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
	
	public void undoEdit(){
		Command cmd = getPrevCommand();
		
		System.out.println("Task number + " + cmd.getTaskNumber());
		System.out.println("Task name + " + cmd.getTask().getName());
		
		taskArray.set(cmd.getTaskNumber(), cmd.getTask());
		
		prevCommands.remove(getCommandSize()-1);
		
	}
	
	public void undoAdd(){
		taskArray.remove(getSize()-1);
		prevCommands.remove(getCommandSize()-1);
	}
	
	public void undoDelete(){
		Command cmd = getPrevCommand();
		taskArray.add(cmd.getTaskNumber()-1, cmd.getTask());
		prevCommands.remove(getCommandSize()-1);
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