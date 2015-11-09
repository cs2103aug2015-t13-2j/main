package command;

import org.joda.time.DateTime;

//@@author A0145035N

import NexTask.EditSpecification;
import NexTask.Task;
import parser.CommandParser;
import parser.DateTimeParser;

public class Edit extends Command {
	private static final String FIELD_START = "start";
	private static final String FIELD_END = "end";
	private CommandParser parser;
	private final String ERROR_INVALID_DATE_FORMAT = "Invalid date format.";
	private final String EDIT_SUCCESSFUL = "Task has been edited!";
	private final String ERROR_INVALID_TASK_NUMBER = "Invalid task number.";

	public Edit() {
		super();
		parser = new CommandParser();
	}

	public String execute() {
		String editMsg = "";
		EditSpecification edit = getEditSpecification();
		int taskNumber = edit.getTaskNumber() - 1;
		String fieldToClear = edit.getFieldToClear();

		if (isValidTaskNumber(taskNumber)) {
			Task t = storage.getTaskObject(edit.getTaskNumber() - 1);
			Task temp;
			try {
				temp = (Task) t.clone();
				setTaskNumber(taskNumber);
				storage.addCommand(this);
				setTask(temp);
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}

			if (fieldToClear.equals("")) {
				editMsg = editAppropriateField(edit);
			} else {
				editMsg = clearField(edit);
			}

		} else {
			editMsg = ERROR_INVALID_TASK_NUMBER;
		}
		return editMsg;
	}

	private String editAppropriateField(EditSpecification edit) {
		String editMsg;
		String fieldToEdit = edit.getFieldToEdit().trim().toLowerCase();
		String theEdit = edit.getTheEdit();
		Task t = storage.getTaskObject(edit.getTaskNumber() - 1);
		if (fieldToEdit.equals(FIELD_START)) {
			DateTime newStart = DateTimeParser.parse(parser.getDateTime(theEdit));
			if(newStart != null) {
				t.setStart(newStart);
			} else {
				return ERROR_INVALID_DATE_FORMAT;
			}
			if (t.getTaskType().equals("deadline")) {
				t.setEnd(t.getCompleteBy());
				t.setCompleteBy(null);
				t.setTaskType("event");
			} else if (t.getTaskType().equals("todo")) {
				t.setEnd(t.getStart().plusHours(1));
				t.setTaskType("event");
			} else if (t.getTaskType().equals("event")) {
				t.setStart(DateTimeParser.parse(parser.getDateTime(theEdit)));
			}
			editMsg = EDIT_SUCCESSFUL;
		} else if (fieldToEdit.equals(FIELD_END)) {
			DateTime newEnd = DateTimeParser.parse(parser.getDateTime(theEdit));
			if(newEnd != null) {
				t.setEnd(newEnd);
			} else {
				return ERROR_INVALID_DATE_FORMAT;
			}
			if (t.getTaskType().equals("deadline")) {
				t.setStart(t.getCompleteBy());
				t.setCompleteBy(null);
				t.setTaskType("event");
			} else if (t.getTaskType().equals("todo")) {
				t.setStart(t.getEnd().minusHours(1));
				t.setTaskType("event");
			}
			editMsg = EDIT_SUCCESSFUL;
		} else if (fieldToEdit.equals("by")) {
			DateTime newBy = DateTimeParser.parse(parser.getDateTime(theEdit));
			if(newBy != null) {
				t.setCompleteBy(DateTimeParser.parse(parser.getDateTime(theEdit)));
			} else {
				return ERROR_INVALID_DATE_FORMAT;
			}
			if (t.getTaskType().equals("event")) {
				t.setStart(null);
				t.setEnd(null);
			}
			t.setTaskType("deadline");
			editMsg = EDIT_SUCCESSFUL;
		} else if (fieldToEdit.equals("on")) {
			DateTime newOn = DateTimeParser.parse(parser.getDateTime(theEdit));
			if(newOn != null) {
				t.setCompleteBy(DateTimeParser.parse(parser.getDateTime(theEdit)));
			} else {
				return ERROR_INVALID_DATE_FORMAT;
			}
			if (t.getTaskType().equals("event")) {
				t.setStart(null);
				t.setEnd(null);
			}
			t.setTaskType("deadline");
			editMsg = EDIT_SUCCESSFUL;
		} else if (fieldToEdit.equals("name")) {
			editMsg = EDIT_SUCCESSFUL;
			t.setName(theEdit);
			editMsg = EDIT_SUCCESSFUL;
		} else {
			editMsg = ERROR_INVALID_DATE_FORMAT;
		}
		return editMsg;
	}

	private String clearField(EditSpecification edit) {
		String editMsg = EDIT_SUCCESSFUL;
		String fieldToClear = edit.getFieldToClear().trim().toLowerCase();
		Task t = storage.getTaskObject(edit.getTaskNumber() - 1);
		if (fieldToClear.equals(FIELD_START)) {
			if (t.getTaskType().equals("event")) {
				t.setCompleteBy(t.getEnd());
				t.setStart(null);
				t.setEnd(null);
				t.setTaskType("deadline");
				editMsg = EDIT_SUCCESSFUL;
			}
		} else if (fieldToClear.equals(FIELD_END)) {
			if (t.getTaskType().equals("event")) {
				t.setCompleteBy(t.getStart());
				t.setStart(null);
				t.setEnd(null);
				t.setTaskType("deadline");
				editMsg = EDIT_SUCCESSFUL;
			}
		} else if (fieldToClear.equals("times")) {
			if (t.getTaskType().equals("event")) {
				t.setStart(null);
				t.setEnd(null);
				editMsg = EDIT_SUCCESSFUL;
			} else if (t.getTaskType().equals("deadline")) {
				t.setCompleteBy(null);
				editMsg = EDIT_SUCCESSFUL;
			}
			t.setTaskType("todo");
		} else if (fieldToClear.equals("by")) {
			if (t.getTaskType().equals("deadline")) {
				t.setCompleteBy(null);
				t.setTaskType("todo");
				editMsg = EDIT_SUCCESSFUL;
			}
		} else if (fieldToClear.equals("on")) {
			if (t.getTaskType().equals("deadline")) {
				t.setCompleteBy(null);
				t.setTaskType("todo");
				editMsg = EDIT_SUCCESSFUL;
			}
		} else {
			editMsg = ERROR_INVALID_DATE_FORMAT;
		}
		return editMsg;
	}

	private boolean isValidTaskNumber(int taskNumber) {
		if (taskNumber < 0) {
			return false;
		} else if (taskNumber >= storage.getNumberOfTasks()) {
			return false;
		} else {
			return true;
		}
	}

}
