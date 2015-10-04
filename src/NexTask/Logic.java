package NexTask;

public class Logic {
	public String executeUserCommand(Command cmd) {
		String commandName = cmd.getCommandName();
		String feedback = "executed command " + "\"" + commandName + "\"";
		return feedback;
	}
}
