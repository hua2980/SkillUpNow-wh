package com.skillupnow.demo.model.dto;

import com.skillupnow.demo.exception.ValidationGroups;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

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

  @AssertTrue(message = "Password and confirm password must match", groups = {
      ValidationGroups.Update.class})
  public boolean isPasswordMatch() {
    return newPassword.equals(confirmPassword);
  }
}
