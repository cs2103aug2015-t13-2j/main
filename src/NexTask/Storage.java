
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
    
    
    private static final String FILE_NAME = "NexTask.txt";
    private static final String USER_FILE_NAME = "\\NexTask.txt";
    private static String userPath = "";
    private static int taskNum = 1;
    private ArrayList<Task> tasks;
    
    
    public Storage(String directory, ArrayList<Task> tasks) {
        this.userPath = directory;
        this.tasks = tasks;
    }
    
    public String getPath(){
            String dir = "";
            if (userPath.equals("")){
                dir = FILE_NAME;
            } else{
                dir = userPath + USER_FILE_NAME;
            }        
            return dir;
    }
    

    public void storeToFile() {
        String savePath = getPath();
        try (PrintWriter writer = new PrintWriter(savePath)) {
            for (Task line : tasks) {
                    
                writer.println(taskNum + ". " + line.getName());
                taskNum ++;
            }
          } catch (FileNotFoundException e) {
              System.out.println(String.format(FILE_NAME));
          }
      }
    
    public String retrive(String fileName){   // Use this later.        
            return fileName;
    }
  }