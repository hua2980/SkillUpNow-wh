package com.skillupnow.demo.model.po;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.skillupnow.demo.model.dto.CourseDto;
import com.skillupnow.demo.model.po.Course;
import com.skillupnow.demo.model.po.Customer;
import com.skillupnow.demo.model.po.Order;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This class contains test cases for the Order.
 *
 * @author Hua Wang
 */
public class OrderTest {
  private Order order;

  /**
   * This method sets up the test environment by initializing the Order instance.
   */
  @BeforeEach
  public void setUp() {
    order = new Order();
  }

  /**
   * Tests the getter and setter methods of the Order class.
   */
  @Test
  public void testGettersAndSetters() {
    // Normal case
    order.setId(1L);
    order.setTotalPrice(new BigDecimal("120.00"));
    order.setCreateTime(LocalDateTime.now());

    Course course1 = new Course();
    course1.setId(1L);
    course1.setName("Course 1");
    Course course2 = new Course();
    course2.setId(2L);
    course2.setName("Course 2");

    List<Course> courses = new ArrayList<>();
    courses.add(course1);
    courses.add(course2);

    order.setCourses(courses);

    Customer customer = new Customer();
    customer.setId(1L);
    order.setCustomer(customer);

    assertEquals(1L, order.getId());
    assertEquals(new BigDecimal("120.00"), order.getTotalPrice());
    assertNotNull(order.getCreateTime());
    assertEquals(courses, order.getCourses());
    assertEquals(customer, order.getCustomer());

    // Test getCourseDtos
    List<CourseDto> courseDtos = order.getCourseDtos();
    assertEquals(courses.size(), courseDtos.size());

    for (int i = 0; i < courseDtos.size(); i++) {
      assertEquals(courses.get(i).getId(), courseDtos.get(i).getId());
      assertEquals(courses.get(i).getName(), courseDtos.get(i).getName());
    }

    // Test getCreateTimeString
    String createTimeString = order.getCreateTime().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    assertEquals(createTimeString, order.getCreateTimeString());
  }
}

