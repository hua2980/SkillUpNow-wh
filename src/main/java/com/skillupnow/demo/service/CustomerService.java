package com.skillupnow.demo.service;

import com.google.gson.Gson;
import com.skillupnow.demo.exception.SkillUpNowException;
import com.skillupnow.demo.model.UserType;
import com.skillupnow.demo.model.dto.CreateUserRequest;
import com.skillupnow.demo.model.dto.ModifyCustomerRequest;
import com.skillupnow.demo.model.po.Cart;
import com.skillupnow.demo.model.po.Customer;
import com.skillupnow.demo.model.po.User;
import com.skillupnow.demo.repository.CartRepository;
import com.skillupnow.demo.repository.CustomerRepository;
import com.skillupnow.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class provides the main services related to managing a customer in the SkillUpNow demo application.
 */
@Service
public class CustomerService {
  Logger logger = LoggerFactory.getLogger(CustomerService.class);

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;


  @Autowired
  private CartRepository cartRepository;

  /**
   * Retrieves a customer by their username.
   *
   * @param username The username of the customer.
   * @return The customer without their password.
   * @throws SkillUpNowException If the customer is not found.
   */
  public Customer findByUsername(String username) throws SkillUpNowException {
    Customer customer = customerRepository.findByUsername(username);
    if (customer == null) {
      logger.error("Failed to get customer. Customer not found, username={}", username);
      throw new SkillUpNowException("Customer not found");
    }
    Customer returnCustomer = new Customer();
    BeanUtils.copyProperties(customer, returnCustomer, "password");
    return returnCustomer;
  }

  /**
   * Creates a new customer based on the provided request.
   * This method is transactional, meaning that all database operations are performed within a single transaction.
   *
   * @param createUserRequest The request containing the customer's information.
   * @return The created user without their password.
   * @throws SkillUpNowException If the username is already taken.
   */
  @Transactional
  public User createCustomer(CreateUserRequest createUserRequest) throws SkillUpNowException {
    // check if the username is already taken
    if (userRepository.findByUsername(createUserRequest.getUsername()) != null) {
      logger.error("Failed to create customer. Username already taken, username={}", createUserRequest.getUsername());
      throw new SkillUpNowException("Username already taken");
    }

    // save the customer
    Customer customer = new Customer();
    BeanUtils.copyProperties(createUserRequest, customer);
    Cart cart = new Cart();
    customer.setCart(cart);
    customer.setPassword(bCryptPasswordEncoder.encode(createUserRequest.getPassword()));

    // write into database
    customer = customerRepository.save(customer);
    cart.setCustomer(customer);
    cartRepository.save(cart);

    // return the saved user and mute the password
    User returnInfo = new User();
    BeanUtils.copyProperties(customer, returnInfo, "password");

    return returnInfo;
  }

  /**
   * Updates the information of an existing customer based on the provided request.
   * This method is transactional, meaning that all database operations are performed within a single transaction.
   *
   * @param modifyCustomerRequest The request containing the customer's updated information.
   * @param username The username of the customer to update.
   * @throws SkillUpNowException If the customer is not found.
   */
  @Transactional
  public void updateCustomer(ModifyCustomerRequest modifyCustomerRequest, String username) throws SkillUpNowException {
    // get customer
    Customer customer = customerRepository.findByUsername(username);
    // check if the customer exists
    if (customer == null) {
      logger.error("Failed to update customer. Customer not found, username={}", username);
      throw new SkillUpNowException("Customer not found");
    }
    // update customer
    BeanUtils.copyProperties(modifyCustomerRequest, customer);
    // write into database
    customerRepository.save(customer);
  }
}
