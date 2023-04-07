package com.skillupnow.demo.repository;

import com.skillupnow.demo.model.UserType;
import com.skillupnow.demo.model.po.Customer;
import com.skillupnow.demo.model.po.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public class UserRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  @BeforeEach
  public void setUp() {
    User user = new Customer("test", "12345678", UserType.CUSTOMER);
    userRepository.save(user);
  }

  @Test
  public void test() {
    User user = userRepository.findByUsername("test");
    System.out.println(user);
  }
}
