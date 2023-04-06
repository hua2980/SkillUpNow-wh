package com.skillupnow.demo.repository;

import com.skillupnow.demo.model.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  User findByUsername(String username);

}
