package com.skillupnow.demo.repository;

import com.skillupnow.demo.model.UserType;
import com.skillupnow.demo.model.po.User;
import java.util.Optional;
import javax.crypto.spec.OAEPParameterSpec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {
  User findByUsername(String username);

  @Query("SELECT u FROM User u WHERE u.id = ?1")
  Optional<User> findById(Long id);
}
