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
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * The Organization class is an entity that extends the User class and represents an organization
 * in the application. This class contains properties such as courses and organizationName.
 *
 * @author Hua Wang
 */
@Entity
@Table(name = "organization")
@Getter
@Setter
public class Organization extends User implements Serializable {
  @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
  @JsonIgnore
  private List<Course> courses;

  @Column
  @JsonProperty
  private String organizationName;

  /**
   * Default constructor for the Organization class. Initializes userType as ORGANIZATION,
   * creates an empty list of courses, and sets the organizationName to "default".
   */
  public Organization() {
    this.userType = UserType.ORGANIZATION;
    this.courses = new ArrayList<>();
    this.organizationName = "default";
  }

  /**
   * Constructor for the Organization class with username, password, and UserType.
   * Initializes userType, creates an empty list of courses, and sets the organizationName to "default".
   *
   * @param username The username of the organization.
   * @param password The password of the organization.
   * @param type The UserType of the organization.
   */
  public Organization(String username, String password, UserType type) {
    super(username, password, type);
    this.courses = new ArrayList<>();
    this.organizationName = "default";
  }

  /**
   * Constructor for the Organization class with username, password, UserType, courses, and organizationName.
   *
   * @param username The username of the organization.
   * @param password The password of the organization.
   * @param type The UserType of the organization.
   * @param courses The list of courses associated with the organization.
   * @param organizationName The name of the organization.
   */
  public Organization(String username, String password, UserType type, List<Course> courses,
      String organizationName) {
    super(username, password, type);
    this.courses = courses;
    this.organizationName = organizationName;
  }
}
