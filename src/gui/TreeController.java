package gui;

import java.util.ArrayList;

import NexTask.Logic;
import NexTask.Task;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;

/*
 * TreeController class initialises the main display of tasks & controls 
 * any update of the tree view.
 */

//@@author A0124710W

public class TreeController {

	private static final String RETRIEVE_COMMAND = "retrieve";
	private static Logic logic = new Logic();

	public TreeController() {

	}

	/**
	 * Initialises the Tree View of the GUI display
	 */
	public static TreeView<String> initialiseTree() {
		// Initialise Tree View
		TreeItem<String> root = new TreeItem<>();
		retrieveTaskList();
		// root
		root = new TreeItem<>();
		root.setExpanded(true);

		ArrayList<Task> updatedArray = logic.getTaskList();
		updateTreeView(root, updatedArray);

		// Create tree
		TreeView<String> tree = new TreeView<String>(root);
		tree.setShowRoot(false);
		tree.setEditable(true);
		tree.setStyle("-fx-background-color: grey;" + "-fx-font-family: Helvetica;" + "-fx-font-size: 13;"
				+ "-fx-text-fill: white;");

		GridPane.setConstraints(tree, 2, 8, 94, 72);
		return tree;
	}

	/**
	 * Retrieves the previous memory of tasks when users last used the program
	 * through ForRetrievalTasks.ser & ForRetrievalCompleted.ser
	 */
	public static void retrieveTaskList() {
		logic.executeUserCommand(RETRIEVE_COMMAND);
	}

	/**
	 * Creates a new TreeItem branch under the TreeView display
	 * 
	 * @param task
	 *            (task to be added)
	 * @param parent
	 *            (the main tree of the TreeView display)
	 * @param taskNumber
	 *            (task number under the tree)
	 * 
	 */
	public static void makeBranch(Task task, TreeItem<String> parent, int taskNumber) {
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
		} else if (taskStart == null && taskEnd != null) {
			TreeItem<String> endTiming = new TreeItem<>(taskEnd);
			item.getChildren().add(endTiming);
		}
	}

	/**
	 * Updates the TreeView display
	 * 
	 * @param root
	 *            (the main tree of the TreeView display)
	 * @param taskArr
	 *            (the arraylist of tasks)
	 * 
	 */
	public static void updateTreeView(TreeItem<String> root, ArrayList<Task> taskArr) {
		for (int i = 0; i < taskArr.size(); i++) {
			makeBranch(taskArr.get(i), root, i);
		}
	}

	/**
	 * Updates the branches of the tree
	 * 
	 * @param tree
	 *            (the main tree of the TreeView display)
	 * @param arr
	 *            (the arraylist of tasks)
	 * 
	 */
	public static void updateTree(TreeView<String> tree, ArrayList<Task> arr) {
		tree.setRoot(null);
		TreeItem<String> root = new TreeItem<>();
		root.setExpanded(true);
		tree.setRoot(root);
		updateTreeView(root, arr);
	}

}
