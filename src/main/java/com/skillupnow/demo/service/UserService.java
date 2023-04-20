package com.skillupnow.demo.service;

import com.skillupnow.demo.exception.SkillUpNowException;
import com.skillupnow.demo.model.dto.ModifyCredentialRequest;
import com.skillupnow.demo.model.po.User;
import com.skillupnow.demo.repository.UserRepository;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
  @Autowired
  UserRepository userRepository;

  @Transactional
  public User updateCredential(ModifyCredentialRequest modifyCredentialRequest,
      String currentUsername){
    if (!modifyCredentialRequest.getNewPassword().equals(modifyCredentialRequest.getConfirmPassword())){
      throw new SkillUpNowException("Password does not match");
    }
    if (!Objects.equals(modifyCredentialRequest.getUsername(), currentUsername) && userRepository.findByUsername(currentUsername)!= null){
      throw new SkillUpNowException("Username already exists");
    }
    User user = userRepository.findByUsername(currentUsername);
    user.setUsername(modifyCredentialRequest.getUsername());
    user.setPassword(modifyCredentialRequest.getNewPassword());
    User savedUser = userRepository.save(user);
    if (savedUser.getId() == null){
      throw new SkillUpNowException("User not saved");
    }
    return savedUser;
  }

}
