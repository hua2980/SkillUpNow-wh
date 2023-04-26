package com.skillupnow.demo.model.po;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.math.BigDecimal;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CartTest {
  private Cart cart;

  @BeforeEach
  public void setUp() {
    cart = new Cart();
  }

  @Test
  public void testGettersAndSetters() {
    // Normal case
    cart.setId(1L);
    cart.setTotal(new BigDecimal("0.00"));
    cart.setOriginalTotal(new BigDecimal("0.00"));
    cart.setCourses(new ArrayList<>());

    Customer customer = new Customer();
    customer.setId(1L);
    customer.setUsername("Irene");
    cart.setCustomer(customer);

    assertEquals(1L, cart.getId());
    assertEquals(new BigDecimal("0.00"), cart.getTotal());
    assertEquals(new BigDecimal("0.00"), cart.getOriginalTotal());
    assertEquals(customer, cart.getCustomer());
    assertEquals("Irene", cart.getUsername());
    assertEquals(0, cart.getCourses().size());
  }

  @Test
  public void testAddAndRemoveCourse() {
    Course course1 = new Course();
    course1.setPrice(new BigDecimal("100.00"));
    course1.setOriginalPrice(new BigDecimal("120.00"));

    Course course2 = new Course();
    course2.setPrice(new BigDecimal("80.00"));
    course2.setOriginalPrice(new BigDecimal("90.00"));

    cart.addCourse(course1);
    assertEquals(1, cart.getCourses().size());
    assertEquals(new BigDecimal("100.00"), cart.getTotal());
    assertEquals(new BigDecimal("120.00"), cart.getOriginalTotal());

    cart.addCourse(course2);
    assertEquals(2, cart.getCourses().size());
    assertEquals(new BigDecimal("180.00"), cart.getTotal());
    assertEquals(new BigDecimal("210.00"), cart.getOriginalTotal());

    cart.removeCourse(course1);
    assertEquals(1, cart.getCourses().size());
    assertEquals(new BigDecimal("80.00"), cart.getTotal());
    assertEquals(new BigDecimal("90.00"), cart.getOriginalTotal());
  }
}
