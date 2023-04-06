package com.skillupnow.demo.repository;

import com.skillupnow.demo.model.po.Course;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CourseRepositoryTest {
  @Autowired
  private CourseRepository courseRepository;

  @Test
  public void testFindAll() {
    List<Course> courses = courseRepository.findAll();
    Assertions.assertNotNull(courses);
  }

  @Test
  void testFindById() {
    Course course = courseRepository.findById(1L).orElse(null);
    Assertions.assertNotNull(course);
    Assertions.assertEquals("The Complete Spring Boot Development BootCamp", course.getName());
  }
}
