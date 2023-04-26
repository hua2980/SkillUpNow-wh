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

public class ModifyCartRequestTest {
  private Validator validator;
  private ModifyCartRequest modifyCartRequest;

  @BeforeEach
  public void setUp() {
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    modifyCartRequest = new ModifyCartRequest();
  }

  @Test
  public void testGettersAndSetters() {
    modifyCartRequest.setUsername("testUser");
    modifyCartRequest.setCourseId(1L);
    modifyCartRequest.setDelete(0);

    assertEquals("testUser", modifyCartRequest.getUsername());
    assertEquals(1L, modifyCartRequest.getCourseId());
    assertEquals(0, modifyCartRequest.getDelete());
  }

  @Test
  public void testConstructor() {
    String username = "testUser";
    long courseId = 1L;
    int delete = 0;

    ModifyCartRequest modifyCartRequest = new ModifyCartRequest(username, courseId, delete);

    assertEquals(username, modifyCartRequest.getUsername());
    assertEquals(courseId, modifyCartRequest.getCourseId());
    assertEquals(delete, modifyCartRequest.getDelete());
  }

  @Test
  public void testValidations() {
    modifyCartRequest.setUsername("");
    modifyCartRequest.setDelete(2);

    Set<ConstraintViolation<ModifyCartRequest>> violations = validator.validate(modifyCartRequest, ValidationGroups.Update.class);

    assertFalse(violations.isEmpty());
    assertEquals(2, violations.size());
    assertTrue(violations.stream().anyMatch(v -> "Username is required".equals(v.getMessage())));
    assertTrue(violations.stream().anyMatch(v -> "Delete must be 0 or 1".equals(v.getMessage())));

    modifyCartRequest.setUsername("testUser");
    modifyCartRequest.setCourseId(1L);
    modifyCartRequest.setDelete(0);

    violations = validator.validate(modifyCartRequest, ValidationGroups.Update.class);
    assertTrue(violations.isEmpty());
  }
}
