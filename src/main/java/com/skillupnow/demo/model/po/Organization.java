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

@Entity
@Table(name = "organization")
@Getter
@Setter
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
}
