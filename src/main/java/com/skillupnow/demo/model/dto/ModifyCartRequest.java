package com.skillupnow.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.skillupnow.demo.exception.ValidationGroups;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Digits;

public class ModifyCartRequest {
  @JsonProperty
  @NotEmpty(message = "Username is required", groups = {ValidationGroups.Update.class})
  private String username;

  @JsonProperty
  @NotNull(message = "CourseId is required", groups = {ValidationGroups.Update.class})
  private long courseId;

  @JsonProperty
  @DecimalMax(value = "1", message = "Delete must be 0 or 1", groups = {ValidationGroups.Update.class})
  @DecimalMin(value = "0", message = "Delete must be 0 or 1", groups = {ValidationGroups.Update.class})
  private int delete;

  public ModifyCartRequest(String username, long courseId, int delete) {
    this.username = username;
    this.courseId = courseId;
    this.delete = delete;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public long getCourseId() {
    return courseId;
  }

  public void setCourseId(long courseId) {
    this.courseId = courseId;
  }

  public int getDelete() {
    return delete;
  }

  public void setDelete(int delete) {
    this.delete = delete;
  }
}
