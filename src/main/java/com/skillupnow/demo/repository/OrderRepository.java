package com.skillupnow.demo.repository;

import com.skillupnow.demo.model.po.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * OrderRepository interface provides methods to interact with the Order entity in the database.
 * It extends JpaRepository and CrudRepository which provide generic CRUD operations.
 *
 * @author Hua Wang
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, CrudRepository<Order, Long> {

}
