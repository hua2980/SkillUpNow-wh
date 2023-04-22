package com.skillupnow.demo.service;

import com.skillupnow.demo.exception.SkillUpNowException;
import com.skillupnow.demo.model.dto.ModifyCartRequest;
import com.skillupnow.demo.model.po.Cart;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration test for CartService
 */
@SpringBootTest
@Transactional
public class CartServiceTest {
  @Autowired
  private CartService cartService;

  private final String username = "Irene";
  private final String invalidUsername = "InvalidUser";
  private final Long validCourseId = 1L;
  private final Long invalidCourseId = 999L;

  @Test
  public void testModifyCartInvalidInputs(){
    // Edge case: adding a non-existing course should throw an exception
    ModifyCartRequest invalidCourseRequest = new ModifyCartRequest(username, invalidCourseId, 0);
    Exception exception = assertThrows(SkillUpNowException.class, () -> cartService.modifyCart(invalidCourseRequest));
    assertEquals("Course not found", exception.getMessage());

    // Edge case: adding to a non-existing user should throw an exception
    ModifyCartRequest invalidUserRequest = new ModifyCartRequest(invalidUsername, validCourseId, 0);
    exception = assertThrows(SkillUpNowException.class, () -> cartService.modifyCart(invalidUserRequest));
    assertEquals("Customer not found", exception.getMessage());
  }

  @Test
  public void testModifyCartAddingCourse() {
    // Normal case
    ModifyCartRequest request = new ModifyCartRequest(username, validCourseId, 0);
    Cart cart = cartService.modifyCart(request);
    assertEquals(1, cart.getCourses().size());
    assertEquals(16.99, cart.getTotal().floatValue(), 0.0001);

    // Edge case: adding the same course again should throw exception
    Exception exception = assertThrows(SkillUpNowException.class, () -> cartService.modifyCart(request));
    assertEquals("The course is already in your cart", exception.getMessage());
  }

  @Test
  void testModifyCartRemoveCourse() {
    // Add course to cart first
    ModifyCartRequest request = new ModifyCartRequest(username, validCourseId, 0);
    Cart cartAdd = cartService.modifyCart(request);
    assertEquals(1, cartAdd.getCourses().size());
    assertEquals(16.99, cartAdd.getTotal().floatValue(), 0.0001);

    // Normal case: removing the course
    request.setDelete(1);
    Cart cartRemove = cartService.modifyCart(request);
    assertEquals(0, cartRemove.getCourses().size());
    assertEquals(0.0, cartRemove.getTotal().floatValue(), 0.0001);

    // Edge case: removing the same course again should throw exception
    Exception exception = assertThrows(SkillUpNowException.class, () -> cartService.modifyCart(request));
    assertEquals("Course not in the cart", exception.getMessage());
  }

  @Test
  void testGetCartByUsername() {
    // add to cart first
    ModifyCartRequest request = new ModifyCartRequest(username, validCourseId, 0);
    cartService.modifyCart(request);

    // Normal case
    Cart cart = cartService.getCartByUsername(username);
    assertEquals(1, cart.getCourses().size());
    assertEquals(16.99, cart.getTotal().floatValue(), 0.0001);
    assertEquals(19.99, cart.getOriginalTotal().floatValue(), 0.0001);
    assertEquals(1L, cart.getCourses().get(0).getId());

    // Edge case: getting cart for a non-existing user should throw exception
    Exception exception = assertThrows(SkillUpNowException.class, () -> cartService.getCartByUsername(invalidUsername));
    assertEquals("Customer not found", exception.getMessage());
  }

}
