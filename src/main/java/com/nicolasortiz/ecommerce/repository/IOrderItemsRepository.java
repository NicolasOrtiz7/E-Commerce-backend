package com.nicolasortiz.ecommerce.repository;

import com.nicolasortiz.ecommerce.model.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderItemsRepository extends JpaRepository<OrderItems, Integer> {

    List<OrderItems> findByProductProductId(int productId);

}
