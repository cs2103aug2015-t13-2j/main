package command;

//@@author A0145035N

/**
 * The Retrieve command is used to recover the previous state of the program.
 * @author Jenny
 *
 */
public class Retrieve extends Command {
	private final String COMMAND_RETRIEVE = "Data has been retrieved.";
	
	public Retrieve(){
		super();
	}
	
	public String execute(){
		storage.retrieve();
		return COMMAND_RETRIEVE;
	}
}
