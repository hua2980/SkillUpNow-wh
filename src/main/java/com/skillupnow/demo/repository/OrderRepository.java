package com.skillupnow.demo.repository;

import com.skillupnow.demo.model.po.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, CrudRepository<Order, Long> {

}
