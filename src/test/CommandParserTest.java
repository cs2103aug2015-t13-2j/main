package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Test;

import NexTask.*;

public class CommandParserTest {

//	@Test
	public void testParseUserInput() {
		fail("Not yet implemented");

	}

	@Test
	public void testSplitString() {
		CommandParser cp = new CommandParser();
		ArrayList<String> actualResult = cp.splitString("add to-do exercise");
		String[] expectedResult = {"add", "to-do", "exercise"};
	
		
		for(int i = 0; i < actualResult.size(); i++) {
			String actual = actualResult.get(i);
			String expected = expectedResult[i];
			assertEquals(expected, actual);
		}
	}
	
	@Test
	public void testConcatenateName() {
		CommandParser cp = new CommandParser();
		ArrayList<String> test = new ArrayList<String>();
		test.add("Hello"); test.add("wonderful"); test.add("people");
		test.add("of"); test.add("the"); test.add("world.");
		
		String actualResult = cp.concatenateName(test).trim();
		String expectedResult = "Hello wonderful people of the world.";
		assertEquals(expectedResult, actualResult);
		
	}
	
	@Test
	public void testInitAddCommand() {
		CommandParser cp = new CommandParser();
		ArrayList<String> test = new ArrayList<String>();
		test.add("todo"); test.add("Call"); test.add("home."); 
		Command result = cp.initAddCommand(test);
		Floating temp = (Floating)result.getTask();
		assertEquals("add", result.getCommandName());
		assertEquals("Call home.", temp.getName());
	}
	
	@Test
	public void testParse() {
		CommandParser cp = new CommandParser();
		// Good Tests
		Command add_actual0 = cp.parse("add todo Call home.");
		Command add_expected0 = new Command();
		add_expected0.setCommandName("add");
		add_expected0.setTask(new Floating("Call home."));
		assertEquals(true, add_expected0.equals(add_actual0));
		
		Command edit_actual0 = cp.parse("edit 0 name Call manager.");
		Command edit_expected0 = new Command();
		edit_expected0.setCommandName("edit");
		edit_expected0.setEditSpecification(new EditSpecification(0, "name", "Call manager."));
		assertEquals(true, edit_expected0.equals(edit_actual0));
		
		Command display_actual0 = cp.parse("display");
		assertEquals("display", display_actual0.getCommandName());
		
		Command delete_actual0 = cp.parse("delete 7");
		Command delete_expected0 = new Command();
		delete_expected0.setCommandName("delete");
		delete_expected0.setTaskNumber(7);
		assertEquals(true, delete_expected0.equals(delete_actual0));
		
		// Bad Tests
		Command add_actual1 = cp.parse("add todo Call home.");
		Command add_expected1 = new Command();
		add_expected1.setCommandName("add");
		add_expected1.setTask(new Floating("Call my parents."));
		assertEquals(false, add_expected1.equals(add_actual1));
		
		Command add_actual3 = cp.parse("add todo Call home.");
		Command add_expected3 = new Command();
		add_expected3.setCommandName("add");
		add_expected3.setTask(new Floating("Call."));
		assertEquals(false, add_expected3.equals(add_actual3));
		
		Command add_actual2 = cp.parse("add todo Call home.");
		Command add_expected2 = new Command();
		add_expected2.setCommandName("edit");
		add_expected2.setTask(new Floating("Call home."));
		assertEquals(false, add_expected2.equals(add_actual2));
		
		Command delete_actual1 = cp.parse("delete 7");
		Command delete_expected1 = new Command();
		delete_expected1.setCommandName("delete");
		delete_expected1.setTaskNumber(6);
		assertEquals(false, delete_expected1.equals(delete_actual1));
		
		
	}

}
