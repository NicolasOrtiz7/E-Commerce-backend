package com.nicolasortiz.ecommerce.service;

import com.nicolasortiz.ecommerce.model.dto.OrderDto;
import com.nicolasortiz.ecommerce.model.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IOrderService {

    Page<OrderDto> findAll(Pageable pageable);

    Order findById(int id);

    List<OrderDto> findByCustomerId(int id);

}
