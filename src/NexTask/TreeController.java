package NexTask;

import java.util.ArrayList;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;

/**
 * TreeController class initialises the main display of tasks & controls 
 * any update of the tree view.
 * 
 * @@author A0124710W
 *
 */

//@@author A0124710W

public class TreeController {
	
	private static final String RETRIEVE_COMMAND = "retrieve";
	private static Logic logic = new Logic();

	public TreeController() {
		
	}
	
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
		GridPane.setConstraints(tree, 2, 8, 94, 72);
		return tree;
	}
	
	public static void retrieveTaskList() {
		logic.executeUserCommand(RETRIEVE_COMMAND);
	}
	
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
		}
		else if (taskStart == null && taskEnd != null) {
			TreeItem<String> endTiming = new TreeItem<>(taskEnd);
			item.getChildren().add(endTiming);
		}
	}
	
	public static void updateTreeView(TreeItem<String> root, ArrayList<Task> taskArr) {
		for (int i = 0; i < taskArr.size(); i++) {
			makeBranch(taskArr.get(i), root, i);
		}
	}
	
	public static void updateTree(TreeView<String> tree, ArrayList<Task> arr) {
		tree.setRoot(null);
		TreeItem<String> root = new TreeItem<>();
		root.setExpanded(true);
		tree.setRoot(root);
		updateTreeView(root, arr);
	}
	
}
