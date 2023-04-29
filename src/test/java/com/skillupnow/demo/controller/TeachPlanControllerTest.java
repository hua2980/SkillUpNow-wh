package com.skillupnow.demo.controller;

import com.skillupnow.demo.model.dto.TeachPlanTreeDto;
import com.skillupnow.demo.model.po.Course;
import com.skillupnow.demo.service.TeachPlanService;
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
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This class contains test cases for the TeachPlanController.
 *
 * @author Hua Wang
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TeachPlanControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TeachPlanService teachPlanService;

  /**
   * Tests the getTeachPlan method of the TeachPlanController.
   *
   * @throws Exception if an error occurs during the test
   */
  @Test
  @WithUserDetails(value = "Ocelia", userDetailsServiceBeanName = "userDetailsService")
  public void testGetTeachPlan() throws Exception {
    TeachPlanTreeDto teachPlanTreeDto1 = new TeachPlanTreeDto(1L, "Plan A", 0L, (short) 1, "A test teach plan", 1, new Course());
    TeachPlanTreeDto teachPlanTreeDto2 = new TeachPlanTreeDto(2L, "Plan B", 0L, (short) 1, "Another test teach plan", 2, new Course());

    List<TeachPlanTreeDto> teachPlanTreeDtoList = Arrays.asList(teachPlanTreeDto1, teachPlanTreeDto2);

    when(teachPlanService.getTeachPlanTreeByCourseId(1L)).thenReturn(teachPlanTreeDtoList);

    mockMvc.perform(get("/teach-plan/1/tree-nodes")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1L))
        .andExpect(jsonPath("$[0].pname").value("Plan A"))
        .andExpect(jsonPath("$[1].id").value(2L))
        .andExpect(jsonPath("$[1].pname").value("Plan B"));
  }
}

