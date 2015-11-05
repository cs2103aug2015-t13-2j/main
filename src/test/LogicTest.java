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
		//Testing date formats
		String res1 = logic.executeUserCommand("add meeting start \"11/11/11 1:00 pm\" end \"11/11/11 2:00pm\"");
		String res2 = logic.executeUserCommand("add big event start \"11/11/11\"");
		
		String res3 = logic.executeUserCommand("add assn 1 due by \"5/11/15 1:00 pm\"");
		String res4 = logic.executeUserCommand("add visit nancy on \"7/11/15 3:00 pm\"");

		String res5 = logic.executeUserCommand("add continue working on assignment 1");
		String res6 = logic.executeUserCommand("add start working on assignment 1");
		String res7 = logic.executeUserCommand("add end scarf");
		
		String expected = "Task has been added!";
		
		assertEquals(expected, res1);
		assertEquals(expected, res2);
		assertEquals(expected, res3);
		assertEquals(expected, res4);
		assertEquals(expected, res5);
		assertEquals(expected, res6);
		assertEquals(expected, res7);
	}

	// Test executing add command
	@Test
	public void testAddBad() {
		Logic logic = new Logic();
		// boundary test cases
		String res1 = logic.executeUserCommand("add meeting1 start \"11/13/11 1:00 pm\" end \"11/11/11 2:00pm\"");
		String res2 = logic.executeUserCommand("add meeting2 start \"32/1/11 5:00 pm\"");
		String res3 = logic.executeUserCommand("add meeting2 start \"31/1/11 25:00 pm\"");
		// empty task name
		//String res4 = logic.executeUserCommand("add start \"11/13/11 25:00 pm\"");

		assertEquals("There is no such command available for usage.", res1);
		assertEquals("There is no such command available for usage.", res2);
		assertEquals("There is no such command available for usage.", res3);
		//assertEquals("Pleae provide a name for your task.", res4);
	}

	// Test executing edit command
	@Test
	public void testEditGood() {
		Logic logic = new Logic();
		ArrayList<String> executionResult = new ArrayList<String>();

		logic.executeUserCommand("add laundry");
		logic.executeUserCommand("add meeting1 start \"11/11/11 1:00 pm\" end \"11/11/11 2:00pm\"");
		logic.executeUserCommand("add assn1 due by \"23/10/15 2:00 pm\"");

		// 1 and 3 are boundary cases for valid task numbers
		// edit names
		String res1 = logic.executeUserCommand("edit 1 name happy");
		String res2 = logic.executeUserCommand("edit 2 name lecture");
		String res3 = logic.executeUserCommand("edit 3 name hw");

		String expected = "Task has been edited!";

		assertEquals(true, res1.equals(expected));
		assertEquals(true, res2.equals(expected));
		assertEquals(true, res3.equals(expected));
	}

	// Test executing edit command
	//@Test
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
