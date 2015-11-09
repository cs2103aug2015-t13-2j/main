package test;

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.junit.Test;

import NexTask.Logic;
import NexTask.Task;

/*
 * Unit test file for editting functions
 */

//@@author A0124710W
public class EditTest {

	Logic logic = new Logic();
	String addRes1 = logic.executeUserCommand("add meeting with boss start \"11 nov 2011\"");
	String addRes2 = logic.executeUserCommand("add Laundry");
	String addRes3 = logic.executeUserCommand("add company retreat start \"11 nov 2015 5pm\"");
	String addRes4 = logic.executeUserCommand("add go to the gym start \"10 nov 2015 5pm\" end \"10 nov 2015 7pm\"");
	String addRes5 = logic.executeUserCommand(
			"add REMEMBER TO WATER PLANTS start \"5 october 2015 7am\" end \"5 october 2015 7.10am\"");
	String addRes6 = logic.executeUserCommand("add Visit Nancy by \"7 december 2015 3:00 pm\"");
	String addRes7 = logic.executeUserCommand("add Babysitting by \"28 november 2015 8pm\"");
	String addRes8 = logic.executeUserCommand("add do laundry by \"8 november 2015 10pm\"");
	String addRes9 = logic.executeUserCommand("add big event");
	String addRes10 = logic.executeUserCommand("add assignment 1");

	String editRes1 = logic.executeUserCommand("edit 1 name Company Lunch with Boss next month");
	String editRes2 = logic.executeUserCommand("edit 2 name LaUnDrY");
	String editRes3 = logic.executeUserCommand("edit 3 clear start");
	String editRes4 = logic.executeUserCommand("edit 4 clear end");
	String editRes5 = logic.executeUserCommand("edit 5 clear times");
	String editRes6 = logic.executeUserCommand("edit 6 end \"7 december 2015 5:00 pm\"");
	String editRes7 = logic.executeUserCommand("edit 7 start \"28 november 2015 8am\"");
	String editRes8 = logic.executeUserCommand("edit 8 clear times");
	String editRes9 = logic.executeUserCommand("edit 9 start \"10/10/15 10:00 pm\"");
	String editRes10 = logic.executeUserCommand("edit 10 by \"11/10/15 2:30 pm\"");

	String expectedEdit = "Task has been edited!";
	String expectedAdd = "Task has been added!";
	String invalidCommand = "Please enter a valid command.";

	// Test for editing names
	@Test
	public void testEditName() {
		Task expectedTask1 = new Task("Company Lunch with Boss next month");
		expectedTask1.setStart(new DateTime(2011, 11, 11, 00, 00, 0));
		expectedTask1.setEnd(new DateTime(2011, 11, 11, 01, 00, 0));
		expectedTask1.setTaskType("event");

		Task expectedTask2 = new Task("LaUnDrY");
		expectedTask2.setTaskType("todo");

		/*
		 * To test whether task is added into array assertEquals(expectedAdd,
		 * addRes1); assertEquals(expectedAdd, addRes2);
		 */
		assertEquals(expectedEdit, editRes1);
		assertEquals(expectedTask1.getName(), logic.getTaskList().get(0).getName());
		assertEquals(expectedTask1.getTaskType(), logic.getTaskList().get(0).getTaskType());

		assertEquals(expectedEdit, editRes2);
		assertEquals(expectedTask2.getName(), logic.getTaskList().get(1).getName());
		assertEquals(expectedTask2.getTaskType(), logic.getTaskList().get(1).getTaskType());
		assertEquals(expectedTask2.getStart(), logic.getTaskList().get(1).getStart());
		assertEquals(expectedTask2.getEnd(), logic.getTaskList().get(1).getEnd());

	}

	// Test for editing from event tasks to other types of tasks
	@Test
	public void testEventEditDate() {
		Task expectedTask3 = new Task("company retreat");
		expectedTask3.setCompleteBy(new DateTime(2015, 11, 11, 18, 00, 0));
		expectedTask3.setTaskType("deadline");

		Task expectedTask4 = new Task("go to the gym");
		expectedTask4.setTaskType("deadline");
		expectedTask4.setCompleteBy(new DateTime(2015, 11, 10, 17, 0, 0));

		Task expectedTask5 = new Task("REMEMBER TO WATER PLANTS");
		expectedTask5.setTaskType("todo");
		expectedTask5.setStart(null);
		expectedTask5.setEnd(null);

		/*
		 * To test whether task is added into array assertEquals(expectedAdd,
		 * addRes3); assertEquals(expectedAdd, addRes4);
		 * assertEquals(expectedAdd, addRes5);
		 */

		assertEquals(expectedEdit, editRes3);
		assertEquals(expectedTask3.getName(), logic.getTaskList().get(2).getName());
		assertEquals(expectedTask3.getTaskType(), logic.getTaskList().get(2).getTaskType());
		assertEquals(expectedTask3.getCompleteBy(), logic.getTaskList().get(2).getCompleteBy());

		assertEquals(expectedEdit, editRes4);
		assertEquals(expectedTask4.getName(), logic.getTaskList().get(3).getName());
		assertEquals(expectedTask4.getTaskType(), logic.getTaskList().get(3).getTaskType());
		assertEquals(expectedTask4.getCompleteBy(), logic.getTaskList().get(3).getCompleteBy());

		assertEquals(expectedEdit, editRes5);
		assertEquals(expectedTask5.getName(), logic.getTaskList().get(4).getName());
		assertEquals(expectedTask5.getTaskType(), logic.getTaskList().get(4).getTaskType());
		assertEquals(expectedTask5.getStart(), logic.getTaskList().get(4).getStart());
		assertEquals(expectedTask5.getEnd(), logic.getTaskList().get(4).getEnd());
	}

