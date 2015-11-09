package test;

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.junit.Test;

import NexTask.Logic;
import NexTask.Task;

/*
* Unit test file for undo functions
*/

//@@author A0124710W
public class UndoTest {

	Logic logic = new Logic();

	String expectedAdd = "Task has been added!";
	String expectedEdit = "Task has been edited!";
	String expectedUndo = "Previous task has been undone!";
	String expectedDelete = "Task has been deleted!";
	String expectedDone = "Task has been marked as completed!";
	String invalidCommand = "Please enter a valid command.";

	@Test
	public void undoTest() {
		String res1 = logic.executeUserCommand("add meeting with clients");
		String res2 = logic.executeUserCommand("add big side project by next year");
		String res3 = logic.executeUserCommand(
				"add meeting with project mates start \"5 october 2015 5pm\" end \"5 october 2015 6pm\"");
		String res4 = logic.executeUserCommand("add visit europe on \"7 december 2015 3:00 pm\"");

		String res5 = logic.executeUserCommand("edit 4 name visit africa");
		String res6 = logic.executeUserCommand("delete 3");
		String res7 = logic.executeUserCommand("add catchup with friends");
		String res8 = logic.executeUserCommand("complete 2");

		String undoRes1 = logic.executeUserCommand("undo");
		String undoRes2 = logic.executeUserCommand("undo");
		String undoRes3 = logic.executeUserCommand("undo");
		String undoRes4 = logic.executeUserCommand("undo");
		String undoRes5 = logic.executeUserCommand("un do");

		Task undoTask1 = new Task("meeting with clients");
		undoTask1.setTaskType("todo");

		Task undoTask2 = new Task("big side project by next year");
		undoTask2.setTaskType("todo");

		Task undoTask3 = new Task("meeting with project mates");
		undoTask3.setStart(new DateTime(2015, 10, 5, 17, 0, 0));
		undoTask3.setEnd(new DateTime(2015, 10, 5, 18, 0, 0));
		undoTask3.setTaskType("event");

		Task undoTask4 = new Task("visit europe");
		undoTask4.setCompleteBy(new DateTime(2015, 12, 7, 15, 0, 0));
		undoTask4.setTaskType("deadline");

		// Checks whether commands are being executed
		assertEquals(expectedAdd, res1);
		assertEquals(expectedAdd, res2);
		assertEquals(expectedAdd, res3);
		assertEquals(expectedAdd, res4);
		assertEquals(expectedEdit, res5);
		assertEquals(expectedDelete, res6);
		assertEquals(expectedAdd, res7);
		assertEquals(expectedDone, res8);

		// Checks whether the undo commands are being executed properly
		assertEquals(expectedUndo, undoRes1);
		assertEquals(expectedUndo, undoRes2);
		assertEquals(expectedUndo, undoRes3);
		assertEquals(expectedUndo, undoRes4);
		assertEquals(invalidCommand, undoRes5);

		// Checking whether if the array is same as the initial one
		assertEquals(undoTask1.getName(), logic.getTaskList().get(0).getName());
		assertEquals(undoTask1.getTaskType(), logic.getTaskList().get(0).getTaskType());

		assertEquals(undoTask2.getName(), logic.getTaskList().get(1).getName());
		assertEquals(undoTask2.getTaskType(), logic.getTaskList().get(1).getTaskType());

		assertEquals(undoTask3.getName(), logic.getTaskList().get(2).getName());
		assertEquals(undoTask3.getTaskType(), logic.getTaskList().get(2).getTaskType());
		assertEquals(undoTask3.getStart(), logic.getTaskList().get(2).getStart());
		assertEquals(undoTask3.getEnd(), logic.getTaskList().get(2).getEnd());

		assertEquals(undoTask4.getName(), logic.getTaskList().get(3).getName());
		assertEquals(undoTask4.getTaskType(), logic.getTaskList().get(3).getTaskType());
		assertEquals(undoTask4.getCompleteBy(), logic.getTaskList().get(3).getCompleteBy());
	}

}
