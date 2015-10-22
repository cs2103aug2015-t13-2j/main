package NexTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Event extends Task {
	private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd/MM/yy h:mm a");
	
	private Date startDateAndTime;
	private Date endDateAndTime;
	private String searchField;
	
	public Event(String name) {
		super(name);
	}
	
	public Event(String name, Date start,  Date end) {
		super(name);
		this.startDateAndTime = start;
		this.endDateAndTime = end;
		this.searchField = name + start.toString() + end.toString();
	}
	
	public Event(String name, String start,  String end) throws ParseException {
		super(name);
		this.setStartDateAndTime(start);
		this.setEndDateAndTime(end);
	}
	
	public boolean equals(Task other) {
		if(other instanceof Event)  {
			if(this.startDateAndTime.equals(((Event) other).getStartDateAndTime())
					&& this.endDateAndTime.equals(((Event) other).getEndDateAndTime())
					&& this.getName().equals(other.getName())) {
					return true;
				} else {
					return false;
				}	
			} else {
				return false;
			}	
	}

	public Date getStartDateAndTime() {
		return startDateAndTime;
	}

	public void setStartDateAndTime(Date startDateAndTime) {
		this.startDateAndTime = startDateAndTime;
	}
	
	public void setStartDateAndTime(String startDateAndTime) throws ParseException {

			this.startDateAndTime = DATE_TIME_FORMAT.parse(startDateAndTime);
	
	}

	public Date getEndDateAndTime() {
		return endDateAndTime;
	}
	
	public String getSearchField(){
		return searchField;
	}

	public void setEndDateAndTime(Date endDateAndTime) {
		this.endDateAndTime = endDateAndTime;
	}
	
	public void setEndDateAndTime(String endDateAndTime) throws ParseException {

			this.endDateAndTime = DATE_TIME_FORMAT.parse(endDateAndTime);
		
	}
	
	public String toString() {
		SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yy h:mm a");
		return this.getName() + " start: " + df1.format(this.getStartDateAndTime()) 
		+ " end: " + df1.format(this.getEndDateAndTime());
	}
	
}	