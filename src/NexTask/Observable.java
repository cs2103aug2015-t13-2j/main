package NexTask;

//@@author A0145695R

public interface Observable {
	public void addObserver(Observer o);
	public void notifyObservers();	
}
