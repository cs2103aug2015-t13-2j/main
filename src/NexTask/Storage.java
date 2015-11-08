
package NexTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import Command.Command;
import java.util.logging.*;

/**
 * This class is to save tasks into txt file.
 * 
 * @author Jingjing Wang
 *
 */

//@@author A0145035N
public class Storage implements java.io.Serializable, Observable {
	// Constants
	private static final String FILE_NAME = "NexTask.txt";
	private static final String USER_FILE_NAME = "\\NexTask.txt";
	private static final String TASK_FILE_TO_RETREIVE = "ForRetrievalTasks.ser";
	private static final String COMPLETED_FILE_TO_RETREIVE = "ForRetrievalCompleted.ser";
	private static final String NESTSK_HEADER = "============================== NexTask ==================================";
	private static final String NEXT_LINE = "\n";
	private static final String INCOMPLETE_TASK = "Incompleted tasks:";
	private static final String DASH_LINE = "-----------------";
	private static final String COMPLETED_TASK = "Completed tasks:";
	private static Logger logger = Logger.getLogger("Storage");
	private static FileHandler fh;
	private static SimpleFormatter formatter;
	
	private static Storage theOne;
	private final Object MUTEX= new Object();
	
	private String userPath;
	private int taskNum;
	private ArrayList<Task> taskArray;
	private ArrayList<Command> prevCommands;
	private ArrayList<Task> completedTasks;
	private ArrayList<Observer> observerList;
	

	public Storage() {
		this.userPath = "";
		this.taskNum = 1;
		this.taskArray = new ArrayList<Task>();
		this.prevCommands = new ArrayList<Command>();
		this.completedTasks = new ArrayList<Task>();
		this.observerList = new ArrayList<Observer>();
		try {
			this.fh = new FileHandler("StorageLogFile.log");
		} catch (SecurityException | IOException e) {
			System.out.println("Cannot intialize log file!");
			System.exit(1);
		}
		this.logger.addHandler(fh);
		formatter = new SimpleFormatter();  
        this.fh.setFormatter(formatter);
	}

	public static Storage getInstance() {
		logger.log(Level.INFO, "going to start processing");
		if (theOne == null) {
			theOne = new Storage();
		}
		logger.log(Level.INFO, "end of processing");
		return theOne;
	}

	public String getPath() {
		logger.log(Level.INFO, "going to start processing");
		return this.userPath;
	}

	public void setPath(String directory) {
		logger.log(Level.INFO, "going to start processing");
		if (userPath.equals("")) {
			this.userPath = FILE_NAME;
		} else {
			this.userPath = directory + USER_FILE_NAME;
		}
		logger.log(Level.INFO, "end of processing");
	}

	public void storeToDefault() throws FileNotFoundException{
		logger.log(Level.INFO, "going to start processing");
		try {
	         FileOutputStream tasksOut = new FileOutputStream(TASK_FILE_TO_RETREIVE);
	         ObjectOutputStream out = new ObjectOutputStream(tasksOut);
	         out.writeObject(taskArray);
	         out.close();
	         tasksOut.close();
	      }catch(IOException i) {
	    	  System.out.println("Failed to create default file for incomplete tasks.");
	    	  logger.log(Level.WARNING, "processing error", i);
	    	  System.exit(1);    	  
	      }
		try {
	         FileOutputStream completedOut = new FileOutputStream(COMPLETED_FILE_TO_RETREIVE);
	         ObjectOutputStream out = new ObjectOutputStream(completedOut);
	         out.writeObject(completedTasks);
	         out.close();
	         completedOut.close();
	      } catch(IOException i) {
	    	  System.out.println("Failed to create default file for completed tasks.");
	    	  logger.log(Level.WARNING, "processing error", i);
	    	  System.exit(1);	    	 
	      }
		logger.log(Level.INFO, "end of processing");
	}
	
	public void storeToFile() {
		logger.log(Level.INFO, "going to start processing");
		String savePath = getPath();
		int incompletedIndex = 1;
		int completedIndex = 1;
		try (PrintWriter writer = new PrintWriter(savePath)) {
			writer.println(NESTSK_HEADER);
			writer.println(NEXT_LINE);
			writer.println(INCOMPLETE_TASK);
			writer.println(DASH_LINE);
			for (Task line : taskArray) {
				writer.println(incompletedIndex + ". " + line.toString());
				incompletedIndex++;
			}
			writer.println(NEXT_LINE);
			writer.println(COMPLETED_TASK);
			writer.println(DASH_LINE);
			for (Task line : completedTasks) {
				writer.println(completedIndex + ". " + line.toString());
				completedIndex++;
			}
		} catch (FileNotFoundException e) {
			System.out.println(String.format(FILE_NAME) + " is not found.");
			logger.log(Level.WARNING, "processing error", e);
			System.exit(1);		
		}
		logger.log(Level.INFO, "end of processing");
	}

