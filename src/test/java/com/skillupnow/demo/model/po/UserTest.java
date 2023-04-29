package com.skillupnow.demo.model.po;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.skillupnow.demo.model.UserType;
import com.skillupnow.demo.model.po.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This class contains test cases for the User class.
 *
 * @author Hua Wang
 */
public class UserTest {
  private User user;

  /**
   * This method sets up the test environment by initializing the User instance.
   */
  @BeforeEach
  public void setUp() {
    user = new User("Irene", "12345678", UserType.CUSTOMER);
  }

  /**
   * Tests the getter and setter methods of the User class.
   */
  @Test
  public void testGettersAndSetters() {
    // Normal case
    user.setId(1L);
    user.setUsername("NewIrene");
    user.setPassword("87654321");
    user.setUserType(UserType.ORGANIZATION);
    user.setEmail("newirene@example.com");

    assertEquals(1L, user.getId());
    assertEquals("NewIrene", user.getUsername());
    assertEquals("87654321", user.getPassword());
    assertEquals(UserType.ORGANIZATION, user.getUserType());
    assertEquals("newirene@example.com", user.getEmail());

    // Constructors
    User user2 = new User();
    assertNotNull(user2);
  }
}

