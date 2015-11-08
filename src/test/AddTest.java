package test;

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.junit.Test;

import NexTask.Logic;
import NexTask.Task;


//@@author - A0124710W
public class AddTest {
	private static final String COMMAND_ERROR = "Unrecognized command type entered! Please input a correct command type!";
	
	Logic logic = new Logic();
	String res1 = logic.executeUserCommand("add meeting start \"11/11/11 1:00 pm\" end \"11/11/11 2:00pm\"");
	String res2 = logic.executeUserCommand("add big event start \"11 nov 2011\"");
	String res3 = logic.executeUserCommand("add assn 1 by \"5 october 2015 8pm\"");
	String res4 = logic.executeUserCommand("add Visit Nancy on \"7 december 2015 3:00 pm\"");
	String res5 = logic.executeUserCommand("ADD CoNtInUe WoRkInG oN aSsIgNmEnT 1");
	String res6 = logic.executeUserCommand("add dinner with family @ fullerton hotel");
	//String res7 = logic.executeUserCommand("add assignment 1 due \"31/1/11 23:00 pm\"");
	
	// Test executing add command
	@Test
	public void addTest() {
		//Testing date formats
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
		
		Task expectedTask4 = new Task("Visit Nancy");
		expectedTask4.setCompleteBy(new DateTime(2015, 12, 7, 15, 0, 0));
		expectedTask4.setTaskType("deadline");

		Task expectedTask5 = new Task("CoNtInUe WoRkInG oN aSsIgNmEnT 1");
		expectedTask5.setTaskType("todo");
		
		Task expectedTask6 = new Task("dinner with family @ fullerton hotel");
		expectedTask6.setTaskType("todo");
		
		Task expectedTask7 = new Task("meeting2");
		expectedTask7.setStart(new DateTime(2011, 1, 31, 0, 0, 0));
		expectedTask7.setEnd(new DateTime(2011, 11, 11, 1, 0, 0));
		expectedTask7.setTaskType("event");

		assertEquals(expected, res1);
		assertEquals(expectedTask1.getName(), logic.getTaskList().get(0).getName());
		assertEquals(expectedTask1.getTaskType(), logic.getTaskList().get(0).getTaskType());
		assertEquals(expectedTask1.getStart(), logic.getTaskList().get(0).getStart());
		assertEquals(expectedTask1.getEnd(), logic.getTaskList().get(0).getEnd());
		
		assertEquals(expected, res2);
		assertEquals(expectedTask2.getName(), logic.getTaskList().get(1).getName());
		assertEquals(expectedTask2.getTaskType(), logic.getTaskList().get(1).getTaskType());
		assertEquals(expectedTask2.getStart(), logic.getTaskList().get(1).getStart());
		assertEquals(expectedTask2.getEnd(), logic.getTaskList().get(1).getEnd());
		
		assertEquals(expected, res3);
		assertEquals(expectedTask3.getName(), logic.getTaskList().get(2).getName());
		assertEquals(expectedTask3.getTaskType(), logic.getTaskList().get(2).getTaskType());
		assertEquals(expectedTask3.getCompleteBy(), logic.getTaskList().get(2).getCompleteBy());
		
		assertEquals(expected, res4);
		assertEquals(expectedTask4.getName(), logic.getTaskList().get(3).getName());
		assertEquals(expectedTask4.getTaskType(), logic.getTaskList().get(3).getTaskType());
		assertEquals(expectedTask4.getCompleteBy(), logic.getTaskList().get(3).getCompleteBy());
		
		assertEquals(expected, res5);
		assertEquals(expectedTask5.getName(), logic.getTaskList().get(4).getName());
		assertEquals(expectedTask5.getTaskType(), logic.getTaskList().get(4).getTaskType());
		
		assertEquals(expected, res6);
		assertEquals(expectedTask6.getName(), logic.getTaskList().get(5).getName());
		assertEquals(expectedTask6.getTaskType(), logic.getTaskList().get(5).getTaskType());
		
		/*assertEquals(expected, res7);
		assertEquals(expectedTask7.getName(), logic.getTaskList().get(6).getName());
		assertEquals(expectedTask7.getTaskType(), logic.getTaskList().get(6).getTaskType());
	    */
	}

}
