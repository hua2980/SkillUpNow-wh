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
import lombok.Getter;
import lombok.Setter;

/**
 * The Cart class represents the shopping cart of a customer.
 * It includes the cart ID, a list of courses in the cart,
 * the customer, and the total and original total prices.
 *
 * @author Hua Wang
 */
@Entity
@Table(name = "cart")
@Getter
@Setter
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
  @OneToOne(mappedBy = "cart", cascade = CascadeType.ALL)
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

  /**
   * Adds a course to the cart and updates the total and original total prices.
   *
   * @param course The course to be added to the cart.
   */
  public void addCourse(Course course) {
    if (courses == null) {
      courses = new ArrayList<>();
    }
    if (courses.isEmpty()) {
      initializeCart();
    }
    courses.add(course);
    total = total.add(course.getPrice());
    originalTotal = originalTotal.add(course.getOriginalPrice());
  }

  /**
   * Removes a course from the cart and updates the total and original total prices.
   *
   * @param course The course to be removed from the cart.
   */
  public void removeCourse(Course course) {
    if (courses == null || courses.isEmpty()) {
      return;
    }
    courses.remove(course);
    total = total.subtract(course.getPrice());
    originalTotal = originalTotal.subtract(course.getOriginalPrice());
  }

  /**
   * Initializes the cart by setting total and original total prices to zero.
   */
  public void initializeCart() {
    total = new BigDecimal(0);
    originalTotal = new BigDecimal(0);
  }

  /**
   * Returns the username of the associated customer.
   *
   * @return The username of the associated customer.
   */
  @JsonProperty("username")
  public String getUsername() {
    return customer.getUsername();
  }
}
