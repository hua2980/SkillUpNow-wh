package com.skillupnow.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.skillupnow.demo.model.dto.ModifyCartRequest;
import com.skillupnow.demo.model.po.Cart;
import com.skillupnow.demo.model.po.Course;
import com.skillupnow.demo.model.po.Customer;
import com.skillupnow.demo.model.po.Order;
import com.skillupnow.demo.model.po.Organization;
import com.skillupnow.demo.security.UserDetailServiceImpl;
import com.skillupnow.demo.service.CartService;
import com.skillupnow.demo.service.OrderService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * This class contains test cases for the CartController.
 *
 * @author Hua Wang
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CartControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CartService cartService;

  @MockBean
  private OrderService orderService;

  private Cart mockCart;

  /**
   * Sets up test data before each test case.
   */
  @BeforeEach
  public void setup() {
    mockCart = new Cart();
    mockCart.setId(1L);
    mockCart.setTotal(BigDecimal.valueOf(10.00));
    mockCart.setCustomer(new Customer());

    Course course1 = new Course();
    course1.setId(1L);
    course1.setName("course1");
    course1.setDescription("course1 description");
    course1.setPrice(BigDecimal.valueOf(10.00));

    Organization organization = new Organization();
    organization.setId(1L);
    organization.setOrganizationName("org1");
    organization.setId(1L);
    course1.setOrganization(organization);

    List<Course> courses = new ArrayList<>();
    courses.add(course1);

    mockCart.setCourses(courses);
    mockCart.getCustomer().setId(1L);
    mockCart.getCustomer().setUsername("Ocelia");
  }

  /**
   * Tests the addCourseToCart method of the CartController.
   *
   * @throws Exception if an error occurs during the test
   */
  @Test
  @WithUserDetails(value = "Ocelia", userDetailsServiceBeanName = "userDetailsService")
  public void testAddCourseToCart() throws Exception {
    when(cartService.modifyCart(any(ModifyCartRequest.class))).thenReturn(mockCart);

    mockMvc.perform(put("/cart")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"username\":\"Ocelia\",\"courseId\":1,\"delete\":0}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.total").value(10.00));

    verify(cartService, times(1)).modifyCart(any(ModifyCartRequest.class));

  }

  /**
   * Tests the getCart method of the CartController.
   *
   * @throws Exception if an error occurs during the test
   */
  @Test
  @WithUserDetails(value = "Ocelia", userDetailsServiceBeanName = "userDetailsService")
  public void testGetCart() throws Exception {
    when(cartService.getCartByUsername("Ocelia")).thenReturn(mockCart);

    mockMvc.perform(get("/cart"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.total").value(10.00))
        .andExpect(jsonPath("$.id").value(1L));

    verify(cartService, times(1)).getCartByUsername("Ocelia");
  }

  /**
   * Tests the checkout method of the CartController.
   *
   * @throws Exception if an error occurs during the test
   */
  @Test
  @WithUserDetails(value = "Ocelia", userDetailsServiceBeanName = "userDetailsService")
  public void testCheckout() throws Exception {

    when(cartService.getCartByUsername("Ocelia")).thenReturn(mockCart);
    Order order = new Order();

    when(orderService.createOrder(any(Cart.class))).thenReturn(order);

    mockMvc.perform(get("/cart/checkout"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.total").value(10.00))
        .andExpect(jsonPath("$.id").value(1L));

    verify(cartService, times(1)).getCartByUsername("Ocelia");
    verify(orderService, times(1)).createOrder(mockCart);
  }

  /**
   * Tests the checkout method with an empty cart.
   *
   * @throws Exception if an error occurs during the test
   */
  @Test
  @WithUserDetails(value = "Ocelia", userDetailsServiceBeanName = "userDetailsService")
  public void testCheckoutWithEmptyCart() throws Exception {
    mockCart.setCourses(new ArrayList<>());
    mockCart.initializeCart();

    when(cartService.getCartByUsername("Ocelia")).thenReturn(mockCart);

    mockMvc.perform(get("/cart/checkout"))
        .andExpect(status().isInternalServerError())
        .andExpect(jsonPath("$.errMessage").value("Cart is empty"));
  }
}
