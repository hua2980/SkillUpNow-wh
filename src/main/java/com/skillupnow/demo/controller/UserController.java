package com.skillupnow.demo.controller;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import com.auth0.jwt.JWT;
import com.skillupnow.demo.model.dto.CreateUserRequest;
import com.skillupnow.demo.model.po.User;
import com.skillupnow.demo.security.SecurityConstants;
import com.skillupnow.demo.service.Iml.CustomerService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {
  @Autowired
  private CustomerService customerService;

  @PostMapping("/user/signup")
  public ResponseEntity<User> createUser(@RequestBody CreateUserRequest createUserRequest) {
    // Exceptions thrown by UserService (either CustomerService or CompanyService)
    // will be handled by ExceptionHandler
    User user;
    if (createUserRequest.isCustomer()) {
      user = customerService.createUser(createUserRequest);
    } else {
      // TODO: create company by CompanyService
      user = null;
    }

    // add JWT token to response header
    String token = JWT.create()
        .withSubject(user.getUsername())
        .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
        .sign(HMAC512(SecurityConstants.SECRET.getBytes()));
    HttpHeaders headers = new HttpHeaders();
    headers.add(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);

    return ResponseEntity.ok().headers(headers).body(user);
  }
}
