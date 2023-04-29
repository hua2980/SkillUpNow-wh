package com.skillupnow.demo.model.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.skillupnow.demo.model.dto.TeachPlanTreeDto;
import com.skillupnow.demo.model.po.Course;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * This class contains test cases for the TeachPlanTreeDto class.
 *
 * @author Hua Wang
 */
public class TeachPlanTreeDtoTest {

  /**
   * Tests the constructor of the TeachPlanTreeDto class.
   */
  @Test
  public void testConstructor() {
    Course course = new Course();
    TeachPlanTreeDto teachPlanTreeDto = new TeachPlanTreeDto(1L, "Test Plan", 0L, (short) 1, "Test Description", 1, course);

    assertEquals(1L, teachPlanTreeDto.getId());
    assertEquals("Test Plan", teachPlanTreeDto.getPname());
    assertEquals(0L, teachPlanTreeDto.getParentId());
    assertEquals((short) 1, teachPlanTreeDto.getGrade());
    assertEquals("Test Description", teachPlanTreeDto.getDescription());
    assertEquals(1, teachPlanTreeDto.getOrderBy());
    assertEquals(course, teachPlanTreeDto.getCourse());
  }

  /**
   * Tests the getter and setter methods of the TeachPlanTreeDto class.
   */
  @Test
  public void testGettersAndSetters() {
    Course course = new Course();
    TeachPlanTreeDto teachPlanTreeDto = new TeachPlanTreeDto(1L, "Test Plan", 0L, (short) 1, "Test Description", 1, course);

    TeachPlanTreeDto childNode = new TeachPlanTreeDto(2L, "Child Plan", 1L, (short) 2, "Child Description", 2, course);
    List<TeachPlanTreeDto> nodes = Arrays.asList(childNode);

    teachPlanTreeDto.setTeachPlanTreeNodes(nodes);
    teachPlanTreeDto.setShowSubNodes(true);

    assertEquals(nodes, teachPlanTreeDto.getTeachPlanTreeNodes());
    assertTrue(teachPlanTreeDto.isShowSubNodes());
  }
}

