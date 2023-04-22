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

@Service
public class OrderService {
  @Autowired
  private OrderRepository orderRepository;
  @Autowired
  private CartRepository cartRepository;

  @Autowired
  private CustomerRepository customerRepository;

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

  public List<Order> getOrdersByUsername(String username) {
    Customer customer = customerRepository.findByUsername(username);
    if (customer == null) {
      throw new SkillUpNowException("Customer not found");
    }
    return customer.getOrders();
  }
}
