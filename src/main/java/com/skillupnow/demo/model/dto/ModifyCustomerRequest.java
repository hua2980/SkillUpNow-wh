package com.skillupnow.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.skillupnow.demo.exception.ValidationGroups;
import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModifyCustomerRequest {
  @NotEmpty(message = "firstname must not be empty", groups = {ValidationGroups.Update.class})
  @JsonProperty
  private String firstname;

  @NotEmpty(message = "lastname must not be empty", groups = {ValidationGroups.Update.class})
  @JsonProperty
  private String lastname;

  @Email(message = "email must be valid", groups = {ValidationGroups.Update.class})
  @JsonProperty
  private String email;

  @JsonProperty
  private String headline;

  public ModifyCustomerRequest(String firstname, String lastname, String email, String headline) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email;
    this.headline = headline;
  }
}
