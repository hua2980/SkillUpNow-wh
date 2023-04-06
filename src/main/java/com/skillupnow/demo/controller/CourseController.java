package com.skillupnow.demo.controller;


import com.skillupnow.demo.model.po.Course;
import com.skillupnow.demo.repository.CourseRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("/course")
public class CourseController {
  @Autowired
  CourseRepository courseRepository;

  @GetMapping("/list")
  public List<Course> listAll() {
    return courseRepository.findAll();
  }

  @GetMapping("/info/{id}")
  public Course getCourseDetails(@PathVariable("id") Long id) {
    return courseRepository.findById(id).orElseThrow(null);
  }
}
