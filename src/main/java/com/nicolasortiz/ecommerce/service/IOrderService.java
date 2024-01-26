package com.nicolasortiz.ecommerce.service;

import com.nicolasortiz.ecommerce.model.dto.order.OrderRequestDto;
import com.nicolasortiz.ecommerce.model.dto.order.OrderResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IOrderService {

    Page<OrderResponseDto> findAll(Pageable pageable);

    OrderResponseDto findById(int id);

    List<OrderResponseDto> findByCustomerId(int id);

    void saveOrder(OrderRequestDto orderRequest);

}
