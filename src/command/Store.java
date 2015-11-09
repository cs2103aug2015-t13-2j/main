package command;

//@@author A0145035N

/**
 * The Store command stores the incomplete task list to a file as specified by the user.
 * If a path is not specified, it would be stored in the same location as the program.
 *
 */
public class Store extends Command{
	private final String COMMAND_STORED = "Tasks has been stored into a text file!";
	
	public Store(){
		super();
	}
	
	public String execute(){
		storage.setPath(getDirectory());
		storage.storeToFile();
		return COMMAND_STORED;
	}
}
