package com.skillupnow.demo.model.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "teach_plan")
public class TeachPlan implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonProperty
  private Long id;

  @Column(name = "pname", nullable = false)
  @JsonProperty
  private String pname;

  @Column(name = "parent_id", nullable = false)
  @JsonProperty
  private Long parentId;

  @Column(name = "grade", nullable = false)
  @JsonProperty
  private Short grade;

  @Column(name = "description")
  @JsonProperty
  private String description;

  @Column(name = "order_by")
  @JsonProperty
  private Integer orderBy;

  /*
  1. bidirectional relationship
  2. Default Fetch Type: EAGER
  3. Not being serialized to JSON
   */
  @ManyToOne
  @JoinColumn(name = "course_id", nullable = false)
  @JsonIgnore
  private Course course;

  public TeachPlan(Long id, String pname, Long parentId, Short grade, String description, Integer orderBy,
      Course course) {
    this.id = id;
    this.pname = pname;
    this.parentId = parentId;
    this.grade = grade;
    this.description = description;
    this.orderBy = orderBy;
    this.course = course;
  }

  public TeachPlan() {
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public String getPname() {
    return pname;
  }

  public void setPname(String pname) {
    this.pname = pname;
  }

  public Long getParentId() {
    return parentId;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getOrderBy() {
    return orderBy;
  }

  public void setOrderBy(Integer orderBy) {
    this.orderBy = orderBy;
  }

  public Short getGrade() {
    return grade;
  }

  public void setGrade(Short grade) {
    this.grade = grade;
  }

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }
}
