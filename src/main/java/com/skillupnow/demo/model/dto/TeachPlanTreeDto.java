package com.skillupnow.demo.model.dto;

import com.skillupnow.demo.model.po.Course;
import com.skillupnow.demo.model.po.TeachPlan;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeachPlanTreeDto extends TeachPlan {
  private List<TeachPlanTreeDto> teachPlanTreeNodes;
  private boolean showSubNodes = false;

  public TeachPlanTreeDto(Long id, String pname, Long parentId, Short grade, String description, Integer orderBy, Course course) {
    super(id, pname, parentId, grade, description, orderBy, course);
    this.teachPlanTreeNodes = new ArrayList<>();
  }
}
