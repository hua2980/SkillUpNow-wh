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
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class contains test cases for the CustomerService class.
 *
 * @author Hua Wang
 */
@SpringBootTest
@Transactional
public class CustomerServiceTest {

  @Autowired
  CustomerService customerService;

  @Autowired
  UserRepository userRepository;

  private final String newUsername = "NoSuchCustomer";
  private final String existedUsername = "hua0837";

  /**
   * Tests the findByUsername method of the CustomerService class.
   */
  @Test
  void testFindByUsername() {
    // Normal case
    Customer customer = customerService.findByUsername(existedUsername);
    assertNotNull(customer);
    assertEquals(1, customer.getId());
    assertEquals(existedUsername, customer.getUsername());
    assertNull(customer.getPassword());  // the password in the returning result should be muted!

    // Edge case: customer not found
    Exception exception = assertThrows(SkillUpNowException.class, () -> customerService.findByUsername(newUsername));
    assertEquals("Customer not found", exception.getMessage());
  }

  /**
   * Tests the createCustomer method of the CustomerService class.
   */
  @Test
  public void testCreateCustomer() {
    // Normal case
    String password = "A@123456";
    CreateUserRequest createUserRequest = new CreateUserRequest(UserType.CUSTOMER, password, newUsername, password);
    User createdUser = customerService.createCustomer(createUserRequest);
    // check if the returned object is correct
    assertNotNull(createdUser);
    assertNotNull(createdUser.getId()); // having id means it has been successfully saved into the database
    assertEquals(newUsername, createdUser.getUsername());
    assertEquals(UserType.CUSTOMER, createdUser.getUserType());
    assertNull(createdUser.getPassword());  // the password in the returning result should be muted!

    // check if the user is saved in database (so the user can be retrieved)
    Customer customer = customerService.findByUsername(newUsername);
    assertEquals(createdUser.getId(), customer.getId());
    assertNotNull(customer.getCart());

    // Edge case: username already exists
    Exception exception = assertThrows(SkillUpNowException.class, () -> customerService.createCustomer(createUserRequest));
    assertEquals("Username already taken", exception.getMessage());
  }

  /**
   * Tests the updateCustomer method of the CustomerService class.
   */
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
