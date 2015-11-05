package Command;

//@@author A0145035N

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
