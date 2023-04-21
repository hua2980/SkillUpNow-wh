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

@SpringBootTest
public class OrderServiceTest {

  @Autowired
  private OrderService orderService;

  @Autowired
  private CartService cartService;

  @Autowired
  private CustomerService customerService;

  @Test
  @Transactional
  public void testSaveOrder() {
    ModifyCartRequest request = new ModifyCartRequest("Irene", 1L, 0);
    cartService.modifyCart(request);
    Order order = orderService.createOrder(cartService.getCartByUsername("Irene"));

    Cart cart = cartService.getCartByUsername("Irene");
    Customer customer = customerService.findByUsername("Irene");

    assertNull(cart.getCourses());
    assertEquals(0.0, cart.getTotal().floatValue(), 0.0001);
    assertEquals(1, customer.getOrders().size());
    assertEquals(16.99, customer.getOrders().get(0).getTotalPrice().floatValue(), 0.0001);
    assertEquals(1L, order.getCourses().get(0).getId());
    assertEquals(16.99, order.getTotalPrice().floatValue(), 0.0001);
  }

  @Test
  @Transactional
  void testDeleteOrder() {
    ModifyCartRequest request = new ModifyCartRequest("Irene", 1L, 0);
    cartService.modifyCart(request);
    Order order = orderService.createOrder(cartService.getCartByUsername("Irene"));

    orderService.deleteOrder(order.getId());

    Customer customer = customerService.findByUsername("Irene");
    assertEquals(0, customer.getOrders().size());
  }
}
