package NexTask;


abstract class Task {
	private String name;
	
	public Task(String name){
		this.name = name;
	}
	
	public void editName(String name){
		this.name = name;
	}
	
}
