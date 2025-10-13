package com.demo.csv;

public class UserPOJO {
	private String userName;
	private String Password;
	
	public UserPOJO() {
		
	}

	public UserPOJO(String userName, String password) {
		super();
		this.userName = userName;
		Password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	@Override
	public String toString() {
		return "UserPOJO [userName=" + userName + ", Password=" + Password + "]";
	}

}
