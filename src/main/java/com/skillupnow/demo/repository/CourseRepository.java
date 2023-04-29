package com.skillupnow.demo.repository;

import com.skillupnow.demo.model.po.Course;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * CourseRepository interface provides methods to interact with the Course entity in the database.
 * It extends JpaRepository which provides generic CRUD operations.
 * This interface provides a custom method to find a course by its ID.
 *
 * @author Hua Wang
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
  /**
   * Find a course by its ID.
   *
   * @param id The ID of the course to be retrieved.
   * @return An Optional containing the Course object matching the given ID, or empty if not found.
   */
  @Query("select c from Course c where c.id = ?1")
  Optional<Course> findById(Long id);
}