	public void retrieve() { 
		logger.log(Level.INFO, "going to start processing");
		ArrayList<Task> retrievedTasks = null;
		try {
	         FileInputStream fileIn = new FileInputStream(TASK_FILE_TO_RETREIVE);
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         retrievedTasks = (ArrayList<Task>) in.readObject();         
	         in.close();
	         fileIn.close();
	      } catch(ClassNotFoundException | IOException i) {
	    	  System.out.println("Cannot retrieve incomplete tasks data from existing files or those files do not exist.");
	    	  logger.log(Level.WARNING, "processing error", i);  
	      }
		
		ArrayList<Task> retrievedCompleted = null;
		try {
	         FileInputStream fileIn = new FileInputStream(COMPLETED_FILE_TO_RETREIVE);
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         retrievedCompleted = (ArrayList<Task>) in.readObject();
	         in.close();
	         fileIn.close();
	      } catch(ClassNotFoundException | IOException i) {
	    	  System.out.println("Cannot retrieve completed tasks data from existing files or those files do not exist.");
	    	  logger.log(Level.WARNING, "processing error", i);  
	      }
		this.taskArray = retrievedTasks;
		this.completedTasks = retrievedCompleted;
		logger.log(Level.INFO, "end of processing");
	}


	public void markComplete(int taskNum) {
		logger.log(Level.INFO, "going to start processing");
		completedTasks.add(taskArray.get(taskNum));
		taskArray.remove(taskNum);
		notifyObservers();
		logger.log(Level.INFO, "end of processing");
	}

	public int getCompletedSize() {
		return completedTasks.size();
	}

	public ArrayList<Task> getCompletedTasks() {
		return completedTasks;
	}

	public String getCompletedName(int num) {
		logger.log(Level.INFO, "going to start processing");
		Task task = completedTasks.get(num);
		String taskName = task.getName();
		logger.log(Level.INFO, "end of processing");
		return taskName;
	}

	public void undoEdit() {
		logger.log(Level.INFO, "going to start processing");
		Command cmd = getPrevCommand();
		taskArray.set(cmd.getTaskNumber(), cmd.getTask());
		prevCommands.remove(getCommandSize() - 1);
		notifyObservers();
		logger.log(Level.INFO, "end of processing");
	}

	public void undoAdd() {
		logger.log(Level.INFO, "going to start processing");
		taskArray.remove(getNumberOfTasks() - 1);
		prevCommands.remove(getCommandSize() - 1);
		notifyObservers();
		logger.log(Level.INFO, "end of processing");
	}

	public void undoDelete() {
		logger.log(Level.INFO, "going to start processing");
		Command cmd = getPrevCommand();
		taskArray.add(cmd.getTaskNumber() - 1, cmd.getTask());
		prevCommands.remove(getCommandSize() - 1);
		notifyObservers();
		logger.log(Level.INFO, "end of processing");
	}

	public void addCommand(Command cmd) {
		logger.log(Level.INFO, "going to start processing");
		prevCommands.add(cmd);
		logger.log(Level.INFO, "end of processing");
	}

	public int getCommandSize() {
		return prevCommands.size();
	}

	public Command getPrevCommand() {
		return prevCommands.get(prevCommands.size() - 1);
	}

	public void add(Task task) {
		logger.log(Level.INFO, "going to start processing");
		this.taskArray.add(task);
		notifyObservers();
		logger.log(Level.INFO, "end of processing");
	}

	public Task getTaskObject(int num) {
		return taskArray.get(num);
	}

	public void deleteIncompleted(int num) {
		logger.log(Level.INFO, "going to start processing");
		taskArray.remove(num - 1);
		notifyObservers();
		logger.log(Level.INFO, "end of processing");
	}

	public void deleteCompleted(int num) {
		logger.log(Level.INFO, "going to start processing");
		completedTasks.remove(num - 1);
		notifyObservers();
		logger.log(Level.INFO, "end of processing");
	}
	
	public void edit(int num, Task task) {
		logger.log(Level.INFO, "going to start processing");
		taskArray.set(num, task);
		notifyObservers();
		logger.log(Level.INFO, "end of processing");
	}

	public ArrayList<Task> getTaskArray() {
		return this.taskArray;
	}

	public int getNumberOfTasks() {
		int numberOfTasks = taskArray.size();
		return numberOfTasks;
	}

	public String getTask(int index) {
		logger.log(Level.INFO, "going to start processing");
		Task task = taskArray.get(index);
		String taskName = task.getName();
		logger.log(Level.INFO, "end of processing");
		return taskName;
	}


//@@author A0145695R
	@Override
	public void addObserver(Observer obj) {
		logger.log(Level.INFO, "going to start processing");
		if(obj == null) {
			throw new NullPointerException("Null Observer");
		} else if(!observerList.contains(obj)) {
			observerList.add(obj);
		}
		logger.log(Level.INFO, "end of processing");
	}

	@Override
	public void notifyObservers() {
		logger.log(Level.INFO, "going to start processing");
		for(Observer o : observerList) {
			o.update();
		}
		logger.log(Level.INFO, "end of processing");
	}
}