package com.slabs.collaborate.core.entity;

import java.io.Serializable;

public class User implements Serializable {

	private String userName;

	private String password;

	private String firstName;

	private String lastName;

	private String sex;

	private String email;

	private String mobile;

	public User() {

		super();
	}

	public User(String userName, String password, String firstName, String lastName, String sex, String email, String mobile) {

		super();
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.sex = sex;
		this.email = email;
		this.mobile = mobile;
	}

	public String getUserName() {

		return userName;
	}

	public void setUserName(String userName) {

		this.userName = userName;
	}

	public String getPassword() {

		return password;
	}

	public void setPassword(String passWord) {

		this.password = passWord;
	}

	public String getFirstName() {

		return firstName;
	}

	public void setFirstName(String firstName) {

		this.firstName = firstName;
	}

	public String getLastName() {

		return lastName;
	}

	public void setLastName(String lastName) {

		this.lastName = lastName;
	}

	public String getSex() {

		return sex;
	}

	public void setSex(String sex) {

		this.sex = sex;
	}

	public String getEmail() {

		return email;
	}

	public void setEmail(String email) {

		this.email = email;
	}

	public String getMobile() {

		return mobile;
	}

	public void setMobile(String mobile) {

		this.mobile = mobile;
	}

	@Override
	public String toString() {

		return "User [userName=" + userName + ", passWord=" + password + ", firstName=" + firstName + ", lastName=" + lastName + ", sex=" + sex + ", email=" + email + ", mobile=" + mobile + "]";
	}

}
