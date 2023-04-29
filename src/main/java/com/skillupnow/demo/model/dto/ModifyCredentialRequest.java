package com.skillupnow.demo.model.dto;

import com.skillupnow.demo.exception.ValidationGroups;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * The ModifyCredentialRequest class represents the data transfer object for updating a user's
 * credentials. It includes the username, new password, and confirm password fields.
 *
 * @author Hua Wang
 */
@Getter
@Setter
public class ModifyCredentialRequest {

  @NotEmpty(message = "username must not be empty", groups = {ValidationGroups.Update.class})
  private String username;

  @NotEmpty(message = "password is required", groups = {ValidationGroups.Update.class})
  @Size(min = 7, max = 20, message = "password must be between 7 and 20 characters", groups = {
      ValidationGroups.Update.class})
  private String newPassword;

  @NotEmpty(message = "confirm password is required", groups = {ValidationGroups.Update.class})
  private String confirmPassword;

  /**
   * Checks if the new password and confirm password fields match.
   *
   * @return true if the new password and confirm password fields match, false otherwise.
   */
  @AssertTrue(message = "Password and confirm password must match", groups = {
      ValidationGroups.Update.class})
  public boolean isPasswordMatch() {
    return newPassword != null && newPassword.equals(confirmPassword);
  }
}
