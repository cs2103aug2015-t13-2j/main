package test;

import static org.junit.Assert.*;

import org.junit.Test;

import NexTask.*;

/*
 * Unit test file for DisplayManager
 */

//@@author A0124710W unused because DisplayManager is not needed in program execution
public class DisplayManagerTest {

	@Test
	// 1st test for welcome messages [boundary case for [1-13] partition, [>2]
	// partition]
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
		assertEquals("Greetings! NexTask is ready for use." + "\n"
				+ "If you need any help, please type \"help\" to retrieve the help guide!", output);
	}

	@Test
	// 3rd test for welcome messages
	public void DisplayManagerTest3() {
		DisplayManager dispM = new DisplayManager();
		String output = dispM.messageSelector(1, 0);
		assertEquals("Command: ", output);
	}

	@Test
	// 1st test for invalid command
	public void DisplayManagerTest4() {
		DisplayManager dispM = new DisplayManager();
		String output = dispM.messageSelector(14, 0);
		assertEquals("There is no such command available for usage.", output);
	}

}
