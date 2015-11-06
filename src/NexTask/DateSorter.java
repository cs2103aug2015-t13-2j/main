package NexTask;

//@@author A0145695R

import java.util.Comparator;

import org.joda.time.DateTime;

public class DateSorter implements Comparator<Task>{
	
	public int compare(Task t1, Task t2) {
		if ((t1.getTaskType().equals("todo")) && (t2.getTaskType().equals("todo"))){
			return 0;
		} else if(t1.getTaskType().equals("todo")) {
			return 1;
		} else if(t2.getTaskType().equals("todo")) {
			return -1;
		} else {
			DateTime d1 = getTaskDate(t1);
			DateTime d2 = getTaskDate(t2);
			return d1.compareTo(d2);
		}
	}

	private DateTime getTaskDate(Task t) {
		assert(!(t.getTaskType().equals("todo")));
		if(t.getTaskType().equals("event")) {
			return t.getStart();
		} else {
			return t.getCompleteBy();
		} 
	}
		
}
