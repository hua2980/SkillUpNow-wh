package com.skillupnow.demo.repository;

import com.skillupnow.demo.model.po.Cart;
import com.skillupnow.demo.model.po.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
	Cart findByCustomer(Customer customer);
}
