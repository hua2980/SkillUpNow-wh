package com.skillupnow.demo.model.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.skillupnow.demo.model.dto.CourseDto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.springframework.beans.BeanUtils;

/**
 * The Order class is an entity that represents an order placed by a customer
 * in the application. It contains properties such as id, courses, totalPrice,
 * createTime, and customer.
 *
 * @author Hua Wang
 */
@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonProperty
  private Long id;

  @ManyToMany
  @JsonIgnore
  private List<Course> courses;

  @JsonProperty
  private BigDecimal totalPrice;

  @CreationTimestamp
  @Type(type = "org.hibernate.type.LocalDateTimeType")
  @Column(name = "create_time", updatable = false)
  @JsonIgnore
  private LocalDateTime createTime;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id")
  @JsonIgnore
  private Customer customer;

  /**
   * Default constructor for the Order class.
   */
  public Order() {
  }

  /**
   * Returns a list of CourseDto objects corresponding to the courses in the order.
   *
   * @return A list of CourseDto objects.
   */
  @JsonProperty("courses")
  public List<CourseDto> getCourseDtos() {
    List<CourseDto> courseDtos = new ArrayList<>();
    for (Course course : courses) {
      CourseDto courseDto = new CourseDto();
      BeanUtils.copyProperties(course, courseDto);
      courseDtos.add(courseDto);
    }
    return courseDtos;
  }

  /**
   * Returns the formatted string representation of the createTime property.
   *
   * @return The formatted createTime as a string.
   */
  @JsonProperty("createTime")
  public String getCreateTimeString() {
    return createTime.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
  }
}
