package com.skillupnow.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.skillupnow.demo.model.UserType;

public class CreateUserRequest {
	@JsonProperty
	private UserType userType;

	@JsonProperty
	private String confirmPassword;

	@JsonProperty
	private String username;

	@JsonProperty
	private String password;

	public CreateUserRequest(String confirmPassword, String username, String password, UserType userType) {
		this.confirmPassword = confirmPassword;
		this.username = username;
		this.password = password;
		this.userType = userType;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

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

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}
}
