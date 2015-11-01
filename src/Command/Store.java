package Command;

public class Store extends Command{
	private final String COMMAND_SORT = "Tasks has been sorted";
	
	public Store(){
		super();
	}
	
	public String execute(){
		storage.setPath(getDirectory());
		storage.storeToFile();
		return COMMAND_SORT;
	}
}
