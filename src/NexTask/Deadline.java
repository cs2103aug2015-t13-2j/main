package NexTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

public class Deadline extends Task {
	private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd/MM/yy h:mm a");

	private Date dueBy;
	
	public Deadline() {
		super();
	}
	
	public Deadline(String name) {
		super(name);
	}
	
	public Deadline(String name, Date dueBy) {
		super(name);
		this.dueBy = dueBy;
	}
	
	public Deadline(String name, String dueBy) {
		super(name);
		try {
			this.setDueBy(dueBy);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	
	public Date getDueBy() {
		return dueBy;
	}
	public void setDueBy(Date dueBy) {
		this.dueBy = dueBy;
	}
	
	public void setDueBy(String dueBy) throws ParseException {
		this.dueBy = DATE_TIME_FORMAT.parse(dueBy);
	}
	
	
	public boolean equals(Task other) {
		if(other instanceof Deadline) {
			if(this.dueBy.equals(((Deadline) other).getDueBy())
				&& this.getName().equals(other.getName())) {
				return true;
			} else {
				System.out.println("hi1");
				return false;
			}	
		} else {
			System.out.println("hi2");
			return false;
		}	
	}
	
	
	public String toString() {
		SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yy h:mm a");
		return this.getName() + " due by: " + df1.format(this.getDueBy());
	}
}
