package test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Test;

import Command.Add;
import NexTask.Storage;
import NexTask.Task;

//@@author A0145035N
public class StorageTest {


	@Test
	public void testStoreToDefault() {
		Storage store = new Storage();
		try {
			store.storeToDefault();
		} catch (FileNotFoundException e) {
			System.out.println("Cannot initiate default file.");
		}
		assertEquals(true, new File("ForRetrievalTasks.ser").isFile());
	}

	@Test
	public void testMarkComplete() {
		Storage store = new Storage();
		store.add(new Task("test"));
		store.markComplete(0);
		assertEquals(0, store.getNumberOfTasks());
	}

	@Test
	public void testGetCompletedSize() {
		Storage store = new Storage();
		store.add(new Task("test"));
		store.markComplete(0);
		assertEquals(1, store.getCompletedSize());
	}

	@Test
	public void testGetCompletedTasks() {
		Storage store = new Storage();
		store.add(new Task("test"));
		store.markComplete(0);
		assertEquals(1, store.getCompletedTasks().size());
	}

	@Test
	public void testGetCompletedName() {
		Storage store = new Storage();
		store.add(new Task("test"));
		store.markComplete(0);
		assertEquals("test", store.getCompletedName(0));
	}

	
	@Test
	public void testAddCommand() {
		Storage store = new Storage();
		store.addCommand(new Add());
		assertEquals(1, store.getCommandSize());
	}

	@Test
	public void testGetCommandSize() {
		Storage store = new Storage();
		Add add = new Add();
		add.setCommandName("test");
		store.addCommand(add);
		assertEquals(1, store.getCommandSize());
	}

	@Test
	public void testGetPrevCommand() {
		Storage store = new Storage();
		Add add = new Add();
		add.setCommandName("test");
		store.addCommand(add);
		assertEquals("test", store.getPrevCommand().getCommandName());
	}

	@Test
	public void testAdd() {
		Storage store = new Storage();
		store.add(new Task());
		assertEquals(1, store.getNumberOfTasks());
	}

	@Test
	public void testGetTaskObject() {
		Storage store = new Storage();
		store.add(new Task("test"));
		assertEquals("test", store.getTaskObject(0).getName());
	}

	@Test
	public void testDeleteIncompleted() {
		Storage store = new Storage();
		store.add(new Task("test"));
		store.deleteIncompleted(1);
		assertEquals(0, store.getNumberOfTasks());
	}

	@Test
	public void testDeleteCompleted() {
		Storage store = new Storage();
		store.add(new Task("test"));
		store.markComplete(0);
		store.deleteCompleted(1);
		assertEquals(0, store.getCompletedSize());
	}

	@Test
	public void testEdit() {
		Storage store = new Storage();
		store.add(new Task("test"));
		store.edit(0, new Task("result"));
		assertEquals("result", store.getTask(0));
	}

	@Test
	public void testGetNumberOfTasks() {
		Storage store = new Storage();
		store.add(new Task("test"));
		assertEquals(1, store.getNumberOfTasks());
	}

	@Test
	public void testGetTask() {
		Storage store = new Storage();
		store.add(new Task("test"));
		assertEquals("test", store.getTask(0));
	}

}
