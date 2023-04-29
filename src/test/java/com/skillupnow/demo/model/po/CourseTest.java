package com.skillupnow.demo.model.po;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.skillupnow.demo.model.po.Organization;
import com.skillupnow.demo.model.po.Course;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This class contains test cases for the Course class.
 *
 * @author Hua Wang
 */
public class CourseTest {
  private Course course;

  /**
   * This method sets up the test environment by initializing the Course instance.
   */
  @BeforeEach
  public void setUp() {
    course = new Course();
  }

  /**
   * Tests the getter and setter methods of the Course class.
   */
  @Test
  public void testGettersAndSetters() {
    // Normal case
    TeachPlan teachPlan = new TeachPlan();
    teachPlan.setId(1L);
    teachPlan.setPname("Test Plan");
    teachPlan.setOrderBy(1);
    teachPlan.setParentId(0L);
    teachPlan.setGrade((short) 1);
    teachPlan.setCourse(course);

    course.setId(1L);
    course.setName("Test Course");
    course.setCourseType("Online");
    course.setDescription("A test course for learning");
    course.setPic("test_course.jpg");
    course.setPrice(new BigDecimal("99.99"));
    course.setOriginalPrice(new BigDecimal("199.99"));
    course.setTeachPlans(Collections.singletonList(teachPlan));

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
    assertEquals(1, course.getTeachPlans().size());
    assertEquals(teachPlan, course.getTeachPlans().get(0));
  }
}

