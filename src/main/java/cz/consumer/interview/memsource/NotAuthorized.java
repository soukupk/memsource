package cz.consumer.interview.memsource;

public class NotAuthorized extends Exception {

	private static final long serialVersionUID = 1L;

	public NotAuthorized(String message) {
		super(message);
	}
	
	public NotAuthorized(Exception exception) {
		super(exception);
	}
	
}
