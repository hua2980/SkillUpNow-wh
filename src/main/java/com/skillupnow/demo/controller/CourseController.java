package com.skillupnow.demo.controller;


import com.google.gson.Gson;
import com.skillupnow.demo.model.po.Course;
import com.skillupnow.demo.repository.CourseRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The CourseController class provides a RESTful API for managing courses.
 * This includes listing all available courses and retrieving course details.
 *
 * @author Hua Wang
 */
@RestController
@RequestMapping("/course")
public class CourseController {
  Logger logger = LoggerFactory.getLogger(CourseController.class);
  Gson gson = new Gson();
  @Autowired
  CourseRepository courseRepository;

  /**
   * Lists all available courses.
   *
   * @return A ResponseEntity containing a list of all courses.
   */
  @GetMapping("/list")
  public ResponseEntity<List<Course>> listAll() {
    return ResponseEntity.ok().body(courseRepository.findAll());
  }

  /**
   * Retrieves the details of a specific course based on the provided course ID.
   *
   * @param id The ID of the course to retrieve details for.
   * @return A ResponseEntity containing a Course object containing the course details.
   */
  @GetMapping("/info/{id}")
  public ResponseEntity<Course> getCourseDetails(@PathVariable("id") Long id) {
    logger.info("getCourseDetails. id={}", id);
    Course course = courseRepository.findById(id).orElseThrow(null);
    return ResponseEntity.ok().body(course);
  }
}
