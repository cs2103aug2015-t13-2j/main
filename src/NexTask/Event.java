package NexTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Event extends Task {
	private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd/MM/yy h:mm a");
	
	private Date startDateAndTime;
	private Date endDateAndTime;
	
	public Event(String name) {
		super(name);
	}
	
	public Event(Date start,  Date end, String name) {
		super(name);
		this.startDateAndTime = start;
		this.endDateAndTime = end;
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
	
	public void setStartDateAndTime(String startDateAndTime) {
		try {
			this.startDateAndTime = DATE_TIME_FORMAT.parse(startDateAndTime);
		} catch (ParseException e) {
			System.out.println("error parsing start date");
		}
	}

	public Date getEndDateAndTime() {
		return endDateAndTime;
	}

	public void setEndDateAndTime(Date endDateAndTime) {
		this.endDateAndTime = endDateAndTime;
	}
	
	public void setEndDateAndTime(String endDateAndTime) {
		try {
			this.endDateAndTime = DATE_TIME_FORMAT.parse(endDateAndTime);
		} catch (ParseException e) {
			System.out.println("error parsing end date");
		}
	}
	
	public String toString() {
		SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yy h:mm a");
		return "EVENT: " + this.getName() + " start: " + df1.format(this.getStartDateAndTime()) 
		+ " end: " + df1.format(this.getEndDateAndTime());
	}
	
}	