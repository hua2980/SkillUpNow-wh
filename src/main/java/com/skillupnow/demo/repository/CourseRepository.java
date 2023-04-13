package com.skillupnow.demo.repository;

import com.skillupnow.demo.model.po.Course;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

  @Query("select c from Course c where c.id = ?1")
  Optional<Course> findById(Long id);
}
