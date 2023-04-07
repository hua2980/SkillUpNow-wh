package com.skillupnow.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.skillupnow.demo.exception.ValidationGroups;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ModifyUserRequest {
  @NotNull(message = "id is required", groups = {ValidationGroups.Update.class})
  @JsonProperty
  private Long id;

  @Email(message = "email must be valid", groups = {ValidationGroups.Update.class})
  @JsonProperty
  private String email;

  @JsonProperty
  private String headline;

  public ModifyUserRequest() {
  }

  public ModifyUserRequest(Long id, String email, String headline) {
    this.id = id;
    this.email = email;
    this.headline = headline;
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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
