package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import org.joda.time.DateTime;
import org.junit.Test;

import NexTask.Logic;
import NexTask.Task;

/*
 * Unit test file for deleting functions
 */

//@@author A0124710W
public class DeleteTest {

	String noTaskToDelete = "There is no content to delete from!";
	String expectedDelete = "Task has been deleted!";
	String cantDelete = "Sorry, the number you have entered is greater than the number of total tasks";
	String incorrectTaskNumber = "Please specify task number as an integer.";
	String invalidCommand = "Please enter a valid command.";

	Logic logic = new Logic();
	String addRes1 = logic.executeUserCommand("add Company Lunch with Boss next month start \"11 nov 2011\"");
	String addRes2 = logic.executeUserCommand("add Laundry");
	String addRes3 = logic.executeUserCommand("add Company retreat start \"11 nov 2015 5pm\"");
	String addRes4 = logic.executeUserCommand("add go to the gym start \"10 nov 2015 5pm\" end \"10 nov 2015 7pm\"");

	String delRes1 = logic.executeUserCommand("delete 4");
	String delRes2 = logic.executeUserCommand("delete 01");
	String delRes3 = logic.executeUserCommand("delete 6");
	String delRes4 = logic.executeUserCommand("delete 0");
	String delRes5 = logic.executeUserCommand("delete $%@#");
	String delRes6 = logic.executeUserCommand("delete two");
	String delRes7 = logic.executeUserCommand("deleted 1");

	String expectedAdd = "Task has been added!";

	// Tests which allows users to delete a specified task
	@Test
	public void testDeletePositive() {
		ArrayList<Task> expectedArr = new ArrayList<Task>();
		Task expectedTask1 = new Task("Company Lunch with Boss next month");
		expectedTask1.setStart(new DateTime(2011, 11, 11, 00, 00, 0));
		expectedTask1.setEnd(new DateTime(2011, 11, 11, 01, 00, 0));
		expectedTask1.setTaskType("event");
		expectedArr.add(expectedTask1);

		Task expectedTask2 = new Task("Laundry");
		expectedTask2.setTaskType("todo");
		expectedArr.add(expectedTask2);

		Task expectedTask3 = new Task("Company retreat");
		expectedTask3.setStart(new DateTime(2015, 11, 11, 17, 00, 0));
		expectedTask3.setEnd(new DateTime(2015, 11, 11, 18, 00, 0));
		expectedTask3.setTaskType("event");
		expectedArr.add(expectedTask3);

		Task expectedTask4 = new Task("go to the gym");
		expectedTask4.setStart(new DateTime(2015, 11, 10, 17, 00, 0));
		expectedTask4.setEnd(new DateTime(2015, 11, 10, 19, 00, 0));
		expectedTask4.setTaskType("event");
		expectedArr.add(expectedTask4);

		expectedArr.remove(3);
		expectedArr.remove(0);

		/*
		 * To test whether task is added into array assertEquals(expectedAdd,
		 * addRes1); assertEquals(expectedAdd, addRes2);
		 * assertEquals(expectedAdd, addRes3); assertEquals(expectedAdd,
		 * addRes4);
		 */

		// Only tasks 2 and 3 are available in the array, therefore we shall
		// check if they are in the right index.
		assertEquals(expectedDelete, delRes1);
		assertEquals(expectedTask2.getName(), logic.getTaskList().get(0).getName());
		assertEquals(expectedTask2.getTaskType(), logic.getTaskList().get(0).getTaskType());

		assertEquals(expectedDelete, delRes2);
		assertEquals(expectedTask3.getName(), logic.getTaskList().get(1).getName());
		assertEquals(expectedTask3.getTaskType(), logic.getTaskList().get(1).getTaskType());
		assertEquals(expectedTask3.getStart(), logic.getTaskList().get(1).getStart());
		assertEquals(expectedTask3.getEnd(), logic.getTaskList().get(1).getEnd());

	}

	// Tests which do not allow users to delete a specified task due to invalid
	// input
	@Test
	public void testDeleteNegative() {
		// There are only 2 tasks available so these test cases are to test
		// either the integer is invalid or the task number input is
		// bigger than the size of the array.

		// input bigger than size of array
		assertEquals(cantDelete, delRes3);

		// input == 0
		assertEquals(noTaskToDelete, delRes4);

		// input is not a valid integer
		assertEquals(incorrectTaskNumber, delRes5);
		assertEquals(incorrectTaskNumber, delRes6);

		// Invalid command
		assertEquals(invalidCommand, delRes7);
	}
}
