package com.skillupnow.demo.model.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CourseDtoTest {
  private CourseDto courseDto;

  @BeforeEach
  public void setUp() {
    courseDto = new CourseDto();
  }

  @Test
  public void testGettersAndSetters() {
    // Normal case
    courseDto.setId(1L);
    courseDto.setName("Test Course");

    assertEquals(Long.valueOf(1), courseDto.getId());
    assertEquals("Test Course", courseDto.getName());
  }
}
