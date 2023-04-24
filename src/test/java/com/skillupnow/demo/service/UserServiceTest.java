package com.skillupnow.demo.service;

import com.skillupnow.demo.exception.SkillUpNowException;
import com.skillupnow.demo.model.UserType;
import com.skillupnow.demo.model.dto.ModifyCredentialRequest;
import com.skillupnow.demo.model.po.User;
import com.skillupnow.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class UserServiceTest {
  @Autowired
  private UserService userService;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Test
  void testUpdateCredential() {
    // Set up
    User user = new User();
    user.setUsername("user1");
    user.setPassword("testpassword");
    user.setUserType(UserType.CUSTOMER);
    userRepository.save(user);

    // Test case: successful update of both username and password
    ModifyCredentialRequest request = new ModifyCredentialRequest();
    request.setUsername("user2");
    request.setNewPassword("newpassword");
    request.setConfirmPassword("newpassword");
    userService.updateCredential(request, "user1");
    User updatedUser = userRepository.findByUsername("user2");
    assertNotNull(updatedUser);
    assertEquals("user2", updatedUser.getUsername());
    assertTrue(bCryptPasswordEncoder.matches("newpassword", updatedUser.getPassword()));

    // Test case: password does not match
    request.setConfirmPassword("wrongpassword");
    Exception exception = assertThrows(SkillUpNowException.class, () -> userService.updateCredential(request, "user3"));
    assertEquals("Password does not match", exception.getMessage());

    // Test case: username already exists
    request.setConfirmPassword("newpassword");
    request.setUsername("Irene");
    exception = assertThrows(SkillUpNowException.class, () -> userService.updateCredential(request, "user2"));
    assertEquals("Username already exists", exception.getMessage());

    // Test case: both username and password are the same as before
    request.setUsername("user2");
    exception = assertThrows(SkillUpNowException.class, () -> userService.updateCredential(request, "user2"));
    assertEquals("Both username and password are the same as before", exception.getMessage());
  }
}
