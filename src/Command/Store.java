package Command;

//@@author A0145035N

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
