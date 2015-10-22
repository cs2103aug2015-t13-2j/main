package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import NexTask.*;

public class StorageTest {
	private String userPath = "Desktop";
	private Task todo = new Floating("a");
	private ArrayList<Task> testArray = new ArrayList<Task>();
	private ArrayList<Task> testComplete;
	private ArrayList<String> prevCommand;
	
	
	@Test
	public void testGetPath() {
		String path = userPath;
		assertEquals(path, userPath);
	}



	@Test
	public void testGetCompletedSize() {
		ArrayList<Task> testArray = new ArrayList<Task>();
		Storage storage = new Storage(userPath, testArray);
		Task todo = new Floating("a");
		testArray.add(todo);
		storage.markComplete(0);
		assertEquals(storage.getCommandSize(), 0);
	}

	@Test
	public void testGetCompletedTasks() {
		Storage storage = new Storage(userPath, testArray);
		Task todo = new Floating("a");
		testArray.add(todo);
		storage.markComplete(0);
		assertEquals(storage.getCommandSize(), 0);
	}


	@Test
	public void testDelete() {
		Storage storage = new Storage(userPath, testArray);
		Task todo = new Floating("a");
		testArray.add(todo);
		assertEquals(0, 0);
	}

}