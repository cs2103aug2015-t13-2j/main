
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
	private static final String LOG_PROCESS = "going to start processing";
	private static final String LOG_END = "end of processing";
	private static final String LOG_ERROR = "processing error";
	private static final String LOG_FILE_NAME = "StorageLogFile.log";
	private static final String LOG_ERROR_INITIALIZE = "Cannot intialize log file!";
	private static final String EXCEPTION_MESSAGE_1 = "Failed to create default file for incomplete tasks.";
	private static final String EXCEPTION_MESSAGE_2 = "Failed to create default file for completed tasks.";
	private static final String EXCEPTION_MESSAGE_3 = "Cannot retrieve incomplete tasks data from existing files or those files do not exist.";
	private static final String EXCEPTION_MESSAGE_4 = "Cannot retrieve completed tasks data from existing files or those files do not exist.";
	private static final String EXCEPTION_MESSAGE_5 = "Null Observer";
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
			this.fh = new FileHandler(LOG_FILE_NAME);
		} catch (SecurityException | IOException e) {
			System.out.println(LOG_ERROR_INITIALIZE);
			System.exit(1);
		}
		this.logger.addHandler(fh);
		formatter = new SimpleFormatter();  
        this.fh.setFormatter(formatter);
	}

	public static Storage getInstance() {
		logger.log(Level.INFO, LOG_PROCESS);
		if (theOne == null) {
			theOne = new Storage();
		}
		logger.log(Level.INFO, LOG_END);
		return theOne;
	}

	public String getPath() {
		logger.log(Level.INFO, LOG_PROCESS);
		return this.userPath;
	}

	public void setPath(String directory) {
		logger.log(Level.INFO, LOG_PROCESS);
		if (userPath.equals("")) {
			this.userPath = FILE_NAME;
		} else {
			this.userPath = directory + USER_FILE_NAME;
		}
		logger.log(Level.INFO, LOG_END);
	}

	public void storeToDefault() throws FileNotFoundException{
		logger.log(Level.INFO, LOG_PROCESS);
		try {
	         FileOutputStream tasksOut = new FileOutputStream(TASK_FILE_TO_RETREIVE);
	         ObjectOutputStream out = new ObjectOutputStream(tasksOut);
	         out.writeObject(taskArray);
	         out.close();
	         tasksOut.close();
	      }catch(IOException i) {
	    	  System.out.println(EXCEPTION_MESSAGE_1);
	    	  logger.log(Level.WARNING, LOG_ERROR, i);    	  
	      }
		try {
	         FileOutputStream completedOut = new FileOutputStream(COMPLETED_FILE_TO_RETREIVE);
	         ObjectOutputStream out = new ObjectOutputStream(completedOut);
	         out.writeObject(completedTasks);
	         out.close();
	         completedOut.close();
	      } catch(IOException i) {
	    	  System.out.println(EXCEPTION_MESSAGE_2);
	    	  logger.log(Level.WARNING, LOG_ERROR, i);	    	 
	      }
		logger.log(Level.INFO, LOG_END);
	}
	
	public void storeToFile() {
		logger.log(Level.INFO, LOG_PROCESS);
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
			logger.log(Level.WARNING, LOG_ERROR, e);	
		}
		logger.log(Level.INFO, LOG_END);
	}

	public void retrieve() { 
		logger.log(Level.INFO, LOG_PROCESS);
		ArrayList<Task> retrievedTasks = null;
		try {
	         FileInputStream fileIn = new FileInputStream(TASK_FILE_TO_RETREIVE);
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         retrievedTasks = (ArrayList<Task>) in.readObject();         
	         in.close();
	         fileIn.close();
	      } catch(ClassNotFoundException | IOException i) {
	    	  System.out.println(EXCEPTION_MESSAGE_3);
	    	  logger.log(Level.WARNING, LOG_ERROR, i);
	    	  System.exit(1);
	      }
		
		ArrayList<Task> retrievedCompleted = null;
		try {
	         FileInputStream fileIn = new FileInputStream(COMPLETED_FILE_TO_RETREIVE);
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         retrievedCompleted = (ArrayList<Task>) in.readObject();
	         in.close();
	         fileIn.close();
	      } catch(ClassNotFoundException | IOException i) {
	    	  System.out.println(EXCEPTION_MESSAGE_4);
	    	  logger.log(Level.WARNING, LOG_ERROR, i); 
	    	  System.exit(1);
	      }
		this.taskArray = retrievedTasks;
		this.completedTasks = retrievedCompleted;
		logger.log(Level.INFO, LOG_END);
	}


	public void markComplete(int taskNum) {
		logger.log(Level.INFO, LOG_PROCESS);
		completedTasks.add(taskArray.get(taskNum));
		taskArray.remove(taskNum);
		notifyObservers();
		logger.log(Level.INFO, LOG_END);
	}

	public int getCompletedSize() {
		return completedTasks.size();
	}

	public ArrayList<Task> getCompletedTasks() {
		return completedTasks;
	}

	public String getCompletedName(int num) {
		logger.log(Level.INFO, LOG_PROCESS);
		Task task = completedTasks.get(num);
		String taskName = task.getName();
		logger.log(Level.INFO, LOG_END);
		return taskName;
	}

	public void undoEdit() {
		logger.log(Level.INFO, LOG_PROCESS);
		Command cmd = getPrevCommand();
		taskArray.set(cmd.getTaskNumber(), cmd.getTask());
		prevCommands.remove(getCommandSize() - 1);
		notifyObservers();
		logger.log(Level.INFO, LOG_END);
	}

	public void undoAdd() {
		logger.log(Level.INFO, LOG_PROCESS);
		taskArray.remove(getNumberOfTasks() - 1);
		prevCommands.remove(getCommandSize() - 1);
		notifyObservers();
		logger.log(Level.INFO, LOG_END);
	}

	public void undoDelete() {
		logger.log(Level.INFO, LOG_PROCESS);
		Command cmd = getPrevCommand();
		taskArray.add(cmd.getTaskNumber() - 1, cmd.getTask());
		prevCommands.remove(getCommandSize() - 1);
		notifyObservers();
		logger.log(Level.INFO, LOG_END);
	}

	public void addCommand(Command cmd) {
		logger.log(Level.INFO, LOG_PROCESS);
		prevCommands.add(cmd);
		logger.log(Level.INFO, LOG_END);
	}

	public int getCommandSize() {
		return prevCommands.size();
	}

	public Command getPrevCommand() {
		return prevCommands.get(prevCommands.size() - 1);
	}

	public void add(Task task) {
		logger.log(Level.INFO, LOG_PROCESS);
		this.taskArray.add(task);
		notifyObservers();
		logger.log(Level.INFO, LOG_END);
	}

	public Task getTaskObject(int num) {
		return taskArray.get(num);
	}

	public void deleteIncompleted(int num) {
		logger.log(Level.INFO, LOG_PROCESS);
		taskArray.remove(num - 1);
		notifyObservers();
		logger.log(Level.INFO, LOG_END);
	}

	public void deleteCompleted(int num) {
		logger.log(Level.INFO, LOG_PROCESS);
		completedTasks.remove(num - 1);
		notifyObservers();
		logger.log(Level.INFO, LOG_END);
	}
	
	public void edit(int num, Task task) {
		logger.log(Level.INFO, LOG_PROCESS);
		taskArray.set(num, task);
		notifyObservers();
		logger.log(Level.INFO, LOG_END);
	}

	public ArrayList<Task> getTaskArray() {
		return this.taskArray;
	}

	public int getNumberOfTasks() {
		int numberOfTasks = taskArray.size();
		return numberOfTasks;
	}

	public String getTask(int index) {
		logger.log(Level.INFO, LOG_PROCESS);
		Task task = taskArray.get(index);
		String taskName = task.getName();
		logger.log(Level.INFO, LOG_END);
		return taskName;
	}


//@@author A0145695R
	@Override
	public void addObserver(Observer obj) {
		logger.log(Level.INFO, LOG_PROCESS);
		if(obj == null) {
			throw new NullPointerException(EXCEPTION_MESSAGE_5);
		} else if(!observerList.contains(obj)) {
			observerList.add(obj);
		}
		logger.log(Level.INFO, LOG_END);
	}

	@Override
	public void notifyObservers() {
		logger.log(Level.INFO, LOG_PROCESS);
		for(Observer o : observerList) {
			o.update();
		}
		logger.log(Level.INFO, LOG_END);
	}
}