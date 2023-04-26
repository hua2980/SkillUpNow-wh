package com.skillupnow.demo.model.po;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.skillupnow.demo.model.UserType;
import com.skillupnow.demo.model.po.Cart;
import com.skillupnow.demo.model.po.Customer;
import com.skillupnow.demo.model.po.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CustomerTest {
  private Customer customer;

  private Customer customer2;

  private Cart cart;

  @BeforeEach
  public void setUp() {
    customer = new Customer("Irene", "12345678", UserType.CUSTOMER);

    cart = new Cart();
    cart.setId(1L);
    customer.setCart(cart);
    customer2 = new Customer("John", "password", UserType.CUSTOMER, cart);
  }

  @Test
  public void testGettersAndSetters() {
    customer.setFirstname("Irene");
    customer.setLastname("Doe");
    customer.setHeadline("Java Developer");
    customer.setCart(cart);

    assertEquals("Irene", customer.getUsername());
    assertEquals("12345678", customer.getPassword());
    assertEquals("Irene", customer.getFirstname());
    assertEquals("Doe", customer.getLastname());
    assertEquals("Java Developer", customer.getHeadline());
    assertEquals(cart, customer.getCart());

    customer.setUsername("Irene2");
    customer.setPassword("87654321");
    customer.setUserType(UserType.ORGANIZATION);
    assertEquals("Irene2", customer.getUsername());
    assertEquals("87654321", customer.getPassword());
    assertEquals(UserType.ORGANIZATION, customer.getUserType());

    assertEquals(cart, customer2.getCart());
  }

  @Test
  public void testAddAndRemoveOrder() {
    Order order1 = new Order();
    order1.setId(1L);

    Order order2 = new Order();
    order2.setId(2L);

    customer.addOrder(order1);
    assertEquals(1, customer.getOrders().size());

    customer.addOrder(order2);
    assertEquals(2, customer.getOrders().size());

    customer.removeOrder(order1);
    assertEquals(1, customer.getOrders().size());
  }
}
