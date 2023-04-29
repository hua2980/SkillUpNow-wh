package com.skillupnow.demo.controller;

import com.skillupnow.demo.model.po.Order;
import com.skillupnow.demo.service.OrderService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The OrderController class provides a RESTful API for managing user orders.
 * This includes listing all orders for an authenticated user and deleting an order by ID.
 *
 * @author Hua Wang
 */
@RestController
@RequestMapping("/order")
public class OrderController {
  @Autowired
  private OrderService orderService;

  /**
   * Retrieves all orders for the authenticated user.
   *
   * @return A ResponseEntity containing a list of all orders for the authenticated user.
   */
  @GetMapping
  public ResponseEntity<List<Order>> getAllOrders() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    List<Order> orders = orderService.getOrdersByUsername(username);
    return ResponseEntity.ok().body(orders);
  }

  /**
   * Deletes an order by the specified ID.
   *
   * @param id The ID of the order to be deleted.
   * @return A ResponseEntity with an HTTP status representing the success or failure of the operation.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<List<Order>> deleteOrderById(@PathVariable Long id) {
    orderService.deleteOrder(id);
    return ResponseEntity.ok().build();
  }
}
