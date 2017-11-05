package cz.consumer.interview.memsource;

import java.util.Date;

public class AuthToken {

	private String token;
	
	private Date expires;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getExpires() {
		return expires;
	}

	public void setExpires(Date expires) {
		this.expires = expires;
	}
	
	public static AuthToken create(String token, Date expires) {
		AuthToken authToken = new AuthToken();
		authToken.setToken(token);
		authToken.setExpires(expires);
		return authToken;
	}
	
}
