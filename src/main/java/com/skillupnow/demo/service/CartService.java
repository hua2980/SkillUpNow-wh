package com.skillupnow.demo.service;

import com.skillupnow.demo.exception.SkillUpNowException;
import com.skillupnow.demo.model.dto.ModifyCartRequest;
import com.skillupnow.demo.model.po.Cart;
import com.skillupnow.demo.model.po.Course;
import com.skillupnow.demo.model.po.Customer;
import com.skillupnow.demo.repository.CartRepository;
import com.skillupnow.demo.repository.CourseRepository;
import com.skillupnow.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {
  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private CartRepository cartRepository;

  @Autowired
  private CourseRepository courseRepository;

  public Cart getCartByUsername(String username){
    Customer customer = customerRepository.findByUsername(username);
    if (customer == null) {
      throw new SkillUpNowException("Customer not found");
    }
    return cartRepository.findByCustomer(customer);
  }

  @Transactional
  public Cart modifyCart(ModifyCartRequest request) {
    // get the customer and assert the customer exists
    Customer customer = customerRepository.findByUsername(request.getUsername());
    if (customer == null) {
      throw new SkillUpNowException("Customer not found");
    }

    // get the course and assert the course exists
    Course course = courseRepository.findById(request.getCourseId()).orElse(null);
    if (course == null) {
      throw new SkillUpNowException("Course not found");
    }

    // get the cart
    Cart cart = customer.getCart();
    // if is deletion, remove the course from the cart
    if (request.getDelete() == 1){
      // course should be in the cart
      if (!cart.getCourses().contains(course)) throw new SkillUpNowException("Course not in the cart");
      cart.removeCourse(course);
    }
    // if is addition, add the course to the cart;
    if (cart.getCourses().contains(course)) return cart;
    cart.addCourse(course);

    return cartRepository.save(cart);
  }

}
