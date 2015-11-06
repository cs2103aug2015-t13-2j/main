package NexTask;

/**
 * An Edit Specification is made up of three components: taskNumber: the id of
 * the task you want to edit fieldToEdit: the field you would like to edit, i.e.
 * name, startdate, etc. theEdit: the edit you would like to make to that task.
 * 
 * @author Jenny
 *
 */

//@@author A0145695R
public class EditSpecification implements java.io.Serializable {
	private int taskNumber;
	private String fieldToEdit;
	private String theEdit;
	private String fieldToClear;

	public EditSpecification() {
		taskNumber = -1;
		fieldToEdit = "";
		theEdit = "";
		fieldToClear = "";
	}

	public EditSpecification(int taskNum, String field, String edit) {
		this.taskNumber = taskNum;
		this.fieldToEdit = field;
		this.theEdit = edit;
	}

	public int getTaskNumber() {
		return taskNumber;
	}

	public void setTaskNumber(int taskNumber) {
		this.taskNumber = taskNumber;
	}

	public String getFieldToEdit() {
		return fieldToEdit;
	}

	public void setFieldToEdit(String fieldToEdit) {
		this.fieldToEdit = fieldToEdit;
	}

	public String getTheEdit() {
		return theEdit;
	}

	public void setTheEdit(String theEdit) {
		this.theEdit = theEdit;
	}

	public String getFieldToClear() {
		return fieldToClear;
	}

	public void setFieldToClear(String fieldToClear) {
		this.fieldToClear = fieldToClear;
	}

	public boolean equals(EditSpecification other) {
		if (this.getTaskNumber() == other.getTaskNumber() && this.getFieldToEdit().equals(other.getFieldToEdit())
				&& this.getTheEdit().equals(other.getTheEdit())) {
			return true;
		} else {
			return false;
		}
	}

	public String toString() {
		return "Edit Specs \n Task Number: " + this.getTaskNumber() + "\n Field To Edit: " + this.getFieldToEdit()
				+ "\n The Edit: " + this.getTheEdit() + "\n Field To Clear: " + this.getFieldToClear();
	}

}
