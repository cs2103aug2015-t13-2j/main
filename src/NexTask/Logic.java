package NexTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.joda.time.DateTime;

import command.Command;
import parser.CommandParser;
import storage.Storage;

/**
 * Logic is the class where the user commands, once parsed by CommandParser,
 * will be passed to this component where Logic will streamline what the user
 * plans to do through his inputs.
 * 
 * The logic class is in charge of handling user command, that is ensuring that 
 * what the user wants to be done is done. 
 * 
 * @author 
 *
 */

//@@author A0145695R
public class Logic implements Observer {
	private static final String CMD_EDIT = "edit";
	private static final String CMD_ADD = "add";
	private static final String CMD_VIEW_INCOMPLETE= "view incomplete";
	private static final String CMD_DELETE = "delete";
	private static final String CMD_STORE = "store";
	private static final String CMD_EXIT = "exit";
	private static final String CMD_UNDO = "undo";
	private static final String CMD_COMPLETE = "complete";
	private static final String CMD_HELP = "help";
	private static final String CMD_SORT = "sort";
	private static final String CMD_VIEW_COMPLETED = "view completed";
	private static final String CMD_SEARCH = "search";
	private static final String CMD_RETRIEVE = "retrieve";
	private static final String CMD_INVALID = "invalid";
	private static final String FILE_TO_RETREIVE = "ForRetrieval.txt";
	private static final String LOG_PROCESS = "going to start processing";
	private static final String LOG_END = "end of processing";
	private static final String LOG_ERROR = "processing error";
	private static final String LOG_FILE_NAME = "LogicLogFile.log";
	private static final String LOG_ERROR_INITIALIZE = "Cannot intialize log file!";	
	private static final String COMMAND_HELP = "The following commands are as shown:\n"
			+ "To add an event: add event start (date & time) end (date & time) (description of task).\n"
			+ "To add a task with deadline: add deadline due by (date & time) (description of task).\n"
			+ "To add a task with no deadline: add todo (description of task).\n"
			+ "To add a recurring task: add repeat (description of task).\n"
			+ "To mark a task as completed: finished/completed (task number).\n"
			+ "To delete a task: delete (task number).\n"
			+ "To edit a specific task: edit (task number) (name/date/start/end) (the edit).\n"
			+ "To undo a certain task: undo"
			+ "To search: search (field you want to search) (description of search term).\n"
			+ "To retrieve an archive of completed tasks: archive.\n"
			+ "To display the current list of tasks: display.\n" 
			+ "To save to: save to (filename).";
	
	private static final String INVALID_COMMAND = "There is no such command available for usage.";
	private static final String SUCCESSFUL_SORT = "Tasks has been sorted";
	private static final String SUCCESSFUL_EDIT = "Task has been edited!";

	private static Logger logger = Logger.getLogger("Logic");
	private static FileHandler fh;
	private static SimpleFormatter formatter;
	private Storage storage;
	private CommandParser parser;
	private boolean hasUpdate;

	public Logic() {
		storage = Storage.getInstance();
		parser = new CommandParser();
		hasUpdate = false;
		try {
			this.fh = new FileHandler(LOG_FILE_NAME, true);
		} catch (SecurityException | IOException e) {
			System.out.println(LOG_ERROR_INITIALIZE);
			System.exit(1);
		}
		this.logger.addHandler(fh);
		formatter = new SimpleFormatter();  
        this.fh.setFormatter(formatter);

	}
	public Logic(boolean clear) {
		storage = Storage.getInstance();
		parser = new CommandParser();
		hasUpdate = false;
		try {
			this.fh = new FileHandler(LOG_FILE_NAME, true);
		} catch (SecurityException | IOException e) {
			System.out.println(LOG_ERROR_INITIALIZE);
			System.exit(1);
		}
		this.logger.addHandler(fh);
		formatter = new SimpleFormatter();  
        this.fh.setFormatter(formatter);
        if(clear == true) {
        	this.getTaskList().clear();
        }

	}
	/**
	 * Takes user input and determines if valid. If it is valid, will perform,
	 * otherwise return error message.
	 * 
	 * @param userInput
	 * @return either a message specifying the result of executing command or an
	 *         error message.
	 */
	public String executeUserCommand(String userInput) {
		logger.log(Level.INFO, LOG_PROCESS);
		Command cmd = getUserCommand(userInput);
		String printMsg = "";
		if (isValid(cmd)) {
			printMsg = performCommand(cmd, storage);
		} else {
			printMsg = cmd.getErrorMessage();
		}
		logger.log(Level.INFO, LOG_END);
		return printMsg;
	}

