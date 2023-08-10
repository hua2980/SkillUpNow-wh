package com.skillupnow.demo.controller;

import com.google.gson.Gson;
import com.skillupnow.demo.exception.SkillUpNowException;
import com.skillupnow.demo.exception.ValidationGroups;
import com.skillupnow.demo.model.dto.ModifyCartRequest;
import com.skillupnow.demo.model.po.Cart;
import com.skillupnow.demo.service.CartService;
import com.skillupnow.demo.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * The CartController class provides a RESTful API for managing customers' cart.
 * This includes adding courses to the cart, viewing the cart, and checking out the cart.
 *
 * @author Hua Wang
 */
@RestController
@RequestMapping("/cart")
public class CartController {
  Logger logger = LoggerFactory.getLogger(CartController.class);
  Gson gson = new Gson();
  @Autowired
  CartService cartService;

  @Autowired
  OrderService orderService;

  /**
   * Adds a course to the authenticated user's cart.
   *
   * @param request A ModifyCartRequest object containing the details of the course to add.
   * @return A ResponseEntity containing the updated cart.
   */
  @PutMapping
  public ResponseEntity<Cart> addCourseToCart(@RequestBody @Validated(ValidationGroups.Update.class) ModifyCartRequest request) {
    // assert that the username in authentication should match the username in the request
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentUsername = authentication.getName();
    logger.info("addCourseToCart. req={}, user={}", gson.toJson(request), currentUsername);
    request.setUsername(currentUsername);
    Cart cart = cartService.modifyCart(request);
    ResponseEntity<Cart> response = ResponseEntity.ok().body(cart);
    return response;
  }

  /**
   * Retrieves the authenticated user's cart.
   *k
   * @return A ResponseEntity containing the user's cart.
   */
  @GetMapping
  public ResponseEntity<Cart> getCart() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentUsername = authentication.getName();
    logger.info("getCart request. currentUsername: {}", currentUsername);
    Cart cart = cartService.getCartByUsername(currentUsername);
    return ResponseEntity.ok().body(cart);
  }

  /**
   * Checks out the authenticated user's cart, creating an order if the cart is not empty.
   *
   * @return A ResponseEntity containing the user's cart.
   * @throws SkillUpNowException if the cart is empty.
   */
  @GetMapping("/checkout")
  public ResponseEntity<Cart> checkout() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentUsername = authentication.getName();
    logger.info("checkout request. currentUsername: {}", currentUsername);
    Cart cart = cartService.getCartByUsername(currentUsername);
    if (cart.getCourses().size() == 0) {
      logger.error("Cart is empty, cannot checkout. currentUsername: {}", currentUsername);
      throw new SkillUpNowException("Cart is empty");
    }
    orderService.createOrder(cart);
    ResponseEntity<Cart> response = ResponseEntity.ok().body(cart);
    return response;
  }

}
