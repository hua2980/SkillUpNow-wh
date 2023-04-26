package com.skillupnow.demo.model.po;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.skillupnow.demo.model.po.Organization;
import com.skillupnow.demo.model.po.Course;
import java.math.BigDecimal;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CourseTest {
  private Course course;

  @BeforeEach
  public void setUp() {
    course = new Course();
  }

  @Test
  public void testGettersAndSetters() {
    // Normal case
    course.setId(1L);
    course.setName("Test Course");
    course.setCourseType("Online");
    course.setDescription("A test course for learning");
    course.setPic("test_course.jpg");
    course.setPrice(new BigDecimal("99.99"));
    course.setOriginalPrice(new BigDecimal("199.99"));
    course.setTeachPlans(new ArrayList<>());

    Organization organization = new Organization();
    organization.setId(1L);
    organization.setOrganizationName("Test Organization");
    course.setOrganization(organization);

    assertEquals(1L, course.getId());
    assertEquals("Test Course", course.getName());
    assertEquals("Online", course.getCourseType());
    assertEquals("A test course for learning", course.getDescription());
    assertEquals("test_course.jpg", course.getPic());
    assertEquals(new BigDecimal("99.99"), course.getPrice());
    assertEquals(new BigDecimal("199.99"), course.getOriginalPrice());
    assertEquals(organization, course.getOrganization());
    assertEquals("Test Organization", course.getOrganizationName());
  }
}

