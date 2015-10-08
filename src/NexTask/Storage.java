package NexTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * This class is to save tasks into txt file.
 * 
 * @author Jingjing Wang
 *
 */
public class Storage {
    
	private static final String DEFAULT_SAVE_DIRECTORY = "NexTask";
	private static final String SAVE_DIRECTORY_FORMAT = "%s\\NexTask";
	private static final String FILE_PATH_FORMAT = "%s\\%s.txt";
    private static final String ERROR_FILE_NOT_FOUND = "File was not found: %s";
    private static final String FILE_NAME = "NexTask";
    private static int taskNum = 1;
    private ArrayList<Task> tasks;
    private String savePath;
    
    
    public Storage(String directory, ArrayList<Task> tasks) {
        this.savePath = String.format(SAVE_DIRECTORY_FORMAT, directory);;
        this.tasks = tasks;
        createSaveDirectory(savePath);      
    }
    
    public Storage() {
        savePath = DEFAULT_SAVE_DIRECTORY;
        // This is to get the current directory of the program.
        createSaveDirectory(savePath);
    }

    private void createSaveDirectory(String directory) {
        File dir = new File(directory);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    public void storeToFile() {
        createSaveDirectory(savePath);
        try (PrintWriter writer = new PrintWriter(String.format(FILE_PATH_FORMAT, savePath, FILE_NAME))) {
            for (Task line : tasks) {
            		
                writer.println(taskNum + line.getName());
                taskNum ++;
            }
          } catch (FileNotFoundException e) {
              System.out.println(String.format(ERROR_FILE_NOT_FOUND, FILE_NAME));
          }
      }
    
    public String retrive(String fileName){   // Use this later. 		
    		return fileName;
    }
  }