package com.skillupnow.demo.model.dto;

import com.skillupnow.demo.model.po.Course;
import com.skillupnow.demo.model.po.TeachPlan;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * The TeachPlanTreeDto class extends TeachPlan and represents a tree node for organizing
 * teach plans in a hierarchical structure. It includes a list of child nodes and a flag
 * indicating whether to show subnodes.
 *
 * @author Hua Wang
 */
@Getter
@Setter
public class TeachPlanTreeDto extends TeachPlan {
  private List<TeachPlanTreeDto> teachPlanTreeNodes;
  private boolean showSubNodes = false;

  /**
   * Constructor for TeachPlanTreeDto with parameters.
   *
   * @param id The ID of the teach plan.
   * @param pname The name of the teach plan.
   * @param parentId The ID of the parent teach plan.
   * @param grade The grade of the teach plan.
   * @param description The description of the teach plan.
   * @param orderBy The order by value for sorting teach plans.
   * @param course The course associated with the teach plan.
   */
  public TeachPlanTreeDto(Long id, String pname, Long parentId, Short grade, String description, Integer orderBy, Course course) {
    super(id, pname, parentId, grade, description, orderBy, course);
    this.teachPlanTreeNodes = new ArrayList<>();
  }
}
