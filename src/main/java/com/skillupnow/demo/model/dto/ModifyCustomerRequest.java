package com.skillupnow.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.skillupnow.demo.exception.ValidationGroups;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

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

  public ModifyCustomerRequest() {
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getHeadline() {
    return headline;
  }

  public void setHeadline(String headline) {
    this.headline = headline;
  }
}
