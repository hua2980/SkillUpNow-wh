package com.skillupnow.demo.repository;

import com.skillupnow.demo.model.po.Course;
import com.skillupnow.demo.model.po.TeachPlan;
import com.skillupnow.demo.repository.TeachPlanRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class contains test cases for the TeachPlanRepository class.
 *
 * @author Hua Wang
 */
@DataJpaTest
public class TeachPlanRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private TeachPlanRepository teachPlanRepository;

  /**
   * Tests the findByCourse method of the TeachPlanRepository class.
   */
  @Test
  public void testFindByCourse() {
    // Setup
    Course course = new Course();
    course.setName("Test Course");
    course.setCourseType("test_course_type");
    entityManager.persist(course);
    entityManager.flush();

    TeachPlan teachPlan1 = new TeachPlan();
    teachPlan1.setCourse(course);
    teachPlan1.setPname("Teach Plan 1");
    teachPlan1.setGrade(Short.valueOf("1"));
    teachPlan1.setParentId(0L);
    entityManager.persist(teachPlan1);
    entityManager.flush();

    TeachPlan teachPlan2 = new TeachPlan();
    teachPlan2.setCourse(course);
    teachPlan2.setPname("Teach Plan 2");
    teachPlan2.setGrade(Short.valueOf("2"));
    teachPlan2.setParentId(teachPlan1.getId());
    entityManager.persist(teachPlan2);
    entityManager.flush();

    // Normal case: findByCourse returns a list of TeachPlans for a course
    List<TeachPlan> teachPlans = teachPlanRepository.findByCourse(course);
    assertEquals(2, teachPlans.size());
    assertEquals("Teach Plan 1", teachPlans.get(0).getPname());
    assertEquals("Teach Plan 2", teachPlans.get(1).getPname());

    // Edge case: findByCourse returns an empty list for a course without TeachPlans
    Course courseWithoutTeachPlans = new Course();
    courseWithoutTeachPlans.setName("Empty Course");
    courseWithoutTeachPlans.setCourseType("empty_course_type");
    entityManager.persist(courseWithoutTeachPlans);
    entityManager.flush();
    List<TeachPlan> emptyTeachPlans = teachPlanRepository.findByCourse(courseWithoutTeachPlans);
    assertTrue(emptyTeachPlans.isEmpty());
  }
}
