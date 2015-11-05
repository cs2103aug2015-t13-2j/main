package NexTask;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
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
 * @@author Javan Huang
 *
 */

public class GUI extends Application {

	private static final int WELCOME_COMMAND = 1;
	private static final int WELCOME_CONDITION = 1;
	private static final int PROMPT_CONDITION = 2;
	private static final String HELP_COMMAND = "help";
	private static final String EDIT_HELP_COMMAND = "edit help";
	private static final String SEARCH_COMMAND = "search";
	private static final String RETRIEVE_COMMAND = "retrieve";
	private static final String ARCHIVE_COMMAND = "archive";
	// private static Scanner scanner;
	private static Logic logic;
	private static DisplayManager display;
	private ArrayList<Task> oldArray;

	public static void main(String[] args) {
		launch(args);
	}

	public static void initialize() {
		// scanner = new Scanner(System.in);
		Storage storage = Storage.getInstance();
		logic = new Logic();
		storage.addObserver(logic);
		display = new DisplayManager();
	}

	@Override
	public void start(Stage primaryStage) {
		initialize();
		oldArray = logic.getTaskList();

		GridPane grid = initialiseGridPane();
		Label nexTaskLabel = initialiseNexTaskLabel();
		initialiseStage(primaryStage, grid);
		Label clockLabel = initialiseClock();	
		Separator clockSeperator = initialiseClkSeperator();
		Label commandLabel = initialiseCommandLabel();
		Label actionLabel = initialiseActionLabel();
		TreeView<String> tree = initialiseTree();
		TextField userInputBox = initialiseTextBox(actionLabel);
		Separator cmdSeperator = initialiseCmdSeperator();
		
		handleInput(actionLabel, userInputBox, tree);

		grid.getChildren().addAll(commandLabel, userInputBox, clockLabel, nexTaskLabel, actionLabel);
		grid.getChildren().addAll(clockSeperator, cmdSeperator);
		grid.getChildren().add(tree);
	}

	private Label initialiseClock() {
		Label clockLabel = new Label();
		DateTimeFormatter format = DateTimeFormat.forPattern("EEEE, dd MMMM yyyy HH:mm:ss");
		Timeline timeline = initialiseTime(clockLabel, format);
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
		GridPane.setConstraints(clockLabel, 20, 0, 54, 6);
		return clockLabel;
	}

