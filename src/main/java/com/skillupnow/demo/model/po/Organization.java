package com.skillupnow.demo.model.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.skillupnow.demo.model.UserType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "organization")
public class Organization extends User implements Serializable {
  @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
  @JsonIgnore
  private List<Course> courses;

  @Column(name = "organization_name", nullable = false)
  @JsonProperty
  private String organizationName;

  public Organization() {
  }

  public Organization(String username, String password, UserType type) {
    super(username, password, type);
    this.courses = new ArrayList<>();
    this.organizationName = "default";
  }

  public Organization(String username, String password, UserType type, List<Course> courses,
      String organizationName) {
    super(username, password, type);
    this.courses = courses;
    this.organizationName = organizationName;
  }

  public List<Course> getCourses() {
    return courses;
  }

  public void setCourses(List<Course> courses) {
    this.courses = courses;
  }

  public String getOrganizationName() {
    return organizationName;
  }

  public void setOrganizationName(String organizationName) {
    this.organizationName = organizationName;
  }
}
