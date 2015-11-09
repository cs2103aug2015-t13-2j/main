package gui;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import NexTask.Logic;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

/*
 * DisplayController class initialises the display of the overall GUI excluding
 * the command section and the tree view.
 */

//@@author A0124710W

public class DisplayController {

	private static Logic logic = new Logic();

	public DisplayController() {

	}

	public static void initialiseStage(Stage primaryStage, GridPane grid) {
		primaryStage.setTitle("nexTask");
		Scene mainScene = new Scene(grid, 650, 520);
		mainScene.setFill((Color.SNOW));
		primaryStage.setScene(mainScene);
		primaryStage.setMinWidth(665);
		primaryStage.setMinHeight(540);
		primaryStage.setMaxWidth(665);
		primaryStage.setMaxHeight(540);
		primaryStage.show();
	}

	public static GridPane initialiseGridPane() {
		// Initialising gridpane
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(15, 15, 15, 15));
		grid.setVgap(5);
		grid.setHgap(6);
		grid.setStyle("-fx-background-color: transparent;");
		return grid;
	}

	public static Label initialiseClock() {
		Label clockLabel = new Label();
		DateTimeFormatter format = DateTimeFormat.forPattern("EEEE, dd MMMM yyyy HH:mm:ss");
		Timeline timeline = initialiseTime(clockLabel, format);
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
		GridPane.setConstraints(clockLabel, 22, 3, 54, 4);
		return clockLabel;
	}

	public static Timeline initialiseTime(Label clockLabel, DateTimeFormatter format) {
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				DateTime dt = new DateTime();
				clockLabel.setText(format.print(dt));
				clockLabel.setFont(Font.font("Agency FB", FontWeight.BOLD, 24));
			}
		}));
		return timeline;
	}

	public static Label initialiseNexTaskLabel() {
		// nexTask label
		Label nexTaskLabel = new Label("nexTask");
		nexTaskLabel.setFont(Font.font("Agency FB", FontWeight.BOLD, 22));
		GridPane.setConstraints(nexTaskLabel, 0, 2, 9, 2);
		return nexTaskLabel;
	}

	public static void initialiseIncompletedHeading(TreeView<String> tree, Label incompletedLabel,
			Label completedLabel) {
		GridPane.setConstraints(incompletedLabel, 84, 0, 13, 2);
		incompletedLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				incompletedLabel.setFont((Font.font("Agency FB", FontWeight.BOLD, 17)));
				completedLabel.setFont((Font.font("Agency FB", 15)));
				TreeController.updateTree(tree, logic.getTaskList());
			}
		});
	}

	public static void initialiseCompletedHeading(TreeView<String> tree, Label incompletedLabel, Label completedLabel) {
		GridPane.setConstraints(completedLabel, 84, 4, 12, 2);
		completedLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				completedLabel.setFont((Font.font("Agency FB", FontWeight.BOLD, 17)));
				incompletedLabel.setFont((Font.font("Agency FB", 15)));
				TreeController.updateTree(tree, logic.getCompletedTaskList());
			}
		});
	}
}