	private Timeline initialiseTime(Label clockLabel, DateTimeFormatter format) {
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				DateTime dt = new DateTime();
				clockLabel.setText(format.print(dt));
				clockLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 15));
			}
		}));
		return timeline;
	}

	private Separator initialiseClkSeperator() {
		Separator clockSeperator = new Separator();
		clockSeperator.setHalignment(HPos.CENTER);
		GridPane.setConstraints(clockSeperator, 2, 5, 90, 2);
		return clockSeperator;
	}

	private TextField initialiseTextBox(Label actionLabel) {
		TextField userInputBox = new TextField();
		userInputBox.setPromptText("Enter your input here!");
		userInputBox.setFont(Font.font("Verdana", 12));
		GridPane.setConstraints(userInputBox, 7, 83, 85, 3);
		// capture input
		return userInputBox;
	}

	private Label initialiseActionLabel() {
		Label actionLabel = new Label();
		GridPane.setConstraints(actionLabel, 7, 76, 80, 6);
		actionLabel.setText(display.messageSelector(WELCOME_COMMAND, WELCOME_CONDITION));
		actionLabel.setFont(Font.font("Verdana", 12));
		return actionLabel;
	}

	private void handleInput(Label actionLabel, TextField userInputBox, TreeView<String> tree) {
		userInputBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				if (keyEvent.getCode() == KeyCode.ENTER) {
					String userInput = userInputBox.getText();
					userInputBox.setText("");
					if (userInput.equals(HELP_COMMAND)) {
						Stage helpStage = new Stage();
						StackPane help = new StackPane();
						Label helpText = new Label(display.messageSelector(2, 1));
						helpText.setFont(Font.font("Verdana", 12));
						StackPane.setAlignment(helpText, Pos.CENTER);
						help.getChildren().add(helpText);
						Scene helpScene = new Scene(help, 650, 240);
						helpStage.setTitle("HELP GUIDE");
						helpStage.setScene(helpScene);
						// pop-up enabled
						helpStage.initModality(Modality.APPLICATION_MODAL);
						helpStage.setMinHeight(250);
						helpStage.setMaxHeight(250);
						helpStage.setMinWidth(650);
						helpStage.setMaxWidth(650);
						helpStage.show();
						helpScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
							@Override
							public void handle(KeyEvent keyEvent) {
								if (keyEvent.getCode() == KeyCode.ESCAPE) {
									helpStage.close();
								}
							}
						});
					} else if (userInput.equals(EDIT_HELP_COMMAND)) {
						Stage editStage = new Stage();
						StackPane edit = new StackPane();
						Label editHelpText = new Label(display.messageSelector(14, 1));
						editHelpText.setFont(Font.font("Verdana", 14));
						StackPane.setAlignment(editHelpText, Pos.CENTER);
						edit.getChildren().add(editHelpText);
						Scene editScene = new Scene(edit, 650, 250);
						editStage.setTitle("EDIT GUIDE");
						editStage.setScene(editScene);
						// pop-up enabled
						editStage.initModality(Modality.APPLICATION_MODAL);
						editStage.setMinHeight(250);
						editStage.setMaxHeight(250);
						editStage.setMinWidth(650);
						editStage.setMaxWidth(650);
						editStage.show();
						editScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
							@Override
							public void handle(KeyEvent keyEvent) {
								if (keyEvent.getCode() == KeyCode.ESCAPE) {
									editStage.close();
								}
							}
						});
					} else if (userInput.contains(SEARCH_COMMAND)) {
						String searchResults = logic.executeUserCommand(userInput);
						Stage searchStage = new Stage();
						StackPane search = new StackPane();
						Label searchLabel = new Label(searchResults);
						searchLabel.setFont(Font.font("Verdana", 12));
						StackPane.setAlignment(searchLabel, Pos.CENTER);
						search.getChildren().add(searchLabel);
						Scene searchScene = new Scene(search, 540, 250);
						searchStage.setTitle("Search results");
						searchStage.setScene(searchScene);
						// pop-up enabled
						searchStage.initModality(Modality.APPLICATION_MODAL);
						searchStage.setMinHeight(250);
						searchStage.setMaxHeight(250);
						searchStage.setMinWidth(540);
						searchStage.setMaxWidth(540);
						searchStage.show();
						searchScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
							@Override
							public void handle(KeyEvent keyEvent) {
								if (keyEvent.getCode() == KeyCode.ESCAPE) {
									searchStage.close();
								}
							}
						});
					} else if (userInput.equals(ARCHIVE_COMMAND)) {
						Stage archiveStage = new Stage();
						StackPane archiveStack = new StackPane();
						String archiveResults = logic.executeUserCommand(userInput);
						Label archiveLabel = new Label(archiveResults);
						archiveLabel.setFont(Font.font("Verdana", 12));
						StackPane.setAlignment(archiveLabel, Pos.CENTER);
						archiveStack.getChildren().add(archiveLabel);
						Scene archiveScene = new Scene(archiveStack, 500, 250);
						archiveStage.setTitle("Search results");
						archiveStage.setScene(archiveScene);
						// pop-up enabled
						archiveStage.initModality(Modality.APPLICATION_MODAL);
						archiveStage.setMinHeight(250);
						archiveStage.setMaxHeight(250);
						archiveStage.setMinWidth(500);
						archiveStage.setMaxWidth(500);
						archiveStage.show();
						archiveScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
							@Override
							public void handle(KeyEvent keyEvent) {
								if (keyEvent.getCode() == KeyCode.ESCAPE) {
									archiveStage.close();
								}
							}
						});
					} else {
						actionLabel.setText(logic.executeUserCommand(userInput));
						if(logic.getHasUpdate()) {
							logic.resetHasUpdate();
							tree.setRoot(null);
							TreeItem<String> root = new TreeItem<>();
							root.setExpanded(true);
							tree.setRoot(root);
							ArrayList<Task> updatedArray = logic.getTaskList();
							for (int i = 0; i < updatedArray.size(); i++) {
								makeBranch(updatedArray.get(i), root, i);
							}
						}
					}
				}
			}

		});
	}

	private Separator initialiseCmdSeperator() {
		Separator cmdSeperator = new Separator();
		cmdSeperator.setHalignment(HPos.CENTER);
		GridPane.setConstraints(cmdSeperator, 2, 74, 90, 2);
		return cmdSeperator;
	}

	private void initialiseStage(Stage primaryStage, GridPane grid) {
		primaryStage.setTitle("nexTask");
		Scene mainScene = new Scene(grid, 620, 520);

		primaryStage.setScene(mainScene);
		primaryStage.setMinWidth(640);
		primaryStage.setMinHeight(540);
		primaryStage.setMaxWidth(640);
		primaryStage.setMaxHeight(540);
		primaryStage.show();
	}

	private TreeView<String> initialiseTree() {
		// Initialise Tree View
		TreeItem<String> root;

		// root
		root = new TreeItem<>();
		root.setExpanded(true);
		
		retrieveTaskList();
		ArrayList<Task> updatedArray = logic.getTaskList();
		for (int i = 0; i < updatedArray.size(); i++) {
			makeBranch(updatedArray.get(i), root, i);
		}

		// Create tree
		TreeView<String> tree = new TreeView<String>(root);
		tree.setShowRoot(false);
		tree.setEditable(true);
		GridPane.setConstraints(tree, 2, 7, 90, 66);
		// updateTree(tree);
		return tree;
	}

	private void retrieveTaskList() {
		logic.executeUserCommand(RETRIEVE_COMMAND);
	}

	private Label initialiseCommandLabel() {
		// Command prompt
		Label commandLabel = new Label(display.messageSelector(WELCOME_COMMAND, PROMPT_CONDITION));
		GridPane.setConstraints(commandLabel, 0, 84, 7, 1);
		commandLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 12));
		return commandLabel;
	}

	private Label initialiseNexTaskLabel() {
		// nexTask label
		Label nexTaskLabel = new Label("nexTask");
		nexTaskLabel.setFont(Font.font("Verdana", 15));
		GridPane.setConstraints(nexTaskLabel, 0, 0, 7, 4);
		return nexTaskLabel;
	}

	private GridPane initialiseGridPane() {
		// Initialising gridpane
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(15, 15, 15, 15));
		grid.setVgap(5);
		grid.setHgap(6);
		return grid;
	}

	private void makeBranch(Task task, TreeItem<String> parent, int taskNumber) {
		String taskName = task.getName();
		String taskEnd = task.endToString();
		String taskStart = task.startToString();
		TreeItem<String> item = new TreeItem<>(taskNumber + 1 + ". " + taskName);
		item.setExpanded(true);
		parent.getChildren().add(item);
		if (taskStart != null && taskEnd != null) {
			TreeItem<String> endTiming = new TreeItem<>(taskEnd);
			TreeItem<String> startTiming = new TreeItem<>(taskStart);
			item.getChildren().add(startTiming);
			item.getChildren().add(endTiming);
		}
		else if (taskStart == null && taskEnd != null) {
			TreeItem<String> endTiming = new TreeItem<>(taskEnd);
			item.getChildren().add(endTiming);
		}
	}

}