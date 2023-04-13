package com.skillupnow.demo.repository;

import com.skillupnow.demo.model.po.Customer;
import com.skillupnow.demo.model.po.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	Customer findByUsername(String username);
}
