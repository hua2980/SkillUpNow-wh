package com.skillupnow.demo.service;

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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  private CartRepository cartRepository;

  public Customer findByUsername(String username) {
    Customer customer = customerRepository.findByUsername(username);
    if (customer == null) {
      throw new SkillUpNowException("Customer not found");
    }
    Customer returnCustomer = new Customer();
    BeanUtils.copyProperties(customer, returnCustomer, "password");
    return returnCustomer;
  }

  @Transactional
  public User createCustomer(CreateUserRequest createUserRequest) throws SkillUpNowException {
    // check if the username is already taken
    if (userRepository.findByUsername(createUserRequest.getUsername()) != null) {
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
    if (customer.getId() == null) {
      throw new SkillUpNowException("User not saved");
    }
    cart.setCustomer(customer);
    cartRepository.save(cart);

    // return the saved user and mute the password
    User returnInfo = new User();
    BeanUtils.copyProperties(customer, returnInfo, "password");

    return returnInfo;
  }

  @Transactional
  public ModifyCustomerRequest updateCustomer(ModifyCustomerRequest modifyCustomerRequest, String username) throws SkillUpNowException {
    // get customer
    Customer customer = customerRepository.findByUsername(username);
    // check if the customer exists
    if (customer == null) {
      throw new SkillUpNowException("Customer not found");
    }
    // update customer
    BeanUtils.copyProperties(modifyCustomerRequest, customer);
    // write into database
    Customer savedCustomer = customerRepository.save(customer);
    if (savedCustomer.getId() == null) {
      throw new SkillUpNowException("Customer not saved");
    }
    // return the saved customer
    ModifyCustomerRequest returnInfo = new ModifyCustomerRequest();
    BeanUtils.copyProperties(savedCustomer, returnInfo);
    return returnInfo;
  }
}
