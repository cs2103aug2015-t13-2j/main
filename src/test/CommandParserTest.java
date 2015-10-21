package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Test;

import NexTask.*;

public class CommandParserTest {

	// ------------------Test Cases for Parsing Add Commands---------------- //

	// ADD TODO -------------------------------------------------------------

	// Good add todo test
	@Test
	public void testParseAddTodoGood0() {
		CommandParser cp = new CommandParser();
		Command actual = cp.parse("add todo Call home.");
		Command expected = new Command();
		expected.setCommandName("add");
		expected.setTask(new Floating("Call home."));
		assertEquals(true, expected.equals(actual));
	}

	// Good add todo test: Extra spaces before name should be disregarded
	@Test
	public void testParseAddTodoGood1() {
		CommandParser cp = new CommandParser();
		Command actual = cp.parse("add todo      Call home.");
		Command expected = new Command();
		expected.setCommandName("add");
		expected.setTask(new Floating("Call home."));
		assertEquals(true, expected.equals(actual));
	}

	// Good add todo test: Letter case should not matter
	@Test
	public void testParseAddTodoGood2() {
		CommandParser cp = new CommandParser();
		Command actual = cp.parse("ADD TODO      Call home.");
		Command expected = new Command();
		expected.setCommandName("add");
		expected.setTask(new Floating("Call home."));
		assertEquals(true, expected.equals(actual));
	}

	// Bad add todo test: Did not specify description/name
	@Test
	public void testParseAddTodoBad0() {
		CommandParser cp = new CommandParser();
		Command actual = cp.parse("add todo");
		Command expected = new Command();
		expected.setCommandName("invalid");
		assertEquals(true, expected.equals(actual));
	}
	// ADD DEADLINE --------------------------------------------------------

	// Good add deadline test
	@Test
	public void testParseAddDeadlineGood0() {
		CommandParser cp = new CommandParser();
		Command actual = cp.parse("add deadline 10/10/10 1:00 pm assignment");
		Command expected = new Command();
		expected.setCommandName("add");
		expected.setTask(new Deadline("assignment", "10/10/10 1:00 pm"));
		assertEquals(true, expected.equals(actual));
	}

	// Good add deadline test: name longer than 1 word & PM capitalized
	@Test
	public void testParseAddDeadlineGood1() {
		CommandParser cp = new CommandParser();
		Command actual = cp.parse("add deadline 10/10/10 1:00 PM assignment for cs2103");
		Command expected = new Command();
		expected.setCommandName("add");
		expected.setTask(new Deadline("assignment for cs2103", "10/10/10 1:00 pm"));
		assertEquals(true, expected.equals(actual));
	}

	// Bad add deadline test: invalid date format
	@Test
	public void testParseAddDeadlineBad0() {
		CommandParser cp = new CommandParser();
		Command actual = cp.parse("add deadline 10/10/10 1:00pm assignment");
		Command expected = new Command();
		expected.setCommandName("invalid");
		assertEquals(true, expected.equals(actual));
	}

	// Bad add deadline test: invalid date format
	@Test
	public void testParseAddDeadlineBad1() {
		CommandParser cp = new CommandParser();
		Command actual = cp.parse("add deadline 10/10/10 1 pm assignment");
		Command expected = new Command();
		expected.setCommandName("invalid");
		assertEquals(true, expected.equals(actual));
	}

	// ADD EVENT --------------------------------------------------------

	// Good add event test
	@Test
	public void testParseAddEventGood0() {
		CommandParser cp = new CommandParser();
		Command actual = cp.parse("add event start 22/10/15 2:00 pm end 10/10/15 3:00 pm meeting1");
		assertEquals(true, actual.getCommandName().equals("add"));
		Event e = (Event)actual.getTask();
		assertEquals(true, ((Event)actual.getTask()).toString().equals("meeting1 start: 22/10/15 2:00 PM end: 10/10/15 3:00 PM"));
	}
	// Bad add event test: invalid event format: start keyword missing
	@Test
	public void testParseAddEventBad0() {
		CommandParser cp = new CommandParser();
		Command actual = cp.parse("add event begin 22/10/15 2:00 pm end 10/10/15 3:00 pm meeting1");
		Command expected = new Command();
		expected.setCommandName("invalid");
		assertEquals(true, expected.equals(actual));
	}

