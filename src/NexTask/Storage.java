
package NexTask;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import Command.Command;

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

	private static Storage theOne;
	private final Object MUTEX= new Object();
	
	private String userPath;
	private int taskNum;
	private ArrayList<Task> taskArray;
	private ArrayList<Command> prevCommands;
	private ArrayList<Task> completedTasks;
	private ArrayList<Observer> observerList;

	private Storage() {
		this.userPath = "";
		this.taskNum = 1;
		this.taskArray = new ArrayList<Task>();
		this.prevCommands = new ArrayList<Command>();
		this.completedTasks = new ArrayList<Task>();
		this.observerList = new ArrayList<Observer>();
	}

	public static Storage getInstance() {
		if (theOne == null) {
			theOne = new Storage();
		}
		return theOne;
	}

	public String getPath() {
		return this.userPath;
	}

	public void setPath(String directory) {
		if (userPath.equals("")) {
			this.userPath = FILE_NAME;
		} else {
			this.userPath = directory + USER_FILE_NAME;
		}
	}

	public void storeToDefault() throws FileNotFoundException{
		try
	      {
	         FileOutputStream tasksOut =
	         new FileOutputStream(TASK_FILE_TO_RETREIVE);
	         ObjectOutputStream out = new ObjectOutputStream(tasksOut);
	         out.writeObject(taskArray);
	         out.close();
	         tasksOut.close();
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
		try
	      {
	         FileOutputStream completedOut =
	         new FileOutputStream(COMPLETED_FILE_TO_RETREIVE);
	         ObjectOutputStream out = new ObjectOutputStream(completedOut);
	         out.writeObject(completedTasks);
	         out.close();
	         completedOut.close();
	      } catch(IOException i)
	      {
	          i.printStackTrace();
	      }
		
	}
	
	public void storeToFile() {
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
			System.out.println(String.format(FILE_NAME));
		}
	}

	public void retrieve() { // Use this later.
		ArrayList<Task> retrievedTasks = null;
		try
	      {
	         FileInputStream fileIn = new FileInputStream(TASK_FILE_TO_RETREIVE);
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         retrievedTasks = (ArrayList<Task>) in.readObject();
	         
	         in.close();
	         fileIn.close();
	      } catch(ClassNotFoundException | IOException i)
	      {
	         i.printStackTrace();
	         return;
	      }
		
		ArrayList<Task> retrievedCompleted = null;
		try
	      {
	         FileInputStream fileIn = new FileInputStream(COMPLETED_FILE_TO_RETREIVE);
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         retrievedCompleted = (ArrayList<Task>) in.readObject();
	         
	         in.close();
	         fileIn.close();
	      } catch(ClassNotFoundException | IOException i)
	      {
	         i.printStackTrace();
	         return;
	      }
		this.taskArray = retrievedTasks;
		this.completedTasks = retrievedCompleted;
	}

	/* Memory Manager */

	public void markComplete(int taskNum) {
		completedTasks.add(taskArray.get(taskNum));
		taskArray.remove(taskNum);
		notifyObservers();
	}

	public int getCompletedSize() {
		return completedTasks.size();
	}

	public ArrayList<Task> getCompletedTasks() {
		return completedTasks;
	}

	public String getCompletedName(int num) {
		Task task = completedTasks.get(num);
		String taskName = task.getName();
		return taskName;
	}

	public void undoEdit() {
		Command cmd = getPrevCommand();
		taskArray.set(cmd.getTaskNumber(), cmd.getTask());
		prevCommands.remove(getCommandSize() - 1);
		notifyObservers();
	}

	public void undoAdd() {
		taskArray.remove(getSize() - 1);
		prevCommands.remove(getCommandSize() - 1);
		notifyObservers();
	}

	public void undoDelete() {
		Command cmd = getPrevCommand();
		taskArray.add(cmd.getTaskNumber() - 1, cmd.getTask());
		prevCommands.remove(getCommandSize() - 1);
		notifyObservers();
	}

	public void addCommand(Command cmd) {
		prevCommands.add(cmd);
	}

	public Command getLastCommand() {
		return prevCommands.get(prevCommands.size() - 1);
	}

	public int getCommandSize() {
		return prevCommands.size();
	}

	public Command getPrevCommand() {
		return prevCommands.get(prevCommands.size() - 1);
	}

	public void add(Task task) {
		this.taskArray.add(task);
		notifyObservers();
	}

	public Task getTaskObject(int num) {
		return taskArray.get(num);
	}

	public void deleteIncompleted(int num) {
		taskArray.remove(num - 1);
		notifyObservers();
	}

	public void deleteCompleted(int num) {
		completedTasks.remove(num - 1);
		notifyObservers();
	}
	
	public void edit(int num, Task task) {
		// Task only has name?
		taskArray.set(num, task);
		notifyObservers();
	}

	public ArrayList<Task> getTaskArray() {
		return this.taskArray;
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

	public int getSize() {
		return taskArray.size();
	}

//@@author A0145695R
	@Override
	public void addObserver(Observer obj) {
		if(obj == null) {
			throw new NullPointerException("Null Observer");
		} else if(!observerList.contains(obj)) {
			observerList.add(obj);
		}
        
	}

	@Override
	public void notifyObservers() {
		for(Observer o : observerList) {
			o.update();
		}
	}
}