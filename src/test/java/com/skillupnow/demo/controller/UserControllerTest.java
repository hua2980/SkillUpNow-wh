package com.skillupnow.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillupnow.demo.model.UserType;
import com.skillupnow.demo.model.dto.CreateUserRequest;
import com.skillupnow.demo.model.dto.ModifyCredentialRequest;
import com.skillupnow.demo.model.dto.ModifyCustomerRequest;
import com.skillupnow.demo.model.po.Cart;
import com.skillupnow.demo.model.po.Course;
import com.skillupnow.demo.model.po.Customer;
import com.skillupnow.demo.model.po.Organization;
import com.skillupnow.demo.model.po.User;
import com.skillupnow.demo.security.UserDetailServiceImpl;
import com.skillupnow.demo.service.CustomerService;
import com.skillupnow.demo.service.OrganizationService;
import com.skillupnow.demo.service.UserService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * This class contains test cases for the UserController.
 *
 * @author Hua Wang
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CustomerService customerService;

  @MockBean
  private OrganizationService organizationService;

  @MockBean
  private UserService userService;

  private User testUser;

  /**
   * Sets up test data before each test case.
   */
  @BeforeEach
  public void setup() {
    testUser = new User();
    testUser.setUsername("Ocelia");
    testUser.setPassword("password123");
    testUser.setEmail("a@gmail.com");
    testUser.setId(1L);
  }

  /**
   * Tests the createCustomer method of the UserController.
   *
   * @throws Exception if an error occurs during the test
   */
  @Test
  @WithUserDetails(value = "Ocelia", userDetailsServiceBeanName = "userDetailsService")
  public void createCustomer() throws Exception {
    testUser.setUserType(UserType.CUSTOMER);
    when(customerService.createCustomer(any(CreateUserRequest.class))).thenReturn(testUser);

    String createUserJson = "{\"userType\":\"CUSTOMER\", \"username\":\"Ocelia\", \"password\":\"password123\", \"confirmPassword\":\"password123\"}";

    mockMvc.perform(post("/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(createUserJson))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username").value("Ocelia"))
        .andExpect(jsonPath("$.password").value("password123"));
  }

  /**
   * Tests the createOrganization method of the UserController.
   *
   * @throws Exception if an error occurs during the test
   */
  @Test
  @WithUserDetails(value = "Ocelia", userDetailsServiceBeanName = "userDetailsService")
  public void createOrganization() throws Exception {
    testUser.setUserType(UserType.ORGANIZATION);
    when(organizationService.createOrganization(any(CreateUserRequest.class))).thenReturn(testUser);

    String createUserJson = "{\"userType\":\"ORGANIZATION\", \"username\":\"Ocelia\", \"password\":\"password123\", \"confirmPassword\":\"password123\"}";

    mockMvc.perform(post("/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(createUserJson))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username").value("Ocelia"))
        .andExpect(jsonPath("$.password").value("password123"));
  }

  /**
   * Tests the getCustomerInfo method of the UserController.
   *
   * @throws Exception if an error occurs during the test
   */
  @Test
  @WithUserDetails(value = "Ocelia", userDetailsServiceBeanName = "userDetailsService")
  public void getCustomerInfo() throws Exception {
    Customer testCustomer = new Customer();
    testCustomer.setUsername("Ocelia");
    testCustomer.setFirstname("Ocelia");
    testCustomer.setLastname("Zhang");
    testCustomer.setCart(new Cart());
    testCustomer.setOrders(new ArrayList<>());
    testCustomer.setUserType(UserType.CUSTOMER);
    testCustomer.setId(1L);
    testCustomer.setPassword("password123");

    when(customerService.findByUsername("Ocelia")).thenReturn(testCustomer);

    mockMvc.perform(get("/customer"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username").value("Ocelia"))
        .andExpect(jsonPath("$.firstname").value("Ocelia"))
        .andExpect(jsonPath("$.lastname").value("Zhang"));
  }

  /**
   * Tests the updateCustomerInfo method of the UserController.
   *
   * @throws Exception if an error occurs during the test
   */
  @Test
  @WithUserDetails(value = "Ocelia", userDetailsServiceBeanName = "userDetailsService")
  public void updateCustomerInfo() throws Exception {
    ModifyCustomerRequest modifyCustomerRequest = new ModifyCustomerRequest("Ocelia", "Huang", "abc@gmail.com", "student");

    doNothing().when(customerService).updateCustomer(any(ModifyCustomerRequest.class), eq("CustUser"));

    String modifyCustomerJson = "{\"firstname\":\"Ocelia\", \"lastname\":\"Huang\", \"email\":\"abc@gmail.com\", \"headline\":\"student\"}";

    mockMvc.perform(put("/customer")
            .contentType(MediaType.APPLICATION_JSON)
            .content(modifyCustomerJson))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.firstname").value("Ocelia"))
        .andExpect(jsonPath("$.lastname").value("Huang"))
        .andExpect(jsonPath("$.email").value("abc@gmail.com"))
        .andExpect(jsonPath("$.headline").value("student"));
  }

  /**
   * Tests the updateCredential method of the UserController.
   *
   * @throws Exception if an error occurs during the test
   */
  @Test
  @WithUserDetails(value = "Ocelia", userDetailsServiceBeanName = "userDetailsService")
  public void updateCredential() throws Exception {
    ModifyCredentialRequest modifyCredentialRequest = new ModifyCredentialRequest();
    modifyCredentialRequest.setUsername("Ocelia1111");
    modifyCredentialRequest.setNewPassword("newPassword");
    modifyCredentialRequest.setConfirmPassword("newPassword");

    doNothing().when(userService).updateCredential(any(ModifyCredentialRequest.class), eq("Ocelia"));

    String modifyCredentialJson = "{\"username\":\"Ocelia1111\", \"newPassword\":\"newPassword\", \"confirmPassword\":\"newPassword\"}";

    mockMvc.perform(put("/user/credential")
            .contentType(MediaType.APPLICATION_JSON)
            .content(modifyCredentialJson))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username").value("Ocelia1111"))
        .andExpect(jsonPath("$.newPassword").doesNotExist())
        .andExpect(jsonPath("$.confirmPassword").doesNotExist());
  }



}
