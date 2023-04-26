package com.skillupnow.demo.model.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.skillupnow.demo.exception.ValidationGroups;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ModifyCustomerRequestTest {
  private Validator validator;
  private ModifyCustomerRequest modifyCustomerRequest;

  String firstname = "Irene";
  String lastname = "Cai";
  String email = "irene@gmail.com";
  String headline = "Software Engineer";

  @BeforeEach
  public void setUp() {
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    modifyCustomerRequest = new ModifyCustomerRequest(firstname, lastname, email, headline);
  }

  @Test
  public void testConstructor() {
    assertEquals(firstname, modifyCustomerRequest.getFirstname());
    assertEquals(lastname, modifyCustomerRequest.getLastname());
    assertEquals(email, modifyCustomerRequest.getEmail());
    assertEquals(headline, modifyCustomerRequest.getHeadline());
  }

  @Test
  public void testGettersAndSetters() {
    String firstname = "Hua";
    String lastname = "Wang";
    String email = "wang@gmail.com";
    String headline = "Java Developer";

    modifyCustomerRequest.setFirstname(firstname);
    modifyCustomerRequest.setLastname(lastname);
    modifyCustomerRequest.setEmail(email);
    modifyCustomerRequest.setHeadline(headline);

    assertEquals(firstname, modifyCustomerRequest.getFirstname());
    assertEquals(lastname, modifyCustomerRequest.getLastname());
    assertEquals(email, modifyCustomerRequest.getEmail());
    assertEquals(headline, modifyCustomerRequest.getHeadline());
  }

  @Test
  public void testValidations() {
    modifyCustomerRequest.setFirstname("");
    modifyCustomerRequest.setLastname("");
    modifyCustomerRequest.setEmail("invalid-email");

    Set<ConstraintViolation<ModifyCustomerRequest>> violations = validator.validate(modifyCustomerRequest, ValidationGroups.Update.class);

    assertFalse(violations.isEmpty());
    assertEquals(3, violations.size());
    assertTrue(violations.stream().anyMatch(v -> "firstname must not be empty".equals(v.getMessage())));
    assertTrue(violations.stream().anyMatch(v -> "lastname must not be empty".equals(v.getMessage())));
    assertTrue(violations.stream().anyMatch(v -> "email must be valid".equals(v.getMessage())));

    modifyCustomerRequest.setFirstname(firstname);
    modifyCustomerRequest.setLastname(lastname);
    modifyCustomerRequest.setEmail(email);

    violations = validator.validate(modifyCustomerRequest, ValidationGroups.Update.class);

    assertTrue(violations.isEmpty());
  }
}
