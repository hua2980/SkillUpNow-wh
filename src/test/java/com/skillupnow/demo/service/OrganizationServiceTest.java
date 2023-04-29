package com.skillupnow.demo.service;

import static org.junit.jupiter.api.Assertions.*;

import com.skillupnow.demo.exception.SkillUpNowException;
import com.skillupnow.demo.model.UserType;
import com.skillupnow.demo.model.dto.CreateUserRequest;
import com.skillupnow.demo.model.po.Organization;
import com.skillupnow.demo.model.po.User;
import com.skillupnow.demo.repository.OrganizationRepository;
import com.skillupnow.demo.repository.UserRepository;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * This class contains test cases for the OrganizationService class.
 *
 * @author Hua Wang
 */
@SpringBootTest
@Transactional
public class OrganizationServiceTest {
  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  private OrganizationRepository organizationRepository;

  @Autowired
  private OrganizationService organizationService;

  @Autowired
  private UserRepository userRepository;

  private final String existedUsername = "learn365";

  /**
   * Tests the findByUsername method of the OrganizationService class.
   */
  @Test
  void testFindByUsername() {
    // Normal case: find an existing organization
    Organization organization = organizationService.findByUsername(existedUsername);
    assertNotNull(organization);
    assertEquals(existedUsername, organization.getUsername());
    assertNull(organization.getPassword());  // password should be muted

    // Edge case: find an organization with invalid username
    SkillUpNowException exception = assertThrows(SkillUpNowException.class, () -> {
      organizationService.findByUsername("UsernameDoesNotExist");
    });
    assertEquals("Organization not found", exception.getMessage());
  }

  /**
   * test the createOrganization method of the OrganizationService class.
   */
  @Test
  void testCreateOrganization() {
    // Normal case
    CreateUserRequest createUserRequest = new CreateUserRequest();
    createUserRequest.setUsername("test_username");
    createUserRequest.setPassword("12345678");
    createUserRequest.setConfirmPassword("12345678");
    createUserRequest.setUserType(UserType.ORGANIZATION);
    User savedUser = organizationService.createOrganization(createUserRequest);
    assertNotNull(savedUser.getId());
    assertEquals(createUserRequest.getUsername(), savedUser.getUsername());
    assertEquals(createUserRequest.getUserType(), savedUser.getUserType());
    assertNull(savedUser.getPassword());  // password should be muted
    // assert that the organization saved in the database has the correct username and password
    Organization organization = organizationRepository.findByUsername(createUserRequest.getUsername());
    assertNotNull(organization);
    assertEquals(createUserRequest.getUsername(), organization.getUsername());
    assertTrue(bCryptPasswordEncoder.matches(createUserRequest.getPassword(), organization.getPassword()));

    // Edge case: username is already taken
    createUserRequest.setUsername(existedUsername);
    SkillUpNowException exception = assertThrows(SkillUpNowException.class, () -> {
      organizationService.createOrganization(createUserRequest);
    });
    assertEquals("Username already taken", exception.getMessage());
  }
}
