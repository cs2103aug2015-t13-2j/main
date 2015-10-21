package test;

import static org.junit.Assert.*;

import org.junit.Test;

import NexTask.*;

public class DisplayManagerTest {

	@Test
	// 1st test for welcome messages
	public void DisplayManagerTest1() {
		DisplayManager dispM = new DisplayManager();
		String output = dispM.messageSelector(1, 500);
		assertEquals("Command: ", output);
	}
	
	@Test
	// 2nd test for welcome messages
	public void DisplayManagerTest2() {
		DisplayManager dispM = new DisplayManager();
		String output = dispM.messageSelector(1, 1);
		assertEquals("Greetings! NexTask is ready for use." + "\n" + "If you need any help, please type \"help\" to retrieve the help guide!", output);
	}
	
	@Test
	// 2nd test for welcome messages
	public void DisplayManagerTest3() {
		DisplayManager dispM = new DisplayManager();
		String output = dispM.messageSelector(1, 0);
		assertEquals("Command: ", output);
	}
	
}
