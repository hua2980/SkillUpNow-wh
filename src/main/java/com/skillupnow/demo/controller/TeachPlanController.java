package com.skillupnow.demo.controller;

import com.skillupnow.demo.model.dto.TeachPlanTreeDto;
import com.skillupnow.demo.service.TeachPlanService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The TeachPlanController class provides a RESTful API for managing teaching plans.
 * This includes retrieving a teaching plan's tree structure based on the provided course ID.
 *
 * @author Hua Wang
 */
@RestController
@RequestMapping("/teach-plan")
public class TeachPlanController {

  @Autowired
  TeachPlanService teachPlanService;

  /**
   * Retrieves the tree structure of a teaching plan based on the provided course ID.
   *
   * @param id The ID of the course for which the teaching plan tree structure is requested.
   * @return An object representing the tree structure of the teaching plan.
   */
  @GetMapping("/{id}/tree-nodes")
  public ResponseEntity<List<TeachPlanTreeDto>> getTeachPlan(@PathVariable("id") Long id) {
    return ResponseEntity.ok().body(teachPlanService.getTeachPlanTreeByCourseId(id));
  }

}
