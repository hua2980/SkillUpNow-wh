package com.skillupnow.demo.service;

import com.skillupnow.demo.model.dto.TeachPlanTreeDto;
import java.util.List;

public interface TeachPlanService {
  List<TeachPlanTreeDto> getTeachPlanTreeByCourseId(Long courseId);
}
