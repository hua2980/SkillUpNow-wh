package com.skillupnow.demo.controller;

// Add necessary imports
import com.skillupnow.demo.model.po.Course;
import com.skillupnow.demo.model.po.Organization;
import com.skillupnow.demo.repository.CourseRepository;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This class contains test cases for the CourseController.
 *
 * @author Hua Wang
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CourseControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CourseRepository courseRepository;

  private Course course1;
  private Course course2;

  /**
   * Sets up test data before each test case.
   */
  @BeforeEach
  public void setup(){
    Organization organization = new Organization();
    organization.setId(1L);
    organization.setOrganizationName("SkillUp Now");


    course1 = new Course();
    course1.setId(1L);
    // Set other course1 properties
    course1.setOrganization(organization);
    course1.setCourseType("Online");
    course1.setName("Java");
    course1.setPrice(BigDecimal.valueOf(100.00));


    course2 = new Course();
    course2.setId(2L);
    // Set other course2 properties
    course2.setOrganization(organization);
    course2.setCourseType("Online");
    course2.setName("Python");
    course2.setPrice(BigDecimal.valueOf(100.00));
  }

  /**
   * Tests the listAll method of the CourseController.
   *
   * @throws Exception if an error occurs during the test
   */
  @Test
  @WithUserDetails(value = "Ocelia", userDetailsServiceBeanName = "userDetailsService")
  public void testListAll() throws Exception {
    when(courseRepository.findAll()).thenReturn(Arrays.asList(course1, course2));

    mockMvc.perform(get("/course/list"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0].id").value(1L))
        .andExpect(jsonPath("$[1].id").value(2L));

    verify(courseRepository, times(1)).findAll();
  }

  /**
   * Tests the getCourseDetails method of the CourseController.
   *
   * @throws Exception if an error occurs during the test
   */
  @Test
  @WithUserDetails(value = "Ocelia", userDetailsServiceBeanName = "userDetailsService")
  public void testGetCourseDetails() throws Exception {
    when(courseRepository.findById(1L)).thenReturn(Optional.of(course1));

    mockMvc.perform(get("/course/info/{id}", 1L))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1L));
    // Add more assertions for other course properties

    verify(courseRepository, times(1)).findById(1L);
  }
}
