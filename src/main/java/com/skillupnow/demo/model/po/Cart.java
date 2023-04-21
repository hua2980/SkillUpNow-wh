package com.skillupnow.demo.model.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cart")
public class Cart implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonProperty
  private Long id;

  /*
  this is a bidirectional relationship,
  we can retrieve the cart data from the user,
  and the user data from the cart.
   */
  @OneToOne(mappedBy = "cart")
  @JsonIgnore
  private Customer customer;

  /*
  1. this is a unidirectional relationship,
  we can retrieve the course data from the cart,
  but not the cart data from the course.

  2. default fetch type for ManyToMany is LAZY:
  the initial query for Cart entity will NOT
  retrieve the entity data for courses.
  */
  @ManyToMany
  @JsonProperty
  private List<Course> courses;

  @JsonProperty
  private BigDecimal total;

  @JsonProperty
  private BigDecimal originalTotal;

  public BigDecimal getOriginalTotal() {
    return originalTotal;
  }

  public void setOriginalTotal(BigDecimal originalTotal) {
    this.originalTotal = originalTotal;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public List<Course> getCourses() {
    return courses;
  }

  public void setCourses(List<Course> courses) {
    this.courses = courses;
  }

  public BigDecimal getTotal() {
    return total;
  }

  public void setTotal(BigDecimal total) {
    this.total = total;
  }

  public void addCourse(Course course) {
    if (courses == null) {
      courses = new ArrayList<>();
      initializeCart();
    }
    courses.add(course);
    total = total.add(course.getPrice());
    originalTotal = originalTotal.add(course.getOriginalPrice());
  }

  public void removeCourse(Course course) {
    if (courses == null) {
      courses = new ArrayList<>();
      initializeCart();
    }
    courses.remove(course);
    total = total.subtract(course.getPrice());
    originalTotal = originalTotal.subtract(course.getOriginalPrice());
  }

  public void initializeCart() {
    total = new BigDecimal(0);
    originalTotal = new BigDecimal(0);
  }

  @JsonProperty("username")
  public String getUsername() {
    return customer.getUsername();
  }
}
