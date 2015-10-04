package NexTask;

public class EditSpecification {
	private int taskNumber;
	private String fieldToEdit;
	private String theEdit;
	
	public EditSpecification() {}
	
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
	
	public boolean equals(EditSpecification other) {
		if(this.getTaskNumber() == other.getTaskNumber()
				&& this.getFieldToEdit().equals(other.getFieldToEdit())
				&& this.getTheEdit().equals(other.getTheEdit())) {
			return true;
		} else {
			return false;
		}
	}
	
}
