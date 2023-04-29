package com.skillupnow.demo.repository;

import com.skillupnow.demo.model.UserType;
import com.skillupnow.demo.model.po.User;
import java.util.Optional;
import javax.crypto.spec.OAEPParameterSpec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * UserRepository interface provides methods to interact with the User entity in the database.
 * It extends JpaRepository which provides generic CRUD operations on a repository for a specific type.
 *
 * @author Hua Wang
 */
public interface UserRepository extends JpaRepository<User, Long> {
  /**
   * Find a User entity by its username.
   *
   * @param username the username of the User entity to find
   * @return the User entity with the specified username or null if not found
   */
  User findByUsername(String username);

  /**
   * Find a User entity by its ID using a custom query.
   *
   * @param id the ID of the User entity to find
   * @return an Optional containing the User entity with the specified ID or empty if not found
   */
  @Query("SELECT u FROM User u WHERE u.id = ?1")
  Optional<User> findById(Long id);
}
