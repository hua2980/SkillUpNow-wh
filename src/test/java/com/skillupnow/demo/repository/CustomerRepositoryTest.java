package com.skillupnow.demo.repository;

import com.skillupnow.demo.model.UserType;
import com.skillupnow.demo.model.po.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
public class CustomerRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private CustomerRepository customerRepository;

  @Test
  public void testFindByUsername() {
    // Setup
    Customer customer = new Customer();
    customer.setUsername("testuser");
    customer.setPassword("testpassword");
    customer.setUserType(UserType.CUSTOMER);
    entityManager.persist(customer);
    entityManager.flush();

    // Test case: findByUsername returns a Customer
    Customer foundCustomer = customerRepository.findByUsername("testuser");
    assertNotNull(foundCustomer);
    assertEquals("testuser", foundCustomer.getUsername());
    assertEquals("testpassword", foundCustomer.getPassword());
    assertEquals(UserType.CUSTOMER, foundCustomer.getUserType());

    // Test case: findByUsername returns null for non-existent username
    Customer nonExistentCustomer = customerRepository.findByUsername("nonexistentuser");
    assertNull(nonExistentCustomer);
  }
}
