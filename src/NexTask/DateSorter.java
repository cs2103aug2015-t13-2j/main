package NexTask;

import java.util.Comparator;
import java.util.Date;

public class DateSorter implements Comparator<Task>{
	
	public int compare(Task t1, Task t2) {
		if ((t1 instanceof Floating) && (t2 instanceof Floating)){
			return 0;
		} else if(t1 instanceof Floating) {
			return 1;
		} else if(t2 instanceof Floating) {
			return -1;
		} else {
			Date d1 = getTaskDate(t1);
			Date d2 = getTaskDate(t2);
			return d1.compareTo(d2);
		}
	}

	private Date getTaskDate(Task t) {
		assert(!(t instanceof Floating));
		if(t instanceof Event) {
			Event e = (Event)t;
			return e.getStartDateAndTime();
		} else {
			Deadline d = (Deadline)t;
			return d.getDueBy();
		} 
	}
		
}
