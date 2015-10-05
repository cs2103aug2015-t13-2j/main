package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Test;

import NexTask.*;

public class CommandParserTest {

	// TEST CASES FOR PARSING ADD COMMANDS
	@Test
	public void testParseAdd0() {
		CommandParser cp = new CommandParser();
		Command add_actual0 = cp.parse("add todo Call home.");
		Command add_expected0 = new Command();
		add_expected0.setCommandName("add");
		add_expected0.setTask(new Floating("Call home."));
		assertEquals(true, add_expected0.equals(add_actual0));
	}

	@Test
	public void testParseAdd1() {
		CommandParser cp = new CommandParser();
		Command add_actual1 = cp.parse("add todo Call home.");
		Command add_expected1 = new Command();
		add_expected1.setCommandName("add");
		add_expected1.setTask(new Floating("Call my parents."));
		assertEquals(false, add_expected1.equals(add_actual1));
	}

	@Test
	public void testParseAdd2() {
		CommandParser cp = new CommandParser();
		Command add_actual3 = cp.parse("add todo Call home.");
		Command add_expected3 = new Command();
		add_expected3.setCommandName("add");
		add_expected3.setTask(new Floating("Call."));
		assertEquals(false, add_expected3.equals(add_actual3));
	}
	
	@Test
	public void testParseAdd3() {
		CommandParser cp = new CommandParser();
		Command actual = cp.parse("add todo");
		Command expected = new Command();
		expected.setCommandName("add");
		expected.setTask(new Floating("Invalid Task."));
		assertEquals(true, expected.equals(actual));
	}

	// TEST CASES FOR PARSING EDIT COMMANDS
	@Test
	public void testParseEdit0() {
		CommandParser cp = new CommandParser();
		Command edit_actual0 = cp.parse("edit 0 name Call manager.");
		Command edit_expected0 = new Command();
		edit_expected0.setCommandName("edit");
		edit_expected0.setEditSpecification(new EditSpecification(0, "name", "Call manager."));
		assertEquals(true, edit_expected0.equals(edit_actual0));
	}
	@Test
	public void testParseEdit1() {
		CommandParser cp = new CommandParser();
		Command actual = cp.parse("edit my name Call manager.");
		Command expected = new Command();
		expected.setCommandName("edit");
		EditSpecification edit = new EditSpecification();
		edit.setTaskNumber(-1);
		expected.setEditSpecification(edit);
		assertEquals(true, expected.equals(actual));
	}

	@Test
	public void testParseEdit2() {
		CommandParser cp = new CommandParser();
		Command actual = cp.parse("edit 0");
		Command expected = new Command();
		expected.setCommandName("edit");
		EditSpecification edit = new EditSpecification();
		edit.setTaskNumber(-2);
		expected.setEditSpecification(edit);
		assertEquals(true, expected.equals(actual));
	}
	
	@Test
	public void testParseEdit3() {
		CommandParser cp = new CommandParser();
		Command actual = cp.parse("edit 0 name");
		Command expected = new Command();
		expected.setCommandName("edit");
		EditSpecification edit = new EditSpecification();
		edit.setTaskNumber(-2);
		expected.setEditSpecification(edit);
		assertEquals(true, expected.equals(actual));
	}
	
	// TEST CASES FOR PARSING DISPLAY COMMANDS
	@Test
	public void testParseDisplay() {
		CommandParser cp = new CommandParser();
		Command display_actual0 = cp.parse("display");
		assertEquals("display", display_actual0.getCommandName());
	}

	// TEST CASES FOR PARSING DELETE COMMANDS
	@Test
	public void testParseDelete0() {
		CommandParser cp = new CommandParser();
		Command delete_actual0 = cp.parse("delete 7");
		Command delete_expected0 = new Command();
		delete_expected0.setCommandName("delete");
		delete_expected0.setTaskNumber(7);
		assertEquals(true, delete_expected0.equals(delete_actual0));
	}
	
	@Test
	public void testParseDelete1() {
		CommandParser cp = new CommandParser();
		Command delete_actual1 = cp.parse("delete elephant");
		Command delete_expected1 = new Command();
		delete_expected1.setCommandName("delete");
		delete_expected1.setTaskNumber(-1);
		assertEquals(true, delete_expected1.equals(delete_actual1));
	}
	
	@Test
	public void testParseDelete2() {
		CommandParser cp = new CommandParser();
		Command delete_actual1 = cp.parse("delete");
		Command delete_expected1 = new Command();
		delete_expected1.setCommandName("delete");
		delete_expected1.setTaskNumber(-2);
		assertEquals(true, delete_expected1.equals(delete_actual1));
	}
}
