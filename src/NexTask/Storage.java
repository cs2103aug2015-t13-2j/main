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
    
    private static final String fileName = "NexText.txt";
    private static final String ERROR_FILE_NOT_FOUND = "File was not found: %s";
    private ArrayList<Task> tasks;
    private String savePath;
    
    
    public Storage(String directory, ArrayList<Task> tasks) {
        this.savePath = directory + fileName;
        this.tasks = tasks;
        createSaveDirectory(savePath);      
    }
    
    public Storage() {
        savePath = System.getProperty("user.dir");
        createSaveDirectory(savePath);
    }

    private void createSaveDirectory(String directory) {
        File dir = new File(directory);

        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    public void addCollatedFile(String fileName) {
        createSaveDirectory(savePath);
        try (PrintWriter writer = new PrintWriter(savePath)) {
            for (Task line : tasks) {
                writer.println(line.toString());
            }
          } catch (FileNotFoundException e) {
              System.out.println(String.format(ERROR_FILE_NOT_FOUND, fileName));
          }
      }
    
    public String retrive(String fileName){   // Probably don't need this method 		
    		return fileName;
    }
  }
