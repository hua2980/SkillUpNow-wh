package com.skillupnow.demo.service;

import com.google.gson.Gson;
import com.skillupnow.demo.exception.SkillUpNowException;
import com.skillupnow.demo.model.dto.TeachPlanTreeDto;
import com.skillupnow.demo.model.po.Course;
import com.skillupnow.demo.model.po.TeachPlan;
import com.skillupnow.demo.repository.CourseRepository;
import com.skillupnow.demo.repository.TeachPlanRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class provides the main services related to managing teach plans in the SkillUpNow demo application.
 */
@Service
public class TeachPlanService {
  Logger logger = LoggerFactory.getLogger(TeachPlanService.class);
  @Autowired
  private TeachPlanRepository teachPlanRepository;

  @Autowired
  private CourseRepository courseRepository;

  /**
   * Retrieves the teach plan tree for a given course ID.
   *
   * @param courseId The ID of the course for which the teach plan tree should be retrieved.
   * @return A list of teach plan tree DTOs representing the teach plan hierarchy.
   * @throws SkillUpNowException If the course is not found.
   */
  public List<TeachPlanTreeDto> getTeachPlanTreeByCourseId(Long courseId) {
    Course course = courseRepository.findById(courseId).orElse(null);
    if (course == null) {
      logger.error("Failed to get teach plan tree. Course not found, courseId={}", courseId);
      throw new SkillUpNowException("Course not found");
    }
    List<TeachPlan> teachPlans = teachPlanRepository.findByCourse(course);
    return buildTree(teachPlans);
  }

  /**
   * Build a tree from a list of teach plans. We only have two levels of teach plans, so we can
   * build a tree by iterating the list twice. If the level of teach plans is more than two, we need
   * to use recursion.
   *
   * @param teachPlans teach plans
   * @return a tree of teach plans
   */
  private List<TeachPlanTreeDto> buildTree(List<TeachPlan> teachPlans) {
    List<TeachPlanTreeDto> roots = new ArrayList<>();
    Map<Long, Integer> map = new HashMap<>();

    // find roots
    for (int i = 0; i < teachPlans.size(); i++) {
      TeachPlan teachPlan = teachPlans.get(i);
      if (teachPlan.getParentId() == 0) {
        map.put(teachPlan.getId(), i);
        roots.add(
            new TeachPlanTreeDto(teachPlan.getId(), teachPlan.getPname(), teachPlan.getParentId(),
                teachPlan.getGrade(), teachPlan.getDescription(), teachPlan.getOrderBy(),
                teachPlan.getCourse()));
      }
    }

    // assign children to roots
    for (TeachPlan teachPlan : teachPlans) {
      if (teachPlan.getParentId() != 0) {
        TeachPlanTreeDto parent = roots.get(map.get(teachPlan.getParentId()));
        parent.getTeachPlanTreeNodes().add(
            new TeachPlanTreeDto(teachPlan.getId(), teachPlan.getPname(), teachPlan.getParentId(),
                teachPlan.getGrade(), teachPlan.getDescription(), teachPlan.getOrderBy(),
                teachPlan.getCourse()));
      }
    }

    // sort children
    for (TeachPlanTreeDto root : roots) {
      root.getTeachPlanTreeNodes().sort(Comparator.comparingInt(TeachPlan::getOrderBy));
    }

    return roots;
  }
}