	public boolean isValid(Command command) {
		if (command.getCommandName() != CMD_INVALID) {
			return true;
		} else {
			return false;
		}
	}

	public Command getUserCommand(String userInput) {
		return parser.parse(userInput);
	}

	private String performCommand(Command cmd, Storage taskList) {
		logger.log(Level.INFO, LOG_PROCESS);
		String messageToPrint = "";
		String commandName = cmd.getCommandName();
		if (commandName == CMD_ADD) {
			messageToPrint = cmd.execute();
			try {
				storage.storeToDefault();
			} catch (FileNotFoundException e) {
				System.out.println(String.format(FILE_TO_RETREIVE));
				logger.log(Level.WARNING, LOG_ERROR, e);
			}
		} else if (commandName == CMD_EDIT) {
			messageToPrint = cmd.execute();
			if(messageToPrint.equals(SUCCESSFUL_EDIT)) {
				hasUpdate = true;
			}
			try {
				storage.storeToDefault();
			} catch (FileNotFoundException e) {
				System.out.println(String.format(FILE_TO_RETREIVE));
				logger.log(Level.WARNING, LOG_ERROR, e);
			}
		} else if (commandName == CMD_DELETE) {
			messageToPrint = cmd.execute();
			try {
				storage.storeToDefault();
			} catch (FileNotFoundException e) {
				System.out.println(String.format(FILE_TO_RETREIVE));
				logger.log(Level.WARNING, LOG_ERROR, e);
			}
		} else if (commandName == CMD_VIEW_INCOMPLETE) {
			messageToPrint = cmd.execute();
		} else if (commandName == CMD_STORE) {
			messageToPrint = cmd.execute();
		} else if (commandName == CMD_EXIT) {
			System.exit(0);
		} else if (commandName == CMD_UNDO) {
			messageToPrint = cmd.execute();
			try {
				storage.storeToDefault();
			} catch (FileNotFoundException e) {
				System.out.println(String.format(FILE_TO_RETREIVE));
				logger.log(Level.WARNING, LOG_ERROR, e);
			}
		} else if (commandName == CMD_COMPLETE) {
			messageToPrint = cmd.execute();
			try {
				storage.storeToDefault();
			} catch (FileNotFoundException e) {
				System.out.println(String.format(FILE_TO_RETREIVE));
				logger.log(Level.WARNING, LOG_ERROR, e);
			}
		} else if (commandName == CMD_HELP) {
			messageToPrint = COMMAND_HELP;
		} else if (commandName == CMD_SORT) {
			messageToPrint = cmd.execute();
			if(messageToPrint.equals(SUCCESSFUL_SORT)) {
				hasUpdate = true;
			}
			try {
				storage.storeToDefault();
			} catch (FileNotFoundException e) {
				System.out.println(String.format(FILE_TO_RETREIVE));
				logger.log(Level.WARNING, LOG_ERROR, e);
			}
		} else if (commandName == CMD_VIEW_COMPLETED) {
			messageToPrint = cmd.execute();
		} else if (commandName == CMD_SEARCH){
			messageToPrint = cmd.execute();
		} else if (commandName == CMD_RETRIEVE){
			messageToPrint = cmd.execute();
		}
		
		logger.log(Level.INFO, LOG_END);
		return messageToPrint;
	}

	public Storage getStorage() {
		return storage;
	}
	
	public ArrayList<Task> getTaskList(){
		return storage.getTaskArray();
	}
	
	public ArrayList<Task> getCompletedTaskList() {
		return storage.getCompletedTasks();
	}
	
	@Override
	public void update() {
		hasUpdate = true;	
	}
	
	public boolean getHasUpdate() {
		return hasUpdate;
	}
	
	public void resetHasUpdate() {
		hasUpdate = false;
	} 

}