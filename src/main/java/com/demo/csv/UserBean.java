package com.demo.csv;

import com.opencsv.bean.CsvBindByName;

public class UserBean {
	@CsvBindByName(column = "userName")
	private String userName;
	@CsvBindByName(column = "Password")
	private String Password;
	
	public UserBean() {
		
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
