package com.skillupnow.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.skillupnow.demo.exception.ValidationGroups;
import com.skillupnow.demo.model.UserType;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * The CreateUserRequest class represents the data transfer object for creating a new user.
 * It includes the user type, username, password, and confirm password fields.
 *
 * @author Hua Wang
 */
@Getter
@Setter
public class CreateUserRequest {

  @NotNull(message = "user type is required", groups = {ValidationGroups.Insert.class})
  @JsonProperty
  private UserType userType;

  @NotEmpty(message = "confirm password is required", groups = {ValidationGroups.Insert.class})
  @JsonProperty
  private String confirmPassword;

  @NotEmpty(message = "username is required", groups = {ValidationGroups.Insert.class})
  @JsonProperty
  private String username;

  @NotEmpty(message = "password is required", groups = {ValidationGroups.Insert.class})
  @Size(min = 7, max = 20, message = "password must be between 7 and 20 characters", groups = {
      ValidationGroups.Insert.class})
  @JsonProperty
  private String password;

  /**
   * Default constructor for CreateUserRequest.
   */
  public CreateUserRequest() {
  }

  /**
   * Constructor for CreateUserRequest with parameters.
   * @param userType The user type of the new user.
   * @param confirmPassword The confirmation password field for the new user.
   * @param username The username of the new user.
   * @param password The password for the new user.
   */
  public CreateUserRequest(UserType userType, String confirmPassword, String username,
      String password) {
    this.userType = userType;
    this.confirmPassword = confirmPassword;
    this.username = username;
    this.password = password;
  }

  /**
   * Checks if the password and confirm password fields match.
   * @return true if the password and confirm password fields match, false otherwise.
   */
  @AssertTrue(message = "Password and confirm password must match", groups = {
      ValidationGroups.Insert.class})
  public boolean isPasswordMatch() {
    return password.equals(confirmPassword);
  }
}
