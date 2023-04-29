package com.skillupnow.demo.controller;

// Add necessary imports
import com.skillupnow.demo.model.po.Course;
import com.skillupnow.demo.model.po.Customer;
import com.skillupnow.demo.model.po.Order;
import com.skillupnow.demo.service.OrderService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * This class contains test cases for the OrderController.
 *
 * @author Hua Wang
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private OrderService orderService;

  private Order order1;
  private Order order2;

  /**
   * Sets up test data before each test case.
   */
  @BeforeEach
  public void setup(){

    order1 = new Order();
    order1.setId(1L);
    // Set other order1 properties
    order1.setCustomer(new Customer());
    order1.setCourses(Collections.singletonList(new Course()));
    order1.setTotalPrice(BigDecimal.valueOf(100.00));
    order1.setCreateTime(LocalDateTime.now());

    order2 = new Order();
    order2.setId(2L);
    // Set other order2 properties
    order2.setCustomer(new Customer());
    order2.setCourses(Collections.singletonList(new Course()));
    order2.setTotalPrice(BigDecimal.valueOf(200.00));
    order2.setCreateTime(LocalDateTime.now());
    when(orderService.getOrdersByUsername("Ocelia")).thenReturn(Arrays.asList(order1, order2));
  }

  /**
   * Tests the getAllOrders method of the OrderController.
   *
   * @throws Exception if an error occurs during the test
   */
  @Test
  @WithUserDetails(value = "Ocelia", userDetailsServiceBeanName = "userDetailsService")
  public void testGetAllOrders() throws Exception {
    mockMvc.perform(get("/order"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0].id").value(1L))
        .andExpect(jsonPath("$[1].id").value(2L));

    verify(orderService, times(1)).getOrdersByUsername("Ocelia");
  }

  /**
   * Tests the deleteOrderById method of the OrderController.
   *
   * @throws Exception if an error occurs during the test
   */
  @Test
  @WithUserDetails(value = "Ocelia", userDetailsServiceBeanName = "userDetailsService")
  public void testDeleteOrderById() throws Exception {
    mockMvc.perform(delete("/order/{id}", 1L))
        .andExpect(status().isOk());

    verify(orderService, times(1)).deleteOrder(1L);
  }
}
