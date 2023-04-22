package com.skillupnow.demo.service;

import static org.junit.jupiter.api.Assertions.*;

import com.skillupnow.demo.exception.SkillUpNowException;
import com.skillupnow.demo.model.UserType;
import com.skillupnow.demo.model.dto.CreateUserRequest;
import com.skillupnow.demo.model.dto.ModifyCustomerRequest;
import com.skillupnow.demo.model.po.Cart;
import com.skillupnow.demo.model.po.Customer;
import com.skillupnow.demo.model.po.User;
import com.skillupnow.demo.repository.CustomerRepository;
import com.skillupnow.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class CustomerServiceTest {

  @Autowired
  CustomerService customerService;

  private final String newUsername = "NoSuchCustomer";
  private final String existedUsername = "Rachel";

  @Test
  void testFindByUsername() {
    // Normal case
    Customer customer = customerService.findByUsername(existedUsername);
    assertNotNull(customer);
    assertEquals(3, customer.getId());
    assertEquals(existedUsername, customer.getUsername());
    assertNull(customer.getPassword());  // the password in the returning result should be muted!

    // Edge case: customer not found
    Exception exception = assertThrows(SkillUpNowException.class, () -> customerService.findByUsername(newUsername));
    assertEquals("Customer not found", exception.getMessage());
  }

  @Test
  public void testCreateCustomer() {
    // Normal case
    String password = "A@123456";
    CreateUserRequest createUserRequest = new CreateUserRequest(UserType.CUSTOMER, password, newUsername, password);
    User createdUser = customerService.createCustomer(createUserRequest);
    // check if the returned object is correct
    assertNotNull(createdUser);
    assertEquals(4, createdUser.getId());
    assertEquals(newUsername, createdUser.getUsername());
    assertEquals(UserType.CUSTOMER, createdUser.getUserType());
    assertNull(createdUser.getPassword());  // the password in the returning result should be muted!

    // check if the user is saved in database (so the user can be retrieved)
    Customer customer = customerService.findByUsername(newUsername);
    assertEquals(4, customer.getId());
    assertNotNull(customer.getCart());

    // Edge case: username already exists
    Exception exception = assertThrows(SkillUpNowException.class, () -> customerService.createCustomer(createUserRequest));
    assertEquals("Username already taken", exception.getMessage());
  }

  @Test
  public void testUpdateCustomer() {
    // Normal case
    ModifyCustomerRequest request = new ModifyCustomerRequest("Hua", "Wang", "abc@gmail.com", "Graduate Student of Computer Science");
    customerService.updateCustomer(request, existedUsername);
    // customer in the database should be updated
    Customer customer = customerService.findByUsername(existedUsername);
    assertEquals("Hua", customer.getFirstname());
    assertEquals("Wang", customer.getLastname());
    assertEquals("abc@gmail.com", customer.getEmail());
    assertEquals("Graduate Student of Computer Science", customer.getHeadline());

    // Edge case: customer not found
    Exception exception = assertThrows(SkillUpNowException.class, () -> customerService.updateCustomer(
        request, newUsername));
    assertEquals("Customer not found", exception.getMessage());
  }
}
