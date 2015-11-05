package Command;

//@@author A0145035N

import java.util.Collections;

import NexTask.DateSorter;
import NexTask.NameSorter;

public class Sort extends Command{
	private static final String FIELD_NAME = "name";
	private static final String FIELD_DATE = "date";
	private static final String UNABLE_TO_SORT = "Unable to sort.";
	private static final String COMMAND_SORT = "Tasks has been sorted";
	
	public Sort(){
		super();
	}
	
	public String execute(){
		String sortMsg = "";
		switch (getSortField()) {
		case FIELD_NAME:
			sortByName();
			sortMsg = COMMAND_SORT;
			break;
		case FIELD_DATE:
			sortByDate();
			sortMsg = COMMAND_SORT;
			break;
		default:
			sortMsg = UNABLE_TO_SORT;
		}
		return sortMsg;
	}
	
	private void sortByName() {
		Collections.sort(storage.getTaskArray(), new NameSorter());
	}

	private void sortByDate() {
		Collections.sort(storage.getTaskArray(), new DateSorter());
	}
}
