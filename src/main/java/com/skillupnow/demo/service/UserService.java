package com.skillupnow.demo.service;

import com.skillupnow.demo.exception.SkillUpNowException;
import com.skillupnow.demo.model.dto.ModifyCredentialRequest;
import com.skillupnow.demo.model.po.User;
import com.skillupnow.demo.repository.UserRepository;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class provides services related to user management in the SkillUpNow demo application.
 */
@Service
public class UserService {

  Logger logger = LoggerFactory.getLogger(UserService.class);

  @Autowired
  UserRepository userRepository;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  /**
   * Updates a user's credentials, including username and password, based on the provided
   * ModifyCredentialRequest and the current username.
   *
   * @param modifyCredentialRequest The request containing the new username and password.
   * @param currentUsername The current username of the user to be updated.
   * @throws SkillUpNowException If the new password does not match the confirmation password,
   *                             the new username already exists, or both the new username
   *                             and password are the same as the current ones.
   */
  @Transactional
  public void updateCredential(ModifyCredentialRequest modifyCredentialRequest,
      String currentUsername){
    if (!modifyCredentialRequest.getNewPassword().equals(modifyCredentialRequest.getConfirmPassword())){
      logger.error("Failed to update user credentials. Password does not match");
      throw new SkillUpNowException("Password does not match");
    }
    if (userRepository.findByUsername(modifyCredentialRequest.getUsername())!= null && !Objects.equals(modifyCredentialRequest.getUsername(), currentUsername)){
      logger.error("Failed to update user credentials. Username already exists, username={}", modifyCredentialRequest.getUsername());
      throw new SkillUpNowException("Username already exists");
    }
    User user = userRepository.findByUsername(currentUsername);

    // Check if current username and password both matches the modification request
    if (user.getUsername().equals(modifyCredentialRequest.getUsername()) &&
        bCryptPasswordEncoder.matches(modifyCredentialRequest.getNewPassword(), user.getPassword())) {
      logger.error("Failed to update user credentials. Both username and password are the same as before");
      throw new SkillUpNowException("Both username and password are the same as before");
    }

    user.setUsername(modifyCredentialRequest.getUsername());
    user.setPassword(bCryptPasswordEncoder.encode(modifyCredentialRequest.getNewPassword()));
    userRepository.save(user);
  }

}
