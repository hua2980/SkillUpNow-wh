package com.skillupnow.demo.service;

import com.skillupnow.demo.model.dto.CreateUserRequest;
import com.skillupnow.demo.model.po.User;

public interface UserService {
  User createUser(CreateUserRequest createUserRequest);
}
