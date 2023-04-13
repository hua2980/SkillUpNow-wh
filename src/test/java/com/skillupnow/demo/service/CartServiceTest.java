package com.skillupnow.demo.service;

import com.skillupnow.demo.model.dto.ModifyCartRequest;
import com.skillupnow.demo.model.po.Cart;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class CartServiceTest {
  @Autowired
  CartService cartService;

  @Transactional
  @Test
  public void testAddProductToCart() {
    ModifyCartRequest request = new ModifyCartRequest("Irene", 1L, 0);
    Cart cart = cartService.modifyCart(request);
    assertEquals(1, cart.getCourses().size());
    assertEquals(16.99, cart.getTotal().floatValue(), 0.0001);
  }

  @Transactional
  @Test
  void testGetCartByUsername() {
    ModifyCartRequest request = new ModifyCartRequest("Irene", 1L, 0);
    cartService.modifyCart(request);

    String username = "Irene";
    Cart cart = cartService.getCartByUsername(username);
    assertEquals(1, cart.getCourses().size());
    assertEquals(16.99, cart.getTotal().floatValue(), 0.0001);
  }

}
