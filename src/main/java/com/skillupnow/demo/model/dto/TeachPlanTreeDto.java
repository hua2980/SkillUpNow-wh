package com.skillupnow.demo.model.dto;

import com.skillupnow.demo.model.po.Course;
import com.skillupnow.demo.model.po.TeachPlan;
import java.util.ArrayList;
import java.util.List;

public class TeachPlanTreeDto extends TeachPlan {
  private List<TeachPlanTreeDto> teachPlanTreeNodes;
  private boolean showSubNodes = false;

  public TeachPlanTreeDto(Long id, String pname, Long parentId, Short grade, String description, Integer orderBy, Course course) {
    super(id, pname, parentId, grade, description, orderBy, course);
    this.teachPlanTreeNodes = new ArrayList<>();
  }

  public List<TeachPlanTreeDto> getTeachPlanTreeNodes() {
    return teachPlanTreeNodes;
  }

  public void setTeachPlanTreeNodes(List<TeachPlanTreeDto> teachPlanTreeNodes) {
    this.teachPlanTreeNodes = teachPlanTreeNodes;
  }

  public boolean isShowSubNodes() {
    return showSubNodes;
  }

  public void setShowSubNodes(boolean showSubNodes) {
    this.showSubNodes = showSubNodes;
  }
}
