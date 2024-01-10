package com.nicolasortiz.ecommerce.service.impl;

import com.nicolasortiz.ecommerce.exception.MyNotFoundException;
import com.nicolasortiz.ecommerce.model.dto.OrderDto;
import com.nicolasortiz.ecommerce.model.entity.Order;
import com.nicolasortiz.ecommerce.model.mapper.OrderMapper;
import com.nicolasortiz.ecommerce.repository.IOrderRepository;
import com.nicolasortiz.ecommerce.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {

    private final IOrderRepository orderRepository;

    @Override
    public Page<OrderDto> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable).map(OrderMapper.INSTANCE::toDto);
    }

    @Override
    public Order findById(int id) {
        return orderRepository.findById(id)
                .orElseThrow(()-> new MyNotFoundException("No se encontró la orden"));
    }

    @Override
    public List<OrderDto> findByCustomerId(int id) {
        return orderRepository.findByCustomerCustomerId(id)
                .stream()
                .map(OrderMapper.INSTANCE::toDto)
                .toList();
    }

}
