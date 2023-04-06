package com.skillupnow.demo.service.Iml;

import com.skillupnow.demo.exception.SkillUpNowException;
import com.skillupnow.demo.model.dto.CreateUserRequest;
import com.skillupnow.demo.model.po.Cart;
import com.skillupnow.demo.model.po.Customer;
import com.skillupnow.demo.model.po.User;
import com.skillupnow.demo.repository.CartRepository;
import com.skillupnow.demo.repository.CustomerRepository;
import com.skillupnow.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService implements UserService {

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  private CartRepository cartRepository;

  @Override
  @Transactional
  public User createUser(CreateUserRequest createUserRequest) throws SkillUpNowException {
    // check if it is a valid password
    if (createUserRequest.getPassword().length() < 7 || !createUserRequest.getPassword()
        .equals(createUserRequest.getConfirmPassword())){
      System.out.println("Invalid password");
      throw new SkillUpNowException("Invalid password");
    }

    // check if the username is already taken
    if (customerRepository.findByUsername(createUserRequest.getUsername()) != null) {
      throw new SkillUpNowException("Username already taken");
    }

    // save the customer
    Customer customer = new Customer();
    customer.setUsername(createUserRequest.getUsername());
    Cart cart = new Cart();
    customer.setCart(cart);
    customer.setPassword(bCryptPasswordEncoder.encode(createUserRequest.getPassword()));

    // write into database
    User savedUser = customerRepository.save(customer);
    cartRepository.save(cart);

    return savedUser;
  }
}
