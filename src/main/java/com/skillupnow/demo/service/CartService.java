package com.skillupnow.demo.service;

import com.skillupnow.demo.model.dto.ModifyCartRequest;
import com.skillupnow.demo.model.po.Customer;
import com.skillupnow.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {
  @Autowired
  private CustomerRepository customerRepository;

  @Transactional
  public void addProductToCart(ModifyCartRequest request) {
    Customer customer = customerRepository.findByUsername(request.getUsername());
    // TODO: 完善逻辑
  }

}
