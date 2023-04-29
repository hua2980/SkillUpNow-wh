package com.skillupnow.demo.model.po;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.skillupnow.demo.model.po.Course;
import com.skillupnow.demo.model.po.TeachPlan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This class contains test cases for the TeachPlan.
 *
 * @author Hua Wang
 */
public class TeachPlanTest {
  private TeachPlan teachPlan;

  /**
   * This method sets up the test environment by initializing the TeachPlan instance.
   */
  @BeforeEach
  public void setUp() {
    Course course = new Course();
    course.setId(1L);
    course.setName("Test Course");
    teachPlan = new TeachPlan(1L, "Test Plan", 0L, (short) 1, "Test description", 1, course);
  }

  /**
   * Tests the getter and setter methods of the TeachPlan class.
   */
  @Test
  public void testGettersAndSetters() {
    // Normal case
    teachPlan.setPname("New Test Plan");
    teachPlan.setParentId(2L);
    teachPlan.setGrade((short) 2);
    teachPlan.setDescription("New description");
    teachPlan.setOrderBy(2);

    Course newCourse = new Course();
    newCourse.setId(2L);
    newCourse.setName("New Test Course");
    teachPlan.setCourse(newCourse);

    assertEquals(1L, teachPlan.getId());
    assertEquals("New Test Plan", teachPlan.getPname());
    assertEquals(2L, teachPlan.getParentId());
    assertEquals((short) 2, teachPlan.getGrade());
    assertEquals("New description", teachPlan.getDescription());
    assertEquals(2, teachPlan.getOrderBy());
    assertEquals(newCourse, teachPlan.getCourse());
    assertEquals("New Test Course", teachPlan.getCourseName());

    // Constructors
    TeachPlan teachPlan2 = new TeachPlan();
    assertNotNull(teachPlan2);
  }
}

