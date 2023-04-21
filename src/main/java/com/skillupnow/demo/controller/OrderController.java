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

@RestController
@RequestMapping("/order")
public class OrderController {
  @Autowired
  private OrderService orderService;

  @GetMapping
  public ResponseEntity<List<Order>> getAllOrders() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    List<Order> orders = orderService.getOrdersByUsername(username);
    return ResponseEntity.ok().body(orders);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<List<Order>> deleteOrderById(@PathVariable Long id) {
    orderService.deleteOrder(id);
    return ResponseEntity.ok().build();
  }
}
