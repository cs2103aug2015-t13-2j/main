package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import NexTask.*;

public class LogicTest {
	private static final String COMMAND_ERROR = "Unrecognized command type entered! Please input a correct command type!";

	// Test executing add command
	@Test
	public void testAddGood() {
		Logic logic = new Logic();
		String res1 = logic.executeUserCommand("add todo laundry");
		String res2 = logic.executeUserCommand("add event start 22/10/15 2:00 pm end 10/10/15 3:00 pm meeting1");
		String res3 = logic.executeUserCommand("add deadline 23/10/15 2:00 pm assn1");

		String expected = "Task has been added!";

		assertEquals(true, res1.equals(expected));
		assertEquals(true, res2.equals(expected));
		assertEquals(true, res3.equals(expected));
	}

	// Test executing add command
	@Test
	public void testAddBad() {
		Logic logic = new Logic();
		// invalid num args
		ArrayList<String> executionResult = new ArrayList<String>();
		executionResult.add(logic.executeUserCommand("add todo "));
		executionResult.add(logic.executeUserCommand("add event start 22/10/152:00 pm end 10/10/15 3:00 pm meeting1"));
		executionResult.add(logic.executeUserCommand("add deadline 23/10/15 2:00pm assn1"));

		// invalid task type
		executionResult.add(logic.executeUserCommand("add to-do laundry "));

		// invalid date format
		executionResult.add(logic.executeUserCommand("add event start 22/10/15 2 pm end 10/10/15 3:00 pm meeting1"));
		executionResult.add(logic.executeUserCommand("add deadline 23/10/15 2 pm assn1"));

		// invalid event format
		executionResult.add(logic.executeUserCommand("add event begin 22/10/15 2:00 pm end 10/10/15 3:00 pm meeting1"));
		executionResult.add(logic.executeUserCommand("add event start 22/10/15 1:00 pm no 10/10/15 3:00 pm meeting1"));

		for (String s : executionResult) {
			assertEquals(true, s.equals(COMMAND_ERROR));
		}
	}

	// Test executing edit command
	@Test
	public void testEditGood() {
		Logic logic = new Logic();
		ArrayList<String> executionResult = new ArrayList<String>();

		logic.executeUserCommand("add todo laundry");
		logic.executeUserCommand("add event start 22/10/15 2:00 pm end 10/10/15 3:00 pm meeting1");
		logic.executeUserCommand("add deadline 23/10/15 2:00 pm assn1");

		// 1 and 3 are boundary cases for valid task numbers
		// edit names
		executionResult.add(logic.executeUserCommand("edit 1 name exercise"));
		executionResult.add(logic.executeUserCommand("edit 2 name lecture"));
		executionResult.add(logic.executeUserCommand("edit 3 name hw"));

		// edit dates
		executionResult.add(logic.executeUserCommand("edit 2 start 11/11/11 3:00 pm"));
		executionResult.add(logic.executeUserCommand("edit 2 end 11/11/11 4:00 pm"));
		executionResult.add(logic.executeUserCommand("edit 3 due 11/11/11 5:00 pm"));

		String expected = "Task has been editted!";

		for (String s : executionResult) {
			assertEquals(true, s.equals(expected));
		}

		Floating f = (Floating) logic.getTaskList().getTaskObject(0);
		Event e = (Event) logic.getTaskList().getTaskObject(1);
		Deadline d = (Deadline) logic.getTaskList().getTaskObject(2);

		assertEquals(true, f.toString().equals("exercise"));
		assertEquals(true, e.toString().equals("lecture start: 11/11/11 3:00 PM end: 11/11/11 4:00 PM"));
		assertEquals(true, d.toString().equals("hw due by: 11/11/11 5:00 PM"));

	}

	// Test executing edit command
	@Test
	public void testEditBad() {
		Logic logic = new Logic();
		ArrayList<String> executionResult1 = new ArrayList<String>();
		ArrayList<String> executionResult2 = new ArrayList<String>();
		ArrayList<String> executionResult3 = new ArrayList<String>();

		logic.executeUserCommand("add todo laundry");
		logic.executeUserCommand("add event start 22/10/15 2:00 pm end 10/10/15 3:00 pm meeting1");
		logic.executeUserCommand("add deadline 23/10/15 2:00 pm assn1");

		// invalid date format
		String res1 = logic.executeUserCommand("edit 2 start 11/11/11 3pm");
		assertEquals(true, res1.equals("Invalid date format."));
		// invalid num args
		executionResult1.add(logic.executeUserCommand("edit 1"));
		// invalid task num
		executionResult1.add(logic.executeUserCommand("edit one name exercise"));
		executionResult1.add(logic.executeUserCommand("edit -1 name exercise"));


		for (String s : executionResult1) {
			assertEquals(true, s.equals(COMMAND_ERROR));
		}

		// boundary case2 for invalid task number partition
		executionResult2.add(logic.executeUserCommand("edit 4 name exercise"));
		executionResult2.add(logic.executeUserCommand("edit 0 name exercise"));
		String expected1 = "There is no task available to edit..\n" + "Please enter an integer as the task number.";
		for (String s : executionResult2) {
			assertEquals(true, s.equals(expected1));
		}

		// invalid field
		executionResult3.add(logic.executeUserCommand("edit 2 hello 11/11/11 3:00 pm"));
		executionResult3.add(logic.executeUserCommand("edit 2 goodbye 11/11/11 4:00 pm"));
		executionResult3.add(logic.executeUserCommand("edit 3 nice 11/11/11 5:00 pm"));
		String expected2 = "There is no task available to edit..\n" + "Invalid field to edit.";
		for (String s : executionResult3) {
			assertEquals(true, s.equals(expected2));
		}

	}

}
