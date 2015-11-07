package NexTask;

import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * CommandController class initialises the display of the command prompt,
 * the text field where users can enter input, and the feedback prompt
 * processed after every user input is entered.
 * 
 * @@author A0124710W
 *
 */

//@@author A0124710W

public class CommandController {
	
	private static final String WELCOME_MSG = "Greetings! NexTask is ready for use.";
	private static final String WELCOME_HELP_MSG = "If you need any help, please type \"help\" to retrieve the help guide!";
	private static final String COMMAND_PROMPT = "Command: ";
	private static final String USER_INPUT = "Enter your input here!";
	
	public CommandController() {
		
	}
	
	// Initialises an user input text box.
	public static TextField initialiseTextBox() {
		TextField userInputBox = new TextField();
		userInputBox.setPromptText(USER_INPUT);
		userInputBox.setFont(Font.font("Verdana", 12));
		GridPane.setConstraints(userInputBox, 7, 88, 89, 3);
		// capture input
		return userInputBox;
	}
	
	// Label that relays the feedback message of users.
	public static Label initialiseActionLabel() {
	
		Label actionLabel = new Label();
		GridPane.setConstraints(actionLabel, 7, 76, 80, 14);
		actionLabel.setText(WELCOME_MSG + "\n" + WELCOME_HELP_MSG);
		actionLabel.setFont(Font.font("Verdana", 12));
		return actionLabel;
	}

	// Initialises a Command Label on the left of the user input text box.
	public static Label initialiseCommandLabel() {
		// Command prompt
		Label commandLabel = new Label(COMMAND_PROMPT);
		GridPane.setConstraints(commandLabel, 0, 89, 7, 1);
		commandLabel.setFont((Font.font("Agency FB", FontWeight.BOLD, 17)));
		return commandLabel;
	}
	
	// Initialises the seperator between the tree and the command section of GUI
	public static Separator initialiseCmdSeperator() {
		Separator cmdSeperator = new Separator();
		cmdSeperator.setHalignment(HPos.CENTER);
		GridPane.setConstraints(cmdSeperator, 2, 80, 94, 1);
		return cmdSeperator;
	}
}