	// Bad add event test: invalid event format: end keyword missing
	@Test
	public void testParseAddEventBad1() {
		CommandParser cp = new CommandParser();
		Command actual = cp.parse("add event start 22/10/15 2:00 pm haha 10/10/15 3:00 pm meeting1");
		Command expected = new Command();
		expected.setCommandName("invalid");
		assertEquals(true, expected.equals(actual));
	}

	// Bad add event test: invalid date format
	@Test
	public void testParseAddEventBad2() {
		CommandParser cp = new CommandParser();
		Command actual = cp.parse("add event start 22/10/15 2:00 pm end 10/10/15 3 pm meeting1");
		Command expected = new Command();
		expected.setCommandName("invalid");
		assertEquals(true, expected.equals(actual));
	}

	// Bad add event test: invalid num arguments
	@Test
	public void testParseAddEventBad3() {
		CommandParser cp = new CommandParser();
		Command actual = cp.parse("add event start 22/10/15 2:00 pm end 10/10/15 3:00 pm ");
		Command expected = new Command();
		expected.setCommandName("invalid");
		assertEquals(true, expected.equals(actual));
	}

	// Bad add test: Invalid task type
	@Test
	public void testParseAddBad1() {
		CommandParser cp = new CommandParser();
		Command actual = cp.parse("add to-do laundry");
		Command expected = new Command();
		expected.setCommandName("invalid");
		assertEquals(true, expected.equals(actual));
	}

	// ------------------Test Cases for Parsing Add Commands---------------- //
	
	// Good edit test
	@Test
	public void testParseEditGood0() {
		CommandParser cp = new CommandParser();
		Command actual = cp.parse("edit 1 name Call manager.");
		Command expected = new Command();
		expected.setCommandName("edit");
		expected.setEditSpecification(new EditSpecification(1, "name", "Call manager."));
		assertEquals(true, expected.equals(actual));
	}
	
	// Bad edit test: invalid task number
	@Test
	public void testParseEditBad0() {
		CommandParser cp = new CommandParser();
		Command actual = cp.parse("edit my name Call manager.");
		Command expected = new Command();
		expected.setCommandName("invalid");
		assertEquals(true, expected.equals(actual));
	}
	
	// Bad edit test: invalid num args
	@Test
	public void testParseEditBad1() {
		CommandParser cp = new CommandParser();
		Command actual = cp.parse("edit my name");
		Command expected = new Command();
		expected.setCommandName("invalid");
		assertEquals(true, expected.equals(actual));
	}
	

	// ------------------Test Case for Parsing Display Command---------------- //
	@Test
	public void testParseDisplay() {
		CommandParser cp = new CommandParser();
		Command display_actual0 = cp.parse("display");
		assertEquals("display", display_actual0.getCommandName());
	}
	
	// ------------------Test Case for Parsing Archive Command---------------- //
	@Test
	public void testParseArchive() {
		CommandParser cp = new CommandParser();
		Command actual = cp.parse("archive");
		assertEquals("archive", actual.getCommandName());
	}
	
	// ------------------Test Case for Parsing Help Command---------------- //
	@Test
	public void testParseHelp() {
		CommandParser cp = new CommandParser();
		Command actual = cp.parse("help");
		assertEquals("help", actual.getCommandName());
	}
	
	// ------------------Test Case for Parsing Store Command---------------- //
	@Test
	public void testParseStore() {
		CommandParser cp = new CommandParser();
		Command actual = cp.parse("store");
		assertEquals("store", actual.getCommandName());
	}
	
