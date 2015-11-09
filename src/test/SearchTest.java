package test;

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.junit.Test;

import NexTask.Logic;
import NexTask.Task;

public class SearchTest {
	private static final String PRINT_SEARCH_FORMAT = "Search results:\n" + "----------------\n" + "\n";
	private static final String COMPLETED_FORMAT = "========== Completed ===========\n";
	private static final String INCOMPLETE_FORMAT = "========== Incomplete ===========\n";
	private static final String UNABLE_TO_SEARCH = "no search results";
	
	private static final String PATTERN_LITERAL = "\"[\\w\\s]+\"";
	
	Logic logic = new Logic(true);

	String res1 = logic.executeUserCommand("add meeting start \"11/11/11 1:00 pm\" end \"11/11/11 2:00pm\"");
	String res2 = logic.executeUserCommand("add big event start \"11 nov 2011\"");
	String res3 = logic.executeUserCommand("add assn 1 by \"5 october 2015 8pm\"");
	String res4 = logic.executeUserCommand("add Meeting Nancy on \"7 december 2015 3:00 pm\"");
	String res5 = logic.executeUserCommand("ADD CoNtInUe WoRkInG oN aSsIgNmEnT 1");
	String res6 = logic.executeUserCommand("add dinner with family @ fullerton hotel");
	
	@Test
	public void searchGood() {
		String expected = "Task has been added!";
		Task expectedTask1 = new Task("meeting");
		expectedTask1.setStart(new DateTime(2011, 11, 11, 13, 0, 0));
		expectedTask1.setEnd(new DateTime(2011, 11, 11, 14, 0, 0));
		expectedTask1.setTaskType("event");
		
		Task expectedTask2 = new Task("big event");
		expectedTask2.setStart(new DateTime(2011, 11, 11, 0, 0, 0));
		expectedTask2.setEnd(new DateTime(2011, 11, 11, 1, 0, 0));
		expectedTask2.setTaskType("event");
		
		Task expectedTask3 = new Task("assn 1");
		expectedTask3.setCompleteBy(new DateTime(2015, 10, 5, 20, 0, 0));
		expectedTask3.setTaskType("deadline");
		
		Task expectedTask4 = new Task("Meeting Nancy");
		expectedTask4.setCompleteBy(new DateTime(2015, 12, 7, 15, 0, 0));
		expectedTask4.setTaskType("deadline");

		Task expectedTask5 = new Task("CoNtInUe WoRkInG oN aSsIgNmEnT 1");
		expectedTask5.setTaskType("todo");
		
		Task expectedTask6 = new Task("dinner with family @ fullerton hotel");
		expectedTask6.setTaskType("todo");
		
		// Good cases
		String res1 = logic.executeUserCommand("search meeting    ");
		String res2 = logic.executeUserCommand("search meeting visit nancy"); 
		String res3 = logic.executeUserCommand("search meet    ");
		String res4 = logic.executeUserCommand("search continue");
	
		boolean r1 = res1.contains(expectedTask1.toString()) && res1.contains(expectedTask4.toString());
		boolean r2 = res2.contains(expectedTask1.toString()) && res2.contains(expectedTask4.toString());
		boolean r3 = res3.contains(expectedTask1.toString()) && res2.contains(expectedTask4.toString());
		boolean r4 = res4.contains(expectedTask5.toString());

		assertEquals(true, r1);
		assertEquals(true, r2);
		assertEquals(true, r3);
		assertEquals(true, r4);
		
		// Bad cases 
		String res5 = logic.executeUserCommand("search meetings"); 
		
		assertEquals(UNABLE_TO_SEARCH, res5);
		
	}

}
