package gui;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

/*
 * SceneController initialises the different scene components of the GUI,
 * i.e the help scene, the edit help scene & the search scene which pops
 * up after the user calls for it.
 */

//@@author A0124710W
public class SceneController {

	private static final String COMMAND_HELP = "The following commands are as shown:\n"
			+ "To add an event: add (description of task) start \"date & time\" end \"date & time\".\n"
			+ "To add a task with deadline: add (description of task) on/by \"date & time\".\n"
			+ "To add a task with no deadline: add (description of task).\n"
			+ "To mark a task as completed: complete (task number).\n" + "To delete a task: delete (task number).\n"
			+ "To edit a specific task: please refer to \"EDIT GUIDE\" for more information through the command: edit help \n"
			+ "To undo a certain task: undo\n" + "To search: search (description of search term).\n"
			+ "To retrieve an archive of completed tasks: view complete.\n"
			+ "To view current incomplete tasks: view incomplete.\n" + "To save to: store (filename).";
	private static final String EDIT_HELP = "The following commands are as shown:\n"
			+ "1. edit (task number) clear start [To set only a deadline timing for specified task]\n"
			+ "2. edit (task number) clear end [To set only a deadline timing for specified task]\n"
			+ "3. edit (task number) clear times [To get rid of all times for specified task]\n"
			+ "4. edit (task number) end (date & time) [To add an end time for specified task]\n"
			+ "5. edit (task number) start (date & time) [To add a start time for specified task]\n"
			+ "6. edit (task number) by (date & time)[To add a deadline timing for specified task]\n";

	public SceneController() {

	}

	/**
	 * Initialises the "help" scene. When users type "help", a popup will appear
	 * with all the help commands.
	 * 
	 */
	public static void initialiseHelpScene() {
		Stage helpStage = new Stage();
		StackPane help = new StackPane();
		help.setStyle("-fx-background-color: snow;");
		Label helpText = new Label(COMMAND_HELP);
		helpText.setTextAlignment(TextAlignment.CENTER);
		helpText.setFont(Font.font("Helvetica", 14));
		StackPane.setAlignment(helpText, Pos.CENTER);
		StackPane.setMargin(helpText, new Insets(2, 2, 2, 2));
		help.getChildren().add(helpText);
		Scene helpScene = new Scene(help, 700, 300);
		helpScene.setFill((Color.SNOW));
		helpStage.setTitle("HELP GUIDE");
		helpStage.setScene(helpScene);
		// pop-up enabled
		helpStage.initModality(Modality.APPLICATION_MODAL);
		helpStage.setMinHeight(300);
		helpStage.setMaxHeight(300);
		helpStage.setMinWidth(720);
		helpStage.setMaxWidth(720);
		helpStage.show();
		helpScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				if (keyEvent.getCode() == KeyCode.ESCAPE) {
					helpStage.close();
				}
			}
		});
	}

	/**
	 * Initialises the "edit help" scene. When users type "edit help", a popup
	 * will appear with all the edit help commands.
	 * 
	 */
	public static void initialiseEditHelpScene() {
		Stage editStage = new Stage();
		StackPane edit = new StackPane();
		edit.setStyle("-fx-background-color: snow;");
		Label editHelpText = new Label(EDIT_HELP);
		editHelpText.setTextAlignment(TextAlignment.CENTER);
		editHelpText.setFont(Font.font("Helvetica", 16));
		StackPane.setAlignment(editHelpText, Pos.CENTER);
		edit.getChildren().add(editHelpText);
		Scene editScene = new Scene(edit, 650, 250);
		editScene.setFill((Color.SNOW));
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
	}

	/**
	 * Initialises the "search" scene. When users type "search (something)", a
	 * popup will appear with all the search results.
	 * 
	 * @param searchResults
	 */
	public static void intialiseSearchScene(String searchResults) {
		Stage searchStage = new Stage();
		StackPane search = new StackPane();
		search.setStyle("-fx-background-color: snow;");
		Label searchLabel = new Label(searchResults);
		searchLabel.setTextAlignment(TextAlignment.CENTER);
		searchLabel.setFont(Font.font("Helvetica", 14));
		searchLabel.setFont(Font.font("Verdana", 12));
		StackPane.setAlignment(searchLabel, Pos.CENTER);
		search.getChildren().add(searchLabel);
		Scene searchScene = new Scene(search, 540, 200);
		searchScene.setFill((Color.SNOW));
		searchStage.setTitle("Search results");
		searchStage.setScene(searchScene);
		// pop-up enabled
		searchStage.initModality(Modality.APPLICATION_MODAL);
		searchStage.setMinHeight(200);
		searchStage.setMinWidth(540);
		searchStage.show();
		searchScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				if (keyEvent.getCode() == KeyCode.ESCAPE) {
					searchStage.close();
				}
			}
		});
	}

}
