package com.skillupnow.demo.repository;

import com.skillupnow.demo.model.po.Course;
import com.skillupnow.demo.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class CourseRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private CourseRepository courseRepository;

  @Test
  public void testFindById() {
    // Setup
    Course course = new Course();
    course.setName("Test Course");
    course.setCourseType("test_course_type");
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
