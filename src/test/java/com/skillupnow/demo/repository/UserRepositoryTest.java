package com.skillupnow.demo.repository;
import com.skillupnow.demo.model.UserType;
import com.skillupnow.demo.model.po.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private UserRepository userRepository;

  private User user;

  @BeforeEach
  public void setup() {
    user = new User();
    user.setUsername("testuser");
    user.setPassword("testpassword");
    user.setUserType(UserType.CUSTOMER);
    entityManager.persist(user);
    entityManager.flush();
  }

  @Test
  public void testFindByUsername() {
    // Normal case: findByUsername with valid username
    User foundUser = userRepository.findByUsername("testuser");
    assertNotNull(foundUser);
    assertEquals("testuser", foundUser.getUsername());

    // Edge case: findByUsername with invalid username
    User nonExistentUser = userRepository.findByUsername("nonexistentuser");
    assertNull(nonExistentUser);
  }

  @Test
  public void testFindById() {
    // Normal case: findById with valid id
    Optional<User> foundUserOptional = userRepository.findById(user.getId());
    assertTrue(foundUserOptional.isPresent());
    User foundUser = foundUserOptional.get();
    assertEquals(user.getId(), foundUser.getId());
    assertEquals("testuser", foundUser.getUsername());

    // Edge case: findById with invalid id
    Optional<User> nonExistentUserOptional = userRepository.findById(-1L);
    assertFalse(nonExistentUserOptional.isPresent());
  }
}

