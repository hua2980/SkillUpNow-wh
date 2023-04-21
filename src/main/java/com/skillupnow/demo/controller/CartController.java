package com.skillupnow.demo.controller;

import com.skillupnow.demo.exception.SkillUpNowException;
import com.skillupnow.demo.exception.ValidationGroups;
import com.skillupnow.demo.model.dto.ModifyCartRequest;
import com.skillupnow.demo.model.po.Cart;
import com.skillupnow.demo.service.CartService;
import com.skillupnow.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {
  @Autowired
  CartService cartService;

  @Autowired
  OrderService orderService;

  @PutMapping
  public ResponseEntity<Cart> addCourseToCart(@RequestBody @Validated(ValidationGroups.Update.class) ModifyCartRequest request) {
    // assert that the username in authentication should match the username in the request
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentUsername = authentication.getName();
    request.setUsername(currentUsername);
    Cart cart = cartService.modifyCart(request);
    return ResponseEntity.ok().body(cart);
  }

  @GetMapping
  public ResponseEntity<Cart> getCart() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentUsername = authentication.getName();
    Cart cart = cartService.getCartByUsername(currentUsername);
    return ResponseEntity.ok().body(cart);
  }

  @GetMapping("/checkout")
  public ResponseEntity<Cart> checkout() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentUsername = authentication.getName();
    Cart cart = cartService.getCartByUsername(currentUsername);
    if (cart.getCourses().size() == 0) {
      throw new SkillUpNowException("Cart is empty");
    }
    orderService.createOrder(cart);
    return ResponseEntity.ok().body(cart);
  }

}
