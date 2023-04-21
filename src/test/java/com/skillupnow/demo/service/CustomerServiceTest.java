package com.skillupnow.demo.service;

import static org.junit.jupiter.api.Assertions.*;

import com.skillupnow.demo.model.UserType;
import com.skillupnow.demo.model.dto.CreateUserRequest;
import com.skillupnow.demo.model.dto.ModifyCustomerRequest;
import com.skillupnow.demo.model.po.Cart;
import com.skillupnow.demo.model.po.Customer;
import com.skillupnow.demo.model.po.User;
import com.skillupnow.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomerServiceTest {

  @Autowired
  CustomerService customerService;

  @Test
  public void testCreateCustomer() {
    CreateUserRequest createUserRequest = new CreateUserRequest(UserType.CUSTOMER, "12345678", "Test", "12345678");
    customerService.createCustomer(createUserRequest);
    Customer customer = customerService.findByUsername("Test");
    assertEquals("Test", customer.getUsername());
    Cart cart = customer.getCart();
    assertEquals(3, cart.getId());
    assertEquals(customer.getId(), cart.getCustomer().getId());
  }

  @Test
  public void testUpdateCustomer() {
    ModifyCustomerRequest request = new ModifyCustomerRequest("Hua", "Wang", "abc@gmail.com", "Graduate Student of Computer Science");
    ModifyCustomerRequest response = customerService.updateCustomer(request, "Rachel");
    assertEquals("Hua", response.getFirstname());
    assertEquals("Wang", response.getLastname());
    assertEquals("abc@gmail.com", response.getEmail());
    assertEquals("Graduate Student of Computer Science", response.getHeadline());
    Customer customer = customerService.findByUsername("Rachel");
    assertEquals("Hua", customer.getFirstname());
    assertEquals("Wang", customer.getLastname());
    assertEquals("abc@gmail.com", customer.getEmail());
    assertEquals("Graduate Student of Computer Science", customer.getHeadline());
  }
}
