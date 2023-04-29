package com.skillupnow.demo.service;

import com.skillupnow.demo.exception.SkillUpNowException;
import com.skillupnow.demo.model.po.Cart;
import com.skillupnow.demo.model.po.Course;
import com.skillupnow.demo.model.po.Customer;
import com.skillupnow.demo.model.po.Order;
import com.skillupnow.demo.repository.CartRepository;
import com.skillupnow.demo.repository.CustomerRepository;
import com.skillupnow.demo.repository.OrderRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class provides the main services related to managing orders in the SkillUpNow demo application.
 */
@Service
public class OrderService {
  @Autowired
  private OrderRepository orderRepository;
  @Autowired
  private CartRepository cartRepository;

  @Autowired
  private CustomerRepository customerRepository;

  /**
   * Creates a new order based on the provided cart.
   * This method is transactional, meaning that all database operations are performed within a single transaction.
   *
   * @param cart The cart from which the order will be created.
   * @return The created order.
   * @throws SkillUpNowException If the cart is empty.
   */
  @Transactional
  public Order createOrder(Cart cart) {
    // get the customer
    Customer customer = cart.getCustomer();

    // create the order
    Order order = new Order();
    List<Course> courses = cart.getCourses();
    if (courses == null) {
      throw new SkillUpNowException("Cart is empty");
    }
    order.setCourses(courses);
    order.setCustomer(customer);
    order.setTotalPrice(cart.getTotal());

    // save the order
    order = orderRepository.save(order);

    // clear the cart
    cart.setCourses(null);
    cart.initializeCart();
    cartRepository.save(cart);

    return order;
  }

  /**
   * Deletes an existing order by its ID.
   * This method is transactional, meaning that all database operations are performed within a single transaction.
   *
   * @param id The ID of the order to be deleted.
   * @throws SkillUpNowException If the order is not found.
   */
  @Transactional
  public void deleteOrder(Long id) {
    Order order;
    Optional<Order> optionalOrder = orderRepository.findById(id);
    if (!optionalOrder.isPresent()) {
      throw new SkillUpNowException("Order not found");
    } else order = optionalOrder.get();
    Customer customer = order.getCustomer();
    customer.removeOrder(order);
    customerRepository.save(customer);
    orderRepository.delete(order);
  }

  /**
   * Retrieves a list of orders associated with a given customer's username.
   *
   * @param username The username of the customer whose orders should be retrieved.
   * @return A list of orders associated with the customer.
   * @throws SkillUpNowException If the customer is not found.
   */
  public List<Order> getOrdersByUsername(String username) {
    Customer customer = customerRepository.findByUsername(username);
    if (customer == null) {
      throw new SkillUpNowException("Customer not found");
    }
    return customer.getOrders();
  }
}
