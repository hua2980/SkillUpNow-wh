package com.skillupnow.demo.service;

import com.skillupnow.demo.exception.SkillUpNowException;
import com.skillupnow.demo.model.dto.TeachPlanTreeDto;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * This class contains test cases for the TeachPlanService class.
 *
 * @author Hua Wang
 */
@SpringBootTest
public class TeachPlanServiceTest {

  @Autowired
  TeachPlanService teachPlanService;

  /**
   * Tests the findTeachPlanByCourseId method of the TeachPlanService class.
   */
  @Test
  void testFindTeachPlanByCourseId() {
    // Normal case: find teach plans by a valid course id
    Long validCourseId = 4L;
    List<TeachPlanTreeDto> tree = teachPlanService.getTeachPlanTreeByCourseId(validCourseId);
    assertNotNull(tree);
    // there are 3 root nodes
    assertEquals(3, tree.size());
    // there are 4 nodes in the third root node
    assertEquals(4, tree.get(2).getTeachPlanTreeNodes().size());
    // the children nodes are sorted in order
    assertEquals(1, tree.get(2).getTeachPlanTreeNodes().get(0).getOrderBy());
    assertEquals(2, tree.get(2).getTeachPlanTreeNodes().get(1).getOrderBy());
    assertEquals(3, tree.get(2).getTeachPlanTreeNodes().get(2).getOrderBy());
    assertEquals(4, tree.get(2).getTeachPlanTreeNodes().get(3).getOrderBy());

    // Exception case: find teach plans by an invalid course id
    Long invalidCourseId = 100L;
    Exception exception = assertThrows(SkillUpNowException.class, () -> {
      teachPlanService.getTeachPlanTreeByCourseId(invalidCourseId);
    });
    assertEquals("Course not found", exception.getMessage());

  }
}
