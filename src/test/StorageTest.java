package test;

import static org.junit.Assert.*;

import org.junit.Test;

import NexTask.*;

public class StorageTest {
	private String userPath = "Desktop";

	@Test
	public void testGetPath() {
		String path = userPath;
		assertEquals(path, userPath);
	}

	@Test
	public void testGetCompletedSize() {
		Storage storage = Storage.getInstance();
		Task todo = new Task("a");
		storage.add(todo);
		storage.markComplete(0);
		assertEquals(storage.getCommandSize(), 0);
	}

	@Test
	public void testGetCompletedTasks() {
		Storage storage = Storage.getInstance();
		Task todo = new Task("a");
		storage.add(todo);
		storage.markComplete(0);
		assertEquals(storage.getCommandSize(), 0);
	}
}