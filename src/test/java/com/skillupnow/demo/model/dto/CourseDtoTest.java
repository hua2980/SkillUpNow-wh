package com.skillupnow.demo.model.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This class contains test cases for the CourseDto.
 *
 * @author Hua Wang
 */
public class CourseDtoTest {
  private CourseDto courseDto;

  /**
   * This method sets up the test environment by initializing the CourseDto instance.
   */
  @BeforeEach
  public void setUp() {
    courseDto = new CourseDto();
  }

  /**
   * Tests the getter and setter methods of the CourseDto class.
   */
  @Test
  public void testGettersAndSetters() {
    // Normal case
    courseDto.setId(1L);
    courseDto.setName("Test Course");

    assertEquals(Long.valueOf(1), courseDto.getId());
    assertEquals("Test Course", courseDto.getName());
  }
}
