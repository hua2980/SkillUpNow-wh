package com.skillupnow.demo.model.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.skillupnow.demo.exception.ValidationGroups;
import com.skillupnow.demo.model.UserType;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This class contains test cases for the ModifyCredentialRequest class.
 *
 * @author Hua Wang
 */
public class ModifyCredentialRequestTest {
  private Validator validator;
  private ModifyCredentialRequest modifyCredentialRequest;

  /**
   * This method sets up the test environment by initializing the ModifyCredentialRequest and Validator instances.
   */
  @BeforeEach
  public void setUp() {
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    modifyCredentialRequest = new ModifyCredentialRequest();
  }

  /**
   * Tests the getter and setter methods of the ModifyCredentialRequest class.
   */
  @Test
  public void testGettersAndSetters() {
    String username = "testUser";
    String password = "password123";
    modifyCredentialRequest.setUsername(username);
    modifyCredentialRequest.setNewPassword(password);
    modifyCredentialRequest.setConfirmPassword(password);

    assertEquals(username, modifyCredentialRequest.getUsername());
    assertEquals(password, modifyCredentialRequest.getNewPassword());
    assertEquals(password, modifyCredentialRequest.getConfirmPassword());
  }

  /**
   * Tests the validation methods of the ModifyCredentialRequest class.
   */
  @Test
  public void testValidationNormalCase(){
    // Normal case: all fields are not empty; password is in the valid format; password and confirm password match
    modifyCredentialRequest.setUsername("TestUser");
    modifyCredentialRequest.setNewPassword("password123");
    modifyCredentialRequest.setConfirmPassword("password123");

    Set<ConstraintViolation<ModifyCredentialRequest>> violations = validator.validate(modifyCredentialRequest, ValidationGroups.Update.class);
    assertTrue(violations.isEmpty());
  }

  /**
   * Tests the validation methods of the ModifyCredentialRequest class for size constraints.
   */
  @Test
  public void testSizeValidation() {
    modifyCredentialRequest.setUsername("testuser");
    modifyCredentialRequest.setConfirmPassword("short");
    modifyCredentialRequest.setNewPassword("short");

    Set<ConstraintViolation<ModifyCredentialRequest>> violations = validator.validate(modifyCredentialRequest, ValidationGroups.Update.class);
    ConstraintViolation<ModifyCredentialRequest> violation = violations.stream().findFirst().orElse(null);
    assertFalse(violations.isEmpty());
    assertEquals(1, violations.size());
    assertEquals("password must be between 7 and 20 characters", violation.getMessage());

    modifyCredentialRequest.setNewPassword("ThisPasswordIsWayTooLongAndShouldNotBeAccepted");
    modifyCredentialRequest.setConfirmPassword("ThisPasswordIsWayTooLongAndShouldNotBeAccepted");
    violations = validator.validate(modifyCredentialRequest, ValidationGroups.Update.class);
    violation = violations.stream().findFirst().orElse(null);
    assertFalse(violations.isEmpty());
    assertEquals("password must be between 7 and 20 characters", violation.getMessage());
  }

  /**
   * Tests the validation methods of the ModifyCredentialRequest class for not empty constraints.
   */
  @Test
  public void testNotEmptyValidations() {
    modifyCredentialRequest.setUsername("");
    modifyCredentialRequest.setNewPassword("");
    modifyCredentialRequest.setConfirmPassword("");

    Set<ConstraintViolation<ModifyCredentialRequest>> violations = validator.validate(modifyCredentialRequest, ValidationGroups.Update.class);

    assertFalse(violations.isEmpty());
    assertEquals(4, violations.size());
    assertTrue(violations.stream().anyMatch(v -> "username must not be empty".equals(v.getMessage())));
    assertTrue(violations.stream().anyMatch(v -> "password is required".equals(v.getMessage())));
    assertTrue(violations.stream().anyMatch(v -> "confirm password is required".equals(v.getMessage())));
    assertTrue(violations.stream().anyMatch(v -> "password must be between 7 and 20 characters".equals(v.getMessage())));

    String username = "testUser";
    String password = "password123";

    modifyCredentialRequest.setUsername(username);
    modifyCredentialRequest.setNewPassword(password);
    modifyCredentialRequest.setConfirmPassword(password);

    violations = validator.validate(modifyCredentialRequest, ValidationGroups.Update.class);
    assertTrue(violations.isEmpty());
  }

  /**
   * Tests the isPasswordMatch method of the ModifyCredentialRequest class.
   */
  @Test
  void testIsPasswordMatch() {
    String username = "testUser";
    String password = "password123";
    String confirmPassword = "password124";
    modifyCredentialRequest.setUsername(username);
    modifyCredentialRequest.setNewPassword(password);
    modifyCredentialRequest.setConfirmPassword(confirmPassword);

    assertFalse(modifyCredentialRequest.isPasswordMatch());

    confirmPassword = "password123";
    modifyCredentialRequest.setConfirmPassword(confirmPassword);
    assertTrue(modifyCredentialRequest.isPasswordMatch());


    assertTrue(modifyCredentialRequest.isPasswordMatch());
  }
}
