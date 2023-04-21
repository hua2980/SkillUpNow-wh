package com.skillupnow.demo.model.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.skillupnow.demo.model.UserType;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public class User implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @JsonProperty
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JsonProperty
  @Column(nullable = false, unique = true)
  String username;

  @JsonProperty
  @Column(nullable = false)
  String password;

  @JsonProperty
  @Column(name = "user_type", nullable = false)
  @Enumerated(EnumType.STRING)
  UserType userType;

  @JsonProperty
  @Column(name = "email")
  String email;


  public User() {
  }

  public User(String username, String password, UserType type) {
    this.username = username;
    this.password = password;
    this.userType = type;
  }
}
