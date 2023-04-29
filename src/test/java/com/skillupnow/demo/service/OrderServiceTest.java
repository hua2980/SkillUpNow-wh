package com.skillupnow.demo.service;

import static org.junit.jupiter.api.Assertions.*;

import com.skillupnow.demo.exception.SkillUpNowException;
import com.skillupnow.demo.model.dto.ModifyCartRequest;
import com.skillupnow.demo.model.po.Cart;
import com.skillupnow.demo.model.po.Customer;
import com.skillupnow.demo.model.po.Order;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class contains test cases for the OrderService class.
 *
 * @auther Hua Wang
 */
@SpringBootTest
@Transactional
public class OrderServiceTest {

  @Autowired
  private OrderService orderService;

  @Autowired
  private CartService cartService;

  @Autowired
  private CustomerService customerService;

  private final String validUsername = "hua0837";
  private final Long validCourseId = 1L;

  /**
   * Tests the createOrder method of the OrderService class.
   */
  @Test
  public void testCreateOrder() {
    ModifyCartRequest request = new ModifyCartRequest(validUsername, validCourseId, 0);
    Cart cart = cartService.modifyCart(request);
    Customer customer = customerService.findByUsername(validUsername);
    int originalOrderSize = customer.getOrders().size();

    // Normal case
    Order order = orderService.createOrder(cart);
    assertNotNull(order);
    assertNotNull(order.getId());  // having id means it has been successfully saved into the database
    assertEquals(1, order.getCourses().size());
    assertEquals(16.99, order.getTotalPrice().floatValue(), 0.0001);
    assertEquals(validUsername, order.getCustomer().getUsername());
    // cart should be empty after checking out
    assertNull(cart.getCourses());

    // Edge case: creating an order with an empty cart should throw an exception
    Exception exception = assertThrows(SkillUpNowException.class, () -> orderService.createOrder(cart));
    assertEquals("Cart is empty", exception.getMessage());
  }

  /**
   * Tests the deleteOrder method of the OrderService class.
   */
  @Test
  void testDeleteOrder() {
    ModifyCartRequest request = new ModifyCartRequest(validUsername, validCourseId, 0);
    Cart cart = cartService.modifyCart(request);
    Order order = orderService.createOrder(cart);
    Customer customer = customerService.findByUsername(validUsername);
    int originalOrderSize = customer.getOrders().size();

    // Normal case
    orderService.deleteOrder(order.getId());
    assertEquals(originalOrderSize-1, customer.getOrders().size());

    // Edge case: deleting an order that does not exist should throw an exception
    Exception exception = assertThrows(SkillUpNowException.class, () -> orderService.deleteOrder(order.getId()));
    assertEquals("Order not found", exception.getMessage());
  }

  /**
   * Tests the getOrdersByUsername method of the OrderService class.
   */
  @Test
  void testGetOrdersByUsername() {
    // Normal case
    assertEquals(1, orderService.getOrdersByUsername(validUsername).size());

    // Edge case: username does not exist
    Exception exception = assertThrows(SkillUpNowException.class, () -> orderService.getOrdersByUsername("InvalidUser"));
    assertEquals("Customer not found", exception.getMessage());
  }
}
