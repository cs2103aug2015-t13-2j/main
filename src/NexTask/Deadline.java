package NexTask;

import java.util.Date;

public class Deadline extends Task {
	private Date dueBy;
	
	public Deadline() {
		super();
		this.dueBy = null;
	}
	
	public Deadline(String name) {
		super(name);
	}
	
	public Deadline(String name, Date dueBy) {
		super(name);
		this.dueBy = dueBy;
	}
	
	public Date getDueBy() {
		return dueBy;
	}
	public void setDueBy(Date dueBy) {
		this.dueBy = dueBy;
	}
	
	@Override
	boolean equals(Task other) {
		if(other instanceof Deadline) {
			if(this.dueBy.equals(((Deadline) other).getDueBy())
				&& this.getName().equals(other.getName())) {
				return true;
			} else {
				return false;
			}	
		} else {
			return false;
		}	
	}
	
	public String toString() {
		return "name: " + this.getName() + " due by: " + this.getDueBy().toString();
	}
}
