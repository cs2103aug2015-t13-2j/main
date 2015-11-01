package Command;

public class Undo extends Command{
	private final String COMMAND_UNDO = "Previous task has been undone!";
	private final String NO_CONTENT = "There is no task available to undo.";
	
	public Undo(){
		super();
	}
	
	public String execute(){
		String undoMsg = "";
		if (storage.getCommandSize() == 0) {
			undoMsg = NO_CONTENT;
		} else if (storage.getLastCommand().getCommandName().equals("edit")) {
			storage.undoEdit();
		} else if (storage.getLastCommand().getCommandName().equals("delete")) {
			storage.undoDelete();
		} else {
			storage.undoAdd();
		}
		undoMsg = COMMAND_UNDO;
		return undoMsg;
	}
}