	// ------------------Test Case for Parsing Exit Command---------------- //
	@Test
	public void testParseEcxit() {
		CommandParser cp = new CommandParser();
		Command actual = cp.parse("exit");
		assertEquals("exit", actual.getCommandName());
	}
	
	// ------------------Test Case for Parsing Undo Command---------------- //
	@Test
	public void testParseUndo() {
		CommandParser cp = new CommandParser();
		Command actual = cp.parse("undo");
		assertEquals("undo", actual.getCommandName());
	}
	
	// ------------------Test Case for Parsing Undo Command---------------- //
	@Test
	public void testParseSearch0() {
		CommandParser cp = new CommandParser();
		Command actual = cp.parse("search meeting");
		Command expected = new Command();
		expected.setCommandName("search");
		expected.setSearchSpecification("meeting");
		assertEquals(true, actual.equals(expected));
	}
	
	@Test
	public void testParseSearch1() {
		CommandParser cp = new CommandParser();
		Command actual = cp.parse("search");
		Command expected = new Command();
		expected.setCommandName("invalid");
		assertEquals(true, actual.equals(expected));
	}
	
	
	// ------------------Test Cases for Parsing Sort Commands---------------- //
	// Good parse sort test
	@Test
	public void testParseSort0() {
		CommandParser cp = new CommandParser();
		Command actual = cp.parse("sort name");
		Command expected = new Command();
		expected.setCommandName("sort");
		expected.setSortField("name");
		assertEquals(true, expected.equals(actual));
	}
	
	// Bad parse sort test
	@Test
	public void testParseSort1() {
		CommandParser cp = new CommandParser();
		Command actual = cp.parse("sort");
		Command expected = new Command();
		expected.setCommandName("invalid");
		assertEquals(true, expected.equals(actual));
	}

	// ------------------Test Cases for Parsing Display Commands---------------- //
	// Good parse delete test
	@Test
	public void testParseDelete0() {
		CommandParser cp = new CommandParser();
		Command actual = cp.parse("delete 7");
		Command expected = new Command();
		expected.setCommandName("delete");
		expected.setTaskNumber(7);
		assertEquals(true, expected.equals(actual));
	}

	// Bad parse delete test: invalid task number
	@Test
	public void testParseDelete1() {
		CommandParser cp = new CommandParser();
		Command actual = cp.parse("delete elephant");
		Command expected = new Command();
		expected.setCommandName("invalid");
		assertEquals(true, expected.equals(actual));
	}

	// Bad parse delete test: invalid number of args
	@Test
	public void testParseDelete2() {
		CommandParser cp = new CommandParser();
		Command actual = cp.parse("delete");
		Command expected = new Command();
		expected.setCommandName("invalid");
		assertEquals(true, expected.equals(actual));
	}
	
	// ------------------Test Cases for Parsing Complete Commands---------------- //
		// Good parse complete test
		@Test
		public void testParseComplete0() {
			CommandParser cp = new CommandParser();
			Command actual = cp.parse("complete 7");
			Command expected = new Command();
			expected.setCommandName("complete");
			expected.setTaskNumber(7);
			assertEquals(true, expected.equals(actual));
		}

		// Bad parse complete test: invalid task number
		@Test
		public void testParseComplete1() {
			CommandParser cp = new CommandParser();
			Command actual = cp.parse("complete elephant");
			Command expected = new Command();
			expected.setCommandName("invalid");
			assertEquals(true, expected.equals(actual));
		}

		// Bad parse complete test: invalid number of args
		@Test
		public void testParseComplete2() {
			CommandParser cp = new CommandParser();
			Command actual = cp.parse("complete");
			Command expected = new Command();
			expected.setCommandName("invalid");
			assertEquals(true, expected.equals(actual));
		}
		
		// Invalid command
		@Test
		public void testParseInvalid() {
			CommandParser cp = new CommandParser();
			Command actual = cp.parse("lalala");
			Command expected = new Command();
			expected.setCommandName("invalid");
			assertEquals(true, expected.equals(actual));
		}
}
