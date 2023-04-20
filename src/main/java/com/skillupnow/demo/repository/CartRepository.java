package com.skillupnow.demo.repository;

import com.skillupnow.demo.model.po.Cart;
import com.skillupnow.demo.model.po.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
	@Query("select c from Cart c where c.id = ?1")
	Cart findById(long id);
}
