package com.skillupnow.demo.repository;

import com.skillupnow.demo.model.po.Customer;
import com.skillupnow.demo.model.po.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * CustomerRepository interface provides methods to interact with the Customer entity in the database.
 * It extends JpaRepository which provides generic CRUD operations
 * This interface provides a custom method to find a customer by its username.
 *
 * @author Hua Wang
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	/**
	 * Find a customer by its username.
	 *
	 * @param username The username of the customer to be retrieved.
	 * @return The Customer object matching the given username, or null if not found.
	 */
	Customer findByUsername(String username);
}
