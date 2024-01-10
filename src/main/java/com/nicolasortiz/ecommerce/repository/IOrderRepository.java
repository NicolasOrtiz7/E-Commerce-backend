package com.nicolasortiz.ecommerce.repository;

import com.nicolasortiz.ecommerce.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByCustomerCustomerId(int id);

}
