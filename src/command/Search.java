package command;

import NexTask.Task;

//@@author A0145035N

/**
 * Search command searches storage and retrieves all tasks that contain the
 * keywords specified in the user input.
 */

public class Search extends Command {
	private static final String PRINT_SEARCH_FORMAT = "Search results:\n" + "----------------\n" + "\n";
	private static final String COMPLETED_FORMAT = "========== Completed ===========\n";
	private static final String INCOMPLETE_FORMAT = "========== Incomplete ===========\n";
	private static final String UNABLE_TO_SEARCH = "no search results";
	private static final String NEXT_LINE = "\n";

	public Search() {
		super();
	}

	public String execute() {
		String searchMsg = PRINT_SEARCH_FORMAT;
		String[] searchSpecification = getSearchSpecification().split(" ");

		int numOfIncomplete = storage.getNumberOfTasks();
		int numOfCompleted = storage.getCompletedSize();
		int numOfResult = 0;

		if (numOfIncomplete > 0) {
			searchMsg += INCOMPLETE_FORMAT;
			for (int i = 0; i < numOfIncomplete; i++) {
				Task task = storage.getTaskArray().get(i);
				String start = task.startToString();
				String end = task.endToString();
				boolean match = false;
				String toSearch = task.toString() + " " + start + " " + end;
				String[] searchField = toSearch.split(" ");
				for (String search : searchField) {
					for (String specification : searchSpecification) {
						if (search.toLowerCase().contains(specification.toLowerCase())) {
							match = true;
							numOfResult++;
						}
					}
				}
				if (match) {
					searchMsg += i + 1 + ". " + task.toString() + "\n";
				}
			}
			searchMsg += NEXT_LINE;
		}

		if (numOfCompleted > 0) {
			searchMsg += COMPLETED_FORMAT;
			for (int i = 0; i < numOfCompleted; i++) {
				Task task = storage.getCompletedTasks().get(i);
				String start = task.startToString();
				String end = task.endToString();
				boolean match = false;
				String toSearch = task.toString() + " " + start + " " + end;
				String[] searchField = toSearch.split(" ");
				for (String search : searchField) {
					for (String specification : searchSpecification) {
						if (search.contains(specification)) {
							match = true;
							numOfResult++;
						}
					}
				}
				if (match) {
					searchMsg += i + 1 + ". " + task.toString() + "\n";
				}
			}
			searchMsg += NEXT_LINE;
		}
		if (numOfResult == 0) {
			searchMsg = UNABLE_TO_SEARCH;
		}
		return searchMsg;
	}
}
