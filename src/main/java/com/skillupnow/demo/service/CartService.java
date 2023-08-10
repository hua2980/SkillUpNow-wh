package com.skillupnow.demo.service;

import com.google.gson.Gson;
import com.skillupnow.demo.exception.SkillUpNowException;
import com.skillupnow.demo.model.dto.ModifyCartRequest;
import com.skillupnow.demo.model.po.Cart;
import com.skillupnow.demo.model.po.Course;
import com.skillupnow.demo.model.po.Customer;
import com.skillupnow.demo.repository.CartRepository;
import com.skillupnow.demo.repository.CourseRepository;
import com.skillupnow.demo.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class provides the main services related to managing a customer's cart in the SkillUpNow demo application.
 */
@Service
public class CartService {
  Logger logger = LoggerFactory.getLogger(CartService.class);

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private CartRepository cartRepository;

  @Autowired
  private CourseRepository courseRepository;

  /**
   * Retrieves the cart of a customer based on their username.
   *
   * @param username The username of the customer.
   * @return The customer's cart.
   * @throws SkillUpNowException If the customer is not found.
   */
  public Cart getCartByUsername(String username) throws SkillUpNowException{
    Customer customer = customerRepository.findByUsername(username);
    if (customer == null) {
      logger.error("Failed to get cart. Customer not found, username={}", username);
      throw new SkillUpNowException("Customer not found");
    }
    return customer.getCart();
  }

  /**
   * Modifies the cart of a customer by adding or removing a course, based on the provided request.
   * This method is transactional, meaning that all database operations are performed within a single transaction.
   *
   * @param request The request containing the customer's username, the course ID, and the action (add or remove).
   * @return The modified cart.
   * @throws SkillUpNowException If the customer or course is not found, or if an invalid action is requested.
   */
  @Transactional(rollbackFor = Exception.class)
  public Cart modifyCart(ModifyCartRequest request) throws SkillUpNowException {
    // get the customer and assert the customer exists
    Customer customer = customerRepository.findByUsername(request.getUsername());
    if (customer == null) {
      logger.error("Failed to modify cart. Customer not found, username={}", request.getUsername());
      throw new SkillUpNowException("Customer not found");
    }

    // get the course and assert the course exists
    Course course = courseRepository.findById(request.getCourseId()).orElse(null);
    if (course == null) {
      logger.error("Failed to modify cart. Course not found, courseId={}, username={}", request.getCourseId(), request.getUsername());
      throw new SkillUpNowException("Course not found");
    }

    // get the cart
    Cart cart = customer.getCart();
    // if is deletion, remove the course from the cart
    if (request.getDelete() == 1){
      // course should be in the cart
      if (!cart.getCourses().contains(course)) {
        logger.error("Failed to modify cart. Course not in the cart, courseId={}, username={}", request.getCourseId(), request.getUsername());
        throw new SkillUpNowException("Course not in the cart");
      }
      cart.removeCourse(course);
    } else {
      // if is addition, add the course to the cart;
      // throw exception if the course is already in the cart
      if (cart.getCourses().contains(course)) {
        logger.error("Failed to modify cart. Course already in the cart, courseId={}, username={}", request.getCourseId(), request.getUsername());
        throw new SkillUpNowException("The course is already in your cart");
      }
      cart.addCourse(course);
    }

    return cartRepository.save(cart);
  }

}
