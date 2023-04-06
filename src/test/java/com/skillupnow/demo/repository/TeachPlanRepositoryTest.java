package com.skillupnow.demo.repository;

import com.skillupnow.demo.model.po.Course;
import com.skillupnow.demo.model.po.TeachPlan;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TeachPlanRepositoryTest {
  @Autowired
  private TeachPlanRepository teachPlanRepository;

  @Autowired
  private CourseRepository courseRepository;

  /**
   * Test if teach plan repository can fina plans by course id.
   */
  @Test
  void testFindByCourseId() {
    Course course = courseRepository.findById(4L).orElse(null);
    List<TeachPlan> teachPlans = teachPlanRepository.findByCourse(course);
    Assertions.assertNotNull(teachPlans);
    Assertions.assertEquals(9, teachPlans.size());
  }

}
