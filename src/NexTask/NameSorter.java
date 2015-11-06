package NexTask;

//@@author A0145695R

import java.util.Comparator;

public class NameSorter implements Comparator<Task>{
	public int compare(Task t1, Task t2) {
		return t1.getName().compareTo(t2.getName());
	}
}
