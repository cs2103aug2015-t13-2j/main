package NexTask;


abstract class Task implements Cloneable{
	private String name;
	private String searchField;
	

	public Task() {
		this.name = "";
	}
	public Task(String name){
		this.name = name;
	}
	
	public Object clone()throws CloneNotSupportedException{  
		return super.clone();  
	}
	
	public String getName() {
		return this.name;
	}
	
	public void editName(String name){
		this.name = name;
	}
	
	public String getSearchField(){
		return searchField;
	}
	
	abstract boolean equals(Task other);

	
}
