package NexTask;

import java.util.Date;

public class Event extends Task {
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

	public Date getEndDateAndTime() {
		return endDateAndTime;
	}

	public void setEndDateAndTime(Date endDateAndTime) {
		this.endDateAndTime = endDateAndTime;
	}
}	