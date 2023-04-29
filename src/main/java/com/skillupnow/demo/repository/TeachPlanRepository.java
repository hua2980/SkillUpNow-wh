package com.skillupnow.demo.repository;

import com.skillupnow.demo.model.po.Course;
import com.skillupnow.demo.model.po.TeachPlan;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * TeachPlanRepository interface provides methods to interact with the TeachPlan entity in the database.
 * It extends JpaRepository which provides generic CRUD operations on a repository for a specific type.
 *
 * @author Hua Wang
 */
@Repository
public interface TeachPlanRepository extends JpaRepository<TeachPlan, Long> {

  /**
   * Find a list of TeachPlan entities by the associated Course entity.
   *
   * @param course the Course entity to find associated TeachPlan entities
   * @return a list of TeachPlan entities associated with the specified Course
   */
  List<TeachPlan> findByCourse(Course course);

}
