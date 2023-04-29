package com.skillupnow.demo.model.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.skillupnow.demo.exception.ValidationGroups;
import com.skillupnow.demo.exception.ValidationGroups.Insert;
import com.skillupnow.demo.model.UserType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This class contains test cases for the CreateUserRequest class.
 *
 * @author Hua Wang
 */
public class CreateUserRequestTest {
  private CreateUserRequest createUserRequest;
  private Validator validator;

  /**
   * This method sets up the test environment by initializing the CreateUserRequest and Validator instances.
   */
  @BeforeEach
  public void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
    createUserRequest = new CreateUserRequest();
  }

  /**
   * Tests the getter and setter methods of the CreateUserRequest class.
   */
  @Test
  public void testGettersAndSetters() {
    UserType userType = UserType.CUSTOMER;
    String confirmPassword = "password123";
    String username = "testUser";
    String password = "password123";

    // Normal case
    createUserRequest.setUserType(userType);
    createUserRequest.setUsername(username);
    createUserRequest.setPassword(password);
    createUserRequest.setConfirmPassword(confirmPassword);

    assertEquals(UserType.CUSTOMER, createUserRequest.getUserType());
    assertEquals(username, createUserRequest.getUsername());
    assertEquals(password, createUserRequest.getPassword());
    assertEquals(confirmPassword, createUserRequest.getConfirmPassword());

    // Constructors
    CreateUserRequest createUserRequest = new CreateUserRequest(userType, confirmPassword, username, password);

    assertEquals(userType, createUserRequest.getUserType());
    assertEquals(confirmPassword, createUserRequest.getConfirmPassword());
    assertEquals(username, createUserRequest.getUsername());
    assertEquals(password, createUserRequest.getPassword());
  }

  /**
   * Tests the normal case validation of the CreateUserRequest class.
   */
  @Test
  public void testValidationNormalCase(){
    // Normal case: all fields are not empty; password is in the valid format; password and confirm password match
    createUserRequest.setUserType(UserType.CUSTOMER);
    createUserRequest.setUsername("TestUser");
    createUserRequest.setPassword("password123");
    createUserRequest.setConfirmPassword("password123");

    Set<ConstraintViolation<CreateUserRequest>> violations = validator
        .validate(createUserRequest, ValidationGroups.Insert.class);
    assertTrue(violations.isEmpty());
  }

  /**
   * Tests the not-null validation of the CreateUserRequest class.
   */
  @Test
  public void testNotNullValidation() {
    createUserRequest.setUserType(null);
    createUserRequest.setConfirmPassword("");
    createUserRequest.setUsername("");
    createUserRequest.setPassword("");

    Set<ConstraintViolation<CreateUserRequest>> violations = validator
        .validate(createUserRequest, Insert.class);
    assertFalse(violations.isEmpty());
    assertEquals(5, violations.size());
    assertTrue(violations.stream().map(ConstraintViolation::getMessage)
        .collect(Collectors.toList()).containsAll(Arrays.asList("user type is required",
            "confirm password is required",
            "username is required",
            "password is required",
            "password must be between 7 and 20 characters")));
  }

  /**
   * Tests the not-empty validation of the CreateUserRequest class.
   */
  @Test
  public void testNotEmptyValidation() {
    createUserRequest.setUserType(UserType.CUSTOMER);
    createUserRequest.setConfirmPassword("password123");
    createUserRequest.setUsername("");
    createUserRequest.setPassword("password123");

    Set<ConstraintViolation<CreateUserRequest>> violations = validator
        .validate(createUserRequest, ValidationGroups.Insert.class);
    ConstraintViolation<CreateUserRequest> violation = violations.stream().findFirst().orElse(null);
    assertFalse(violations.isEmpty());
    assertEquals(1, violations.size());
    assertEquals("username is required", violation.getMessage());
  }

  /**
   * Tests the size validation of the CreateUserRequest class.
   */
  @Test
  public void testSizeValidation() {
    createUserRequest.setUserType(UserType.CUSTOMER);
    createUserRequest.setConfirmPassword("short");
    createUserRequest.setUsername("testuser");
    createUserRequest.setPassword("short");

    Set<ConstraintViolation<CreateUserRequest>> violations = validator
        .validate(createUserRequest, ValidationGroups.Insert.class);
    ConstraintViolation<CreateUserRequest> violation = violations.stream().findFirst().orElse(null);
    assertFalse(violations.isEmpty());
    assertEquals(1, violations.size());
    assertEquals("password must be between 7 and 20 characters", violation.getMessage());

    createUserRequest.setPassword("ThisPasswordIsWayTooLongAndShouldNotBeAccepted");
    createUserRequest.setConfirmPassword("ThisPasswordIsWayTooLongAndShouldNotBeAccepted");
    violations = validator
        .validate(createUserRequest, ValidationGroups.Insert.class);
    violation = violations.stream().findFirst().orElse(null);
    assertFalse(violations.isEmpty());
    assertEquals("password must be between 7 and 20 characters", violation.getMessage());
  }

  /**
   * Tests the password match validation of the CreateUserRequest class.
   */
  @Test
  public void testIsPasswordMatch() {
    createUserRequest.setUserType(UserType.CUSTOMER);
    createUserRequest.setPassword("password123");
    createUserRequest.setUsername("testuser");

    // Test when password and confirm password do not match
    createUserRequest.setConfirmPassword("password124");
    Set<ConstraintViolation<CreateUserRequest>> violations = validator
        .validate(createUserRequest, ValidationGroups.Insert.class);
    assertFalse(violations.isEmpty());

    ConstraintViolation<CreateUserRequest> violation = violations.stream().findFirst().orElse(null);
    assertEquals("Password and confirm password must match", violation.getMessage());
  }
}
