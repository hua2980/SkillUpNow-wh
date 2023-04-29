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
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * The TeachPlan class is an entity that represents a teaching plan in the application.
 * It contains properties such as id, pname, parentId, grade, description, orderBy, and course.
 *
 * @author Your Name
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "teach_plan")
@Getter
@Setter
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

  /**
   * Constructor for the TeachPlan class with id, pname, parentId, grade, description, orderBy, and course.
   *
   * @param id The id of the teaching plan.
   * @param pname The name of the teaching plan.
   * @param parentId The parent id of the teaching plan.
   * @param grade The grade of the teaching plan.
   * @param description The description of the teaching plan.
   * @param orderBy The order of the teaching plan.
   * @param course The course associated with the teaching plan.
   */
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

  /**
   * Default constructor for the TeachPlan class.
   */
  public TeachPlan() {
  }

  /**
   * Getter method for the course name associated with the teaching plan.
   *
   * @return The name of the associated course.
   */
  @JsonProperty("course")
  public String getCourseName() {
    return course.getName();
  }
}
