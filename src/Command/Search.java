package Command;

import NexTask.Task;

public class Search extends Command{
	private final String UNABLE_TO_SEARCH = "Sorry, unable to find any results for the search term!";
	
	public Search(){
		super();
	}
	public String execute(){
		String searchMsg = "";
		String[] searchSpecification = getSearchSpecification().split(" ");
		
		int numOfIncomplete = storage.getSize();
		int numOfCompleted  = storage.getCompletedSize();
		int numOfResult = 0;
		
		if (numOfIncomplete > 0){
			System.out.println("Incomplete:");
			for (int i = 0; i < numOfIncomplete; i++){
				Task task = storage.getTaskArray().get(i);
				boolean match = false;
				String [] searchField = task.toString().split("[ :]+");
				for (String search: searchField){
					for (String specification: searchSpecification){
						if (search.contains(specification)){
							match = true;
							numOfResult ++;
						}
					}
				}
				if (match){
					System.out.println(i + 1 + ". " + task.toString());
				}	
			}
		}
		
		if (numOfCompleted > 0){
			System.out.println("Completed:");
			for (int i = 0; i < numOfCompleted; i++){
				Task task = storage.getCompletedTasks().get(i);
				boolean match = false;
				String [] searchField = task.toString().split(" :");
				for (String search: searchField){
					for (String specification: searchSpecification){
						if (search.contains(specification)){
							match = true;
							numOfResult ++;
						}
					}
				}	
				if (match){
					System.out.println(i + 1 + ". " + task.toString());
				}
			}
		}
		if (numOfResult == 0){
			searchMsg = UNABLE_TO_SEARCH;
		} 
		return searchMsg;
	}
}
