package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.junit.Test;

import NexTask.Logic;
import NexTask.Task;

/*
 * Unit test file for marking task as done functions
 */

//@@author A0124710W
public class CompletedTest {

	Logic logic = new Logic(true);
	String res1 = logic.executeUserCommand("add meeting with clients");
	String res2 = logic.executeUserCommand("add big event");
	String res3 = logic.executeUserCommand(
			"add meeting with project mates start \"5 october 2015 5pm\" end \"5 october 2015 6pm\"");
	String res4 = logic.executeUserCommand("add Visit grandparents on \"7 december 2015 3:00 pm\"");

	String expectedAdd = "Task has been added!";
	String expectedDone = "Task has been marked as completed!";
	String taskDoesNotExist = "Sorry, the number you have entered is greater than the number of total tasks";
	String invalidTaskNumber = "Please specify task number as an integer.";
	String invalidCommand = "Please enter a valid command.";

	String doneRes1 = logic.executeUserCommand("complete 2");
	String doneRes2 = logic.executeUserCommand("complete 4");
	String doneRes3 = logic.executeUserCommand("complete 0");
	String doneRes4 = logic.executeUserCommand("complete #$%1");
	String doneRes5 = logic.executeUserCommand("complete1");
	String doneRes6 = logic.executeUserCommand("completed 1");

	// Tests to see whether program allows users to mark a specified task as
	// done.
	@Test
	public void completedTest() {
		ArrayList<Task> expectedArr = new ArrayList<Task>();
		ArrayList<Task> expectedCompletedArr = new ArrayList<Task>();

		Task expectedTask1 = new Task("meeting with clients");
		expectedTask1.setTaskType("todo");
		expectedArr.add(expectedTask1);

		Task expectedTask2 = new Task("big event");
		expectedTask2.setTaskType("todo");
		expectedArr.add(expectedTask2);

		Task expectedTask3 = new Task("meeting with project mates");
		expectedTask3.setStart(new DateTime(2015, 10, 5, 17, 0, 0));
		expectedTask3.setEnd(new DateTime(2015, 10, 5, 18, 0, 0));
		expectedTask3.setTaskType("event");
		expectedArr.add(expectedTask3);

		Task expectedTask4 = new Task("Visit grandparents");
		expectedTask4.setCompleteBy(new DateTime(2015, 12, 7, 15, 0, 0));
		expectedTask4.setTaskType("deadline");
		expectedArr.add(expectedTask4);

		expectedArr.remove(1);
		expectedCompletedArr.add(expectedTask2);

		/*
		 * To test whether task is added into array assertEquals(expectedAdd,
		 * res1); assertEquals(expectedAdd, res2); assertEquals(expectedAdd,
		 * res3); assertEquals(expectedAdd, res4); assertEquals(expectedAdd,
		 * res5); assertEquals(expectedAdd, res6);
		 */

		// To check whether the tasks are in the correct array
		assertEquals(expectedArr.get(0).getName(), logic.getTaskList().get(0).getName());
		assertEquals(expectedArr.get(0).getTaskType(), logic.getTaskList().get(0).getTaskType());
		assertEquals(expectedArr.get(1).getName(), logic.getTaskList().get(1).getName());
		assertEquals(expectedArr.get(1).getTaskType(), logic.getTaskList().get(1).getTaskType());
		assertEquals(expectedArr.get(1).getStart(), logic.getTaskList().get(1).getStart());
		assertEquals(expectedArr.get(1).getEnd(), logic.getTaskList().get(1).getEnd());
		assertEquals(expectedArr.get(2).getName(), logic.getTaskList().get(2).getName());
		assertEquals(expectedArr.get(2).getCompleteBy(), logic.getTaskList().get(2).getCompleteBy());
		assertEquals(expectedArr.get(2).getTaskType(), logic.getTaskList().get(2).getTaskType());

		System.out.println(logic.getCompletedTaskList().get(0).toString());
		assertEquals(expectedCompletedArr.get(0).getName(), logic.getCompletedTaskList().get(0).getName());
		assertEquals(expectedCompletedArr.get(0).getTaskType(), logic.getCompletedTaskList().get(0).getTaskType());

		// positive test cases
		assertEquals(expectedDone, doneRes1);

		// negative test cases
		assertEquals(taskDoesNotExist, doneRes2);
		assertEquals(invalidTaskNumber, doneRes3);
		assertEquals(invalidTaskNumber, doneRes4);
		assertEquals(invalidCommand, doneRes5);
		assertEquals(invalidCommand, doneRes6);
	}

}
