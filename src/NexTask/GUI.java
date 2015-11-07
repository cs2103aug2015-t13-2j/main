package NexTask;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * GUI class will display the appropriate print messages required when user
 * input is processed and appropriate actions are executed. GUI is the main
 * interface for user interaction where users are updated through
 * the GUI with any changes made through their inputs.
 * 
 * @@author A0124710W
 *
 */

//@@author A0124710W

public class GUI extends Application {

	private static final String HELP_COMMAND = "help";
	private static final String EDIT_HELP_COMMAND = "edit help";
	private static final String SEARCH_COMMAND = "search";
	private static final String COMPLETED_COMMAND = "view completed";
	private static final String INCOMPLETED_COMMAND = "view incompleted";
	private static final String COMPLETED_HEADING = "Completed!";
	private static final String INCOMPLETED_HEADING = "Incompleted!";
	// private static Scanner scanner;
	private static Logic logic;
	private Label incompletedLabel = new Label (INCOMPLETED_HEADING);
	private Label completedLabel = new Label (COMPLETED_HEADING);

	public static void main(String[] args) {
		launch(args);
	}

	public static void initialize() {
		// scanner = new Scanner(System.in);
		Storage storage = Storage.getInstance();
		logic = new Logic();
		storage.addObserver(logic);
	}

	@Override
	public void start(Stage primaryStage) {
		initialize();
		Label actionLabel = CommandController.initialiseActionLabel();
		Label commandLabel = CommandController.initialiseCommandLabel();
		TextField textBox = CommandController.initialiseTextBox();
		Separator cmdSeperator = CommandController.initialiseCmdSeperator();
		
		TreeView<String> tree = TreeController.initialiseTree();
		
		GridPane grid = DisplayController.initialiseGridPane();
		Label nexTaskLabel = DisplayController.initialiseNexTaskLabel();
		DisplayController.initialiseStage(primaryStage, grid);
		Label clockLabel = DisplayController.initialiseClock();

		incompletedLabel.setFont((Font.font("Agency FB", FontWeight.BOLD, 15))); 
    	completedLabel.setFont((Font.font("Agency FB", 15)));
		DisplayController.initialiseCompletedHeading(tree, incompletedLabel, completedLabel);
		DisplayController.initialiseIncompletedHeading(tree, incompletedLabel, completedLabel);
		
		grid.getChildren().addAll(commandLabel, textBox, clockLabel, nexTaskLabel, actionLabel, cmdSeperator, completedLabel, incompletedLabel);
		grid.getChildren().add(tree);
		
		handleInput(actionLabel, textBox, tree);
	}

	private void handleInput(Label actionLabel, TextField userInputBox, TreeView<String> tree) {
		userInputBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				if (keyEvent.getCode() == KeyCode.ENTER) {
					String userInput = userInputBox.getText();
					userInputBox.setText("");
					if (userInput.equals(HELP_COMMAND)) {
						SceneController.initialiseHelpScene();
					} else if (userInput.equals(EDIT_HELP_COMMAND)) {
						SceneController.initialiseEditHelpScene();
					} else if (userInput.contains(SEARCH_COMMAND)) {
						SceneController.intialiseSearchScene(userInput);
					} else if (userInput.equals(COMPLETED_COMMAND)) {
						TreeController.updateTree(tree, logic.getCompletedTaskList());
						completedLabel.setFont((Font.font("Agency FB", FontWeight.BOLD, 17)));
						incompletedLabel.setFont((Font.font("Agency FB", 15)));
					}  else if (userInput.equals(INCOMPLETED_COMMAND)) {
						TreeController.updateTree(tree, logic.getTaskList());
						incompletedLabel.setFont((Font.font("Agency FB", FontWeight.BOLD, 17)));
						completedLabel.setFont((Font.font("Agency FB", 15)));
					} else {
						actionLabel.setText(logic.executeUserCommand(userInput));
						if(logic.getHasUpdate()) {
							logic.resetHasUpdate();
							tree.setRoot(null);
							TreeItem<String> root = new TreeItem<>();
							root.setExpanded(true);
							tree.setRoot(root);
							ArrayList<Task> overviewArray = logic.getTaskList();
							TreeController.updateTreeView(root, overviewArray);
						}
					}
				}
			}

		});
	}
	
}