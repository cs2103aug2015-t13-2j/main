package test;

import NexTask.*;
import static org.junit.Assert.*;
import org.junit.Test;

public class CommandParserTest {

	@Test
	public void parseDeleteTest() {
		CommandParser cp = new CommandParser();
		String str1 = "delete 1";
		String str2 = "delete ";
		String str3 = "delete one";
		
		Command res1 = cp.parse(str1);
		Command res2 = cp.parse(str2);
		Command res3 = cp.parse(str3);
		
		assertEquals("delete", res1.getCommandName());		
		assertEquals(1, res1.getTaskNumber());
		
		assertEquals("invalid", res2.getCommandName());
		assertEquals("Please provide a task number.", res2.getErrorMessage());
		
		assertEquals("invalid", res3.getCommandName());
		assertEquals("Please specify an integer for task number.", res3.getErrorMessage());
	}
	
	@Test
	public void parseEditTest() {
		CommandParser cp = new CommandParser();
		String str1 = "edit 1 clear start";
		String str2 = "edit 1 start \"10/10/10 1:00 pm\"";
		String str3 = "edit one name args";
		String str4 = "edit 1 start";
		
		Command res1 = cp.parse(str1);
		Command res2 = cp.parse(str2);
		Command res3 = cp.parse(str3);
		Command res4 = cp.parse(str4);
		
		assertEquals("edit", res1.getCommandName());
		assertEquals("start", res1.getEditSpecification().getFieldToClear());
		assertEquals(1, res1.getEditSpecification().getTaskNumber());
		
		assertEquals("edit", res2.getCommandName());
		assertEquals("start", res2.getEditSpecification().getFieldToEdit());
		assertEquals("\"10/10/10 1:00 pm\"", res2.getEditSpecification().getTheEdit());
		
		assertEquals("invalid", res3.getCommandName());
		assertEquals("Please specify an integer for task number.", res3.getErrorMessage());
		
		assertEquals("invalid", res4.getCommandName());
		assertEquals("Invalid number of arguments", res4.getErrorMessage());
	}
	
	//@Test
	public void parseAddTest() {
		CommandParser cp = new CommandParser();
		String str1 = "add meeting start \"10/10/10 1:00 pm\" end \"10/10/10 1:00 pm\"";
		String str2 = "add assn1 due by \"10/10/10 1:00 pm\"";
		String str3 = "add Saya bday on \"10/10/10\"";
		String str4 = "add laundry";
		String str5 = "add meeting start \"10/10/10 1:00 pm\"";
		String str6 = "add meeting end \"10/10/10\"";
		
		Command res1 = cp.parse(str1);
		Command res2 = cp.parse(str2);
		Command res3 = cp.parse(str3);
		Command res4 = cp.parse(str4);
		Command res5 = cp.parse(str5);
		Command res6 = cp.parse(str6);
		
		System.out.println(res1.getTask());
		System.out.println(res2.getTask());
		System.out.println(res3.getTask());
		System.out.println(res4.getTask());
		System.out.println(res5.getTask());
		System.out.println(res6.getTask());
		
	}
	
	@Test
	public void parseSortTest0() {
		CommandParser cp = new CommandParser();
		String str1 = "sort name";
		String str2 = "sort ";
		
		Command res1 = cp.parse(str1);
		Command res2 = cp.parse(str2);
		
		assertEquals("sort", res1.getCommandName());
		assertEquals("name", res1.getSortField());
		
		assertEquals("invalid", res2.getCommandName());
		assertEquals("Please specify field you wish to sort by.", res2.getErrorMessage());
	}
	
	@Test
	public void parseDisplayTest() {
		CommandParser cp = new CommandParser();
		String str1 = "display";
		
		Command res1 = cp.parse(str1);
		System.out.println(res1.getErrorMessage());
		assertEquals("display", res1.getCommandName());
		
	}
	

}
