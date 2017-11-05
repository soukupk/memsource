package cz.consumer.interview.accountconfiguration;

public class ConfigurationNotFound extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ConfigurationNotFound(String message) {
		super(message);
	}

}
