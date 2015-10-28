package NexTask;

import java.util.Scanner;



import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * GUI class will display the appropriate print messages required when user
 * input is processed and appropriate actions are executed.
 * GUI is the main interface for user interaction.
 * 
 * @author Javan
 *
 */

public class GUI extends Application {

	private static final int WELCOME_COMMAND = 1;
	private static final int WELCOME_CONDITION = 1;
	private static final int PROMPT_CONDITION = 2;
	private static final String HELP_COMMAND = "help";
	private static Scanner scanner;
	private static Logic logic;
	private static DisplayManager display;
	
	public static void main(String[] args) {
		launch(args);
	}

	public static void initialize() {
		scanner = new Scanner(System.in);
		logic = new Logic();
		display = new DisplayManager();
	}

	@Override
	public void start(Stage primaryStage) {
		initialize();
		primaryStage.setTitle("nexTask");
		String userInput = new String();
		
		GridPane grid = initialiseGridPane();
		Label nexTaskLabel = initialiseNexTaskLabel();
		
		// Clock
		Label clockLabel = new Label();
		DateTimeFormatter format = DateTimeFormat.forPattern("EEEE, dd MMMM yyyy HH:mm:ss");
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				DateTime dt = new DateTime();
				clockLabel.setText(format.print(dt));
			}
		}));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
		GridPane.setConstraints(clockLabel, 24, 0, 35, 5);
		Separator clockSeperator = new Separator();
		clockSeperator.setHalignment(HPos.CENTER);
		GridPane.setConstraints(clockSeperator, 0, 4, 90, 2);
		

		Label commandLabel = initialiseCommandLabel();
		
		Label actionLabel = new Label();
		GridPane.setConstraints(actionLabel, 3, 76, 70, 6);
		actionLabel.setText(display.messageSelector(WELCOME_COMMAND, WELCOME_CONDITION));
	
		TreeView<String> tree = initialiseTree();
		
		// Text box
		TextField userInputBox = new TextField();
		userInputBox.setPromptText("Enter your input here!");
		GridPane.setConstraints(userInputBox, 3, 83, 87, 1);
		//capture input
		userInputBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				if (keyEvent.getCode() == KeyCode.ENTER) {
					String userInput = userInputBox.getText();
					userInputBox.setText("");
					if (userInput.contains(HELP_COMMAND)) {
						Stage helpStage = new Stage();
						StackPane help = new StackPane();
						Label helpText = new Label(display.messageSelector(2, 1));
						StackPane.setAlignment(helpText, Pos.CENTER);
						help.getChildren().add(helpText);
						Scene helpScene = new Scene(help, 600, 260);
						helpStage.setTitle("HELP GUIDE");
						helpStage.setScene(helpScene);
						// pop-up enabled
						helpStage.initModality(Modality.APPLICATION_MODAL);
						helpStage.setMinHeight(260);
						helpStage.setMaxHeight(260);
						helpStage.setMinWidth(600);
						helpStage.setMaxWidth(600);
						helpStage.show();
					}
					else {
						actionLabel.setText(logic.executeUserCommand(userInput));
						updateTree(tree);
					}
				}
			}

		});
		Separator cmdSeperator = initialiseCmdSeperator();
		
		grid.getChildren().addAll(commandLabel, userInputBox, clockLabel, nexTaskLabel, actionLabel);
		grid.getChildren().addAll(clockSeperator, cmdSeperator);
		grid.getChildren().add(tree);
		
		initialiseStage(primaryStage, grid);
	}

	private Separator initialiseCmdSeperator() {
		Separator cmdSeperator = new Separator();
		cmdSeperator.setHalignment(HPos.CENTER);
		GridPane.setConstraints(cmdSeperator, 0, 74, 90, 2);
		return cmdSeperator;
	}

	private void initialiseStage(Stage primaryStage, GridPane grid) {
		Scene mainScene = new Scene(grid, 620, 520);
		
		primaryStage.setScene(mainScene);
		primaryStage.setMinWidth(640);
		primaryStage.setMinHeight(540);
		primaryStage.setMaxWidth(640);
		primaryStage.setMaxHeight(540);
		primaryStage.show();
	}

	private TreeView<String> initialiseTree() {
		//Initialise Tree View
		TreeItem<String> root, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday;
		
		//root
		root = new TreeItem<>();
		root.setExpanded(true);
		
		Monday = makeBranch("Monday", root);
		Tuesday = makeBranch("Tuesday", root);
		Wednesday = makeBranch("Wednesday", root);
		Thursday = makeBranch("Thursday", root);
		Friday = makeBranch("Friday", root);
		Saturday = makeBranch("Saturday", root);
		Sunday = makeBranch("Sunday", root);
		
		//Create tree
		TreeView<String> tree = new TreeView<String>(root);
		tree.setShowRoot(false);
		GridPane.setConstraints(tree, 0, 7, 90, 66);
		return tree;
	}

	private Label initialiseCommandLabel() {
		// Command prompt
		Label commandLabel = new Label(display.messageSelector(WELCOME_COMMAND, PROMPT_CONDITION));
		GridPane.setConstraints(commandLabel, 0, 83, 2, 1);
		return commandLabel;
	}

	private Label initialiseNexTaskLabel() {
		//nexTask label
		Label nexTaskLabel = new Label("nexTask");
		GridPane.setConstraints(nexTaskLabel, 0, 0, 2, 2);
		return nexTaskLabel;
	}

	private GridPane initialiseGridPane() {
		//Initialising gridpane
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(15, 15, 15, 15));
		grid.setVgap(5);
		grid.setHgap(6);
		return grid;
	}

	private TreeItem<String> makeBranch(String string, TreeItem<String> parent) {
		TreeItem<String> item = new TreeItem<>(string);
		item.setExpanded(true);
		parent.getChildren().add(item);
		return item;
	}
	
	private void updateTree(TreeView<String> tree) {
		// TODO Auto-generated method stub
		
	}
	
/*	public static String getUserInput(String input) {
		String userInput = input;
		if (scanner.hasNextLine()) {
			userInput = scanner.nextLine();
		} else {
			System.exit(0);
		}
		return userInput;
	}
	
*/
}
