package com.skillupnow.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateUserRequest {
	@JsonProperty
	private boolean isCustomer;

	@JsonProperty
	private String confirmPassword;

	@JsonProperty
	private String username;

	@JsonProperty
	private String password;

	public CreateUserRequest(String confirmPassword, String username, String password, boolean isCustomer) {
		this.confirmPassword = confirmPassword;
		this.username = username;
		this.password = password;
		this.isCustomer = isCustomer;
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

	public boolean isCustomer() {
		return isCustomer;
	}

	public void setCustomer(boolean customer) {
		isCustomer = customer;
	}
}
