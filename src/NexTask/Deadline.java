package NexTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Deadline extends Task {
	private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd/MM/yy h:mm a");
	private String searchField;

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
		this.searchField = name + dueBy.toString();
	}
	
	public Date getDueBy() {
		return dueBy;
	}
	public void setDueBy(Date dueBy) {
		this.dueBy = dueBy;
	}
	
	public void setDueBy(String dueBy) {
		try {
			this.dueBy = DATE_TIME_FORMAT.parse(dueBy);
		} catch (ParseException e) {
			System.out.println("error parsing start date");
		}
	}
	
	public String getSearchField(){
		return searchField;
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
		SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yy h:mm a");
		return "DEADLINE: " + this.getName() + " due by: " + df1.format(this.getDueBy());
	}
}
