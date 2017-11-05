package cz.consumer.interview.accountconfiguration;

import org.hibernate.validator.constraints.Length;

public class AccountRequestBody {
	
	@Length(min=3)
	private String username;
	
	@Length(min=3)
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
