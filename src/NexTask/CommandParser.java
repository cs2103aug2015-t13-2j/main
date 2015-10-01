package NexTask;

public class CommandParser {
	public Command parseUserInput(String input) {
		System.out.println("parsed user input");
		return new Command();
	}
	
	public String[] splitString() {
		String[] commandComponents = {"add", "line"};
		return commandComponents;
	}
}

