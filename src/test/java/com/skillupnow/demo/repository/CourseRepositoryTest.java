package com.skillupnow.demo.repository;

import com.skillupnow.demo.model.po.Course;
import com.skillupnow.demo.model.po.TeachPlan;
import com.skillupnow.demo.repository.CourseRepository;
import java.util.Arrays;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class contains test cases for the CourseRepository class.
 *
 * @author Hua Wang
 */
@DataJpaTest
public class CourseRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private CourseRepository courseRepository;

  /**
   * Tests the findById method of the CourseRepository class.
   */
  @Test
  public void testFindById() {
    // Setup
    TeachPlan teachPlan = new TeachPlan();
    teachPlan.setPname("Test Teach Plan");
    teachPlan.setParentId(0L);
    teachPlan.setGrade((short) 1);

    Course course = new Course();
    course.setName("Test Course");
    course.setCourseType("test_course_type");
    course.setTeachPlans(Collections.singletonList(teachPlan));

    teachPlan.setCourse(course);
    
    entityManager.persist(course);
    entityManager.flush();

    // Test case: find a valid course
    Optional<Course> foundCourseOptional = courseRepository.findById(course.getId());
    assertTrue(foundCourseOptional.isPresent());
    Course foundCourse = foundCourseOptional.get();
    assertEquals(course.getId(), foundCourse.getId());
    assertEquals("Test Course", foundCourse.getName());

    // Test case: find a non-existent course
    Optional<Course> nonExistentCourseOptional = courseRepository.findById(-1L);
    assertFalse(nonExistentCourseOptional.isPresent());
  }
}
