package com.skillupnow.demo.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.skillupnow.demo.model.po.Cart;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class CartRepositoryTest {
  @Autowired
  private CartRepository cartRepository;

  @Autowired
  private TestEntityManager entityManager;

  @Test
  public void testFindById() {
    // Setup
    Cart cart = new Cart();
    entityManager.persist(cart);
    entityManager.flush();

    // Test case: findById returns a Cart
    Optional<Cart> CartOptional = cartRepository.findById(cart.getId());
    assertTrue(CartOptional.isPresent());
    Cart foundCart = CartOptional.get();
    assertEquals(cart.getId(), foundCart.getId());

    // Test case: findById returns null for non-existent id
    CartOptional = Optional.ofNullable(cartRepository.findById(-1L));
    assertFalse(CartOptional.isPresent());
  }

}