	// Test for editing from deadline tasks to other types of tasks
	@Test
	public void testDeadlineEditDate() {
		Task expectedTask6 = new Task("Visit Nancy");
		expectedTask6.setStart(new DateTime(2015, 12, 07, 15, 00, 0));
		expectedTask6.setEnd(new DateTime(2015, 12, 07, 17, 00, 0));
		expectedTask6.setTaskType("event");

		Task expectedTask7 = new Task("Babysitting");
		expectedTask7.setStart(new DateTime(2015, 11, 28, 8, 00, 0));
		expectedTask7.setEnd(new DateTime(2015, 11, 28, 20, 00, 0));
		expectedTask7.setTaskType("event");

		Task expectedTask8 = new Task("do laundry");
		expectedTask8.setTaskType("todo");

		/*
		 * To test whether task is added into array assertEquals(expectedAdd,
		 * addRes6); assertEquals(expectedAdd, addRes7);
		 * assertEquals(expectedAdd, addRes8);
		 */

		assertEquals(expectedEdit, editRes6);
		assertEquals(expectedTask6.getName(), logic.getTaskList().get(5).getName());
		assertEquals(expectedTask6.getTaskType(), logic.getTaskList().get(5).getTaskType());
		assertEquals(expectedTask6.getStart(), logic.getTaskList().get(5).getStart());
		assertEquals(expectedTask6.getEnd(), logic.getTaskList().get(5).getEnd());

		assertEquals(expectedEdit, editRes7);
		assertEquals(expectedTask7.getName(), logic.getTaskList().get(6).getName());
		assertEquals(expectedTask7.getTaskType(), logic.getTaskList().get(6).getTaskType());
		assertEquals(expectedTask7.getStart(), logic.getTaskList().get(6).getStart());
		assertEquals(expectedTask7.getEnd(), logic.getTaskList().get(6).getEnd());

		assertEquals(expectedEdit, editRes8);
		assertEquals(expectedTask8.getName(), logic.getTaskList().get(7).getName());
		assertEquals(expectedTask8.getTaskType(), logic.getTaskList().get(7).getTaskType());
		assertEquals(expectedTask8.getStart(), logic.getTaskList().get(7).getStart());
		assertEquals(expectedTask8.getEnd(), logic.getTaskList().get(7).getEnd());
	}

	// Test for editing from to-do tasks to other types of tasks
	@Test
	public void testTodoEditDate() {
		Task expectedTask9 = new Task("big event");
		expectedTask9.setStart(new DateTime(2015, 10, 10, 22, 00, 0));
		expectedTask9.setEnd(new DateTime(2015, 10, 10, 23, 00, 0));
		expectedTask9.setTaskType("event");

		Task expectedTask10 = new Task("assignment 1");
		expectedTask10.setCompleteBy(new DateTime(2015, 10, 11, 14, 30, 0));
		expectedTask10.setTaskType("deadline");

		/*
		 * To test whether task is added into array assertEquals(expectedAdd,
		 * addRes9); assertEquals(expectedAdd, addRes10);
		 */

		assertEquals(expectedEdit, editRes9);
		assertEquals(expectedTask9.getName(), logic.getTaskList().get(8).getName());
		assertEquals(expectedTask9.getTaskType(), logic.getTaskList().get(8).getTaskType());
		assertEquals(expectedTask9.getStart(), logic.getTaskList().get(8).getStart());
		assertEquals(expectedTask9.getEnd(), logic.getTaskList().get(8).getEnd());

		assertEquals(expectedEdit, editRes10);
		assertEquals(expectedTask10.getName(), logic.getTaskList().get(9).getName());
		assertEquals(expectedTask10.getTaskType(), logic.getTaskList().get(9).getTaskType());
		assertEquals(expectedTask10.getCompleteBy(), logic.getTaskList().get(9).getCompleteBy());
	}

	// Test for editing from to-do tasks to other types of tasks
	@Test
	public void testEditNegative() {
		String editRes11 = logic.executeUserCommand("edited 2 laundry");
		String editRes12 = logic.executeUserCommand("edit2 name help");

		assertEquals(invalidCommand, editRes11);
		assertEquals(invalidCommand, editRes12);
	}
}
