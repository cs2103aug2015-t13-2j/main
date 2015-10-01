
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
    
    public static final String DEFAULT_SAVE_DIRECTORY = "NexText";
    private static final String SAVE_DIRECTORY_FORMAT = "%s\\collated";
    private static final String COLLATED_FILE_PATH_FORMAT = "%s\\%s.txt";
    private static final String ERROR_FILE_NOT_FOUND = "File was not found: %s";
    private ArrayList<Task> tasks;
    private String savePath;
    
    
    public Storage(String directory, ArrayList<Task> tasks) {
        this.savePath = String.format(SAVE_DIRECTORY_FORMAT, directory);
        this.tasks = tasks;
        createSaveDirectory(savePath);      
    }
    
    public Storage() {
        savePath = DEFAULT_SAVE_DIRECTORY;
        createSaveDirectory(DEFAULT_SAVE_DIRECTORY);
    }

    private void createSaveDirectory(String directory) {
        File dir = new File(directory);

        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    public void addCollatedFile(String fileName) {
        createSaveDirectory(savePath);
        try (PrintWriter writer = new PrintWriter(String.format(COLLATED_FILE_PATH_FORMAT, savePath, fileName))) {
            for (Task line : tasks) {
                writer.println(line.toString());
            }
          } catch (FileNotFoundException e) {
              System.out.println(String.format(ERROR_FILE_NOT_FOUND, fileName));
          }
      }
  }
