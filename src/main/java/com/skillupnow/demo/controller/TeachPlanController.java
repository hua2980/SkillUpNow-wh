package com.skillupnow.demo.controller;

import com.skillupnow.demo.service.Iml.TeachPlanServiceImpl;
import com.skillupnow.demo.service.TeachPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("/teach-plan")
public class TeachPlanController {

  @Autowired
  TeachPlanService teachPlanService;

  @GetMapping("/{id}/tree-nodes")
  public Object getTeachPlan(@PathVariable("id") Long id) {
    return teachPlanService.getTeachPlanTreeByCourseId(id);
  }

}
