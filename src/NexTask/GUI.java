package NexTask;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
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
	private static final String COMPLETED_HEADING = "Completed!";
	private static final String INCOMPLETED_HEADING = "Incomplete!";
	private static final String NO_COMPLETED = "no completed tasks available";
	private static final String COMPLETED = "completed tasks available";
	private static final String NO_INCOMPLETE = "no incomplete tasks available";
	private static final String INCOMPLETE = "incomplete tasks available";
	private static final String NO_INCOMPLETE_DISPLAY = "You do not have any incomplete tasks!";
	private static final String INCOMPLETE_DISPLAY = "Here is the list of incomplete tasks!";
	private static final String NO_COMPLETE_DISPLAY = "You do not have any completed tasks!";
	private static final String COMPLETE_DISPLAY = "Here is the list of completed tasks!";
	private static final String NEGATIVE_SEARCH = "Sorry, unable to find any results for the search term!";
	private static final String POSITIVE_SEARCH = "Search results:";
	private static final String NO_SEARCH_RESULTS = "no search results";
	private static final String SEARCH_RESULTS = "Here are the search results!";
	private static final String LOG_PROCESS = "going to start processing";
	private static final String LOG_END = "end of processing";
	private static final String LOG_ERROR = "processing error";
	private static final String LOG_FILE_NAME = "LogicLogFile.log";
	private static final String LOG_ERROR_INITIALIZE = "Cannot intialize log file!";
	
	private static Logger logger = Logger.getLogger("GUI");
	private static FileHandler fh;
	private static SimpleFormatter formatter;
	
	private static Logic logic;
	private Label incompletedLabel = new Label (INCOMPLETED_HEADING);
	private Label completedLabel = new Label (COMPLETED_HEADING);
	public Label actionLabel = new Label();

	public static void main(String[] args) {
		launch(args);
		try {
			fh = new FileHandler("GUILogFile.log", true);
		} catch (SecurityException | IOException e) {
			System.out.println("Cannot intialize log file!");
			System.exit(1);
		}
		logger.addHandler(fh);
		formatter = new SimpleFormatter();  
        fh.setFormatter(formatter);
	}

	public static void initialize() {
		// scanner = new Scanner(System.in);
		logger.log(Level.INFO, LOG_PROCESS);
		Storage storage = Storage.getInstance();
		logic = new Logic();
		storage.addObserver(logic);
		logger.log(Level.INFO, LOG_END);
	}

	@Override
	public void start(Stage primaryStage) {
		logger.log(Level.INFO, LOG_PROCESS);
		initialize();
		actionLabel = CommandController.initialiseActionLabel();
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
		logger.log(Level.INFO, LOG_END);
	}

	private void handleInput(Label actionLabel, TextField userInputBox, TreeView<String> tree) {
		logger.log(Level.INFO, LOG_PROCESS);
		userInputBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				logger.log(Level.INFO, LOG_PROCESS);
				if (keyEvent.getCode() == KeyCode.ENTER) {
					String userInput = userInputBox.getText();
					userInputBox.setText("");
					if (userInput.toLowerCase().equals(HELP_COMMAND)) {
						SceneController.initialiseHelpScene();
					} else if (userInput.toLowerCase().equals(EDIT_HELP_COMMAND)) {
						SceneController.initialiseEditHelpScene();
					} else {
						String feedBackMsg = logic.executeUserCommand(userInput);
						if (feedBackMsg.equals(NO_COMPLETED)) {
							actionLabel.setText(NO_COMPLETE_DISPLAY);
							TreeController.updateTree(tree, logic.getCompletedTaskList());
							completedLabel.setFont((Font.font("Agency FB", FontWeight.BOLD, 17)));
							incompletedLabel.setFont((Font.font("Agency FB", 15)));
						} else if (feedBackMsg.equals(COMPLETED)) {
							actionLabel.setText(COMPLETE_DISPLAY);
							TreeController.updateTree(tree, logic.getCompletedTaskList());
							completedLabel.setFont((Font.font("Agency FB", FontWeight.BOLD, 17)));
							incompletedLabel.setFont((Font.font("Agency FB", 15)));
						} else if (feedBackMsg.equals(INCOMPLETE)) {
							actionLabel.setText(INCOMPLETE_DISPLAY);
							TreeController.updateTree(tree, logic.getTaskList());
							incompletedLabel.setFont((Font.font("Agency FB", FontWeight.BOLD, 17)));
							completedLabel.setFont((Font.font("Agency FB", 15)));
						} else if (feedBackMsg.equals(NO_INCOMPLETE)) {
							actionLabel.setText(NO_INCOMPLETE_DISPLAY);
							TreeController.updateTree(tree, logic.getTaskList());
							incompletedLabel.setFont((Font.font("Agency FB", FontWeight.BOLD, 17)));
							completedLabel.setFont((Font.font("Agency FB", 15)));
						} else if (feedBackMsg.equals(NO_SEARCH_RESULTS)) {
							actionLabel.setText(NEGATIVE_SEARCH);
						} else if (feedBackMsg.contains(POSITIVE_SEARCH)) {
							SceneController.intialiseSearchScene(feedBackMsg);
							actionLabel.setText(SEARCH_RESULTS);
						} else {
							actionLabel.setText(feedBackMsg);
							if (logic.getHasUpdate()) {
								logic.resetHasUpdate();
								TreeController.updateTree(tree, logic.getTaskList());
							}
						}
					}
				}
				logger.log(Level.INFO, LOG_END);
			}
			
		});
		logger.log(Level.INFO, LOG_END);
	}
	
}