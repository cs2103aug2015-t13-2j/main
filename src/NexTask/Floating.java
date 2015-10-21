package NexTask;

public class Floating extends Task implements Cloneable {
	private String searchField;
	
	public Floating(String name) {
		super(name);
		this.searchField = name;
	}
	

	@Override
	public boolean equals(Task other) {
		if (other instanceof Floating) {
			if (this.getName().equals(other.getName())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public String toString() {
		return "TODO: " + this.getName();

	}
	

}
