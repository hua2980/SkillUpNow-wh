package com.skillupnow.demo.repository;

import com.skillupnow.demo.model.po.Cart;
import com.skillupnow.demo.model.po.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * CartRepository interface provides methods to interact with the Cart entity in the database.
 * It extends JpaRepository which provides generic CRUD operations
 * This interface provides a custom method to find a cart by its ID.
 *
 * @author Hua Wang
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
	/**
	 * Find a cart by its ID.
	 *
	 * @param id The ID of the cart to be retrieved.
	 * @return The Cart object matching the given ID, or null if not found.
	 */
	@Query("select c from Cart c where c.id = ?1")
	Cart findById(long id);
}
