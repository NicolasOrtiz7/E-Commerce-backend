package com.nicolasortiz.ecommerce.service.impl;

import com.nicolasortiz.ecommerce.exception.MyNotFoundException;
import com.nicolasortiz.ecommerce.model.dto.order.OrderRequestDto;
import com.nicolasortiz.ecommerce.model.dto.order.OrderResponseDto;
import com.nicolasortiz.ecommerce.model.entity.Order;
import com.nicolasortiz.ecommerce.model.entity.OrderDetails;
import com.nicolasortiz.ecommerce.model.entity.OrderItems;
import com.nicolasortiz.ecommerce.model.mapper.OrderMapper;
import com.nicolasortiz.ecommerce.repository.IOrderItemsRepository;
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
    private final IOrderItemsRepository itemsRepository;

    @Override
    public Page<OrderResponseDto> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable).map(OrderMapper.INSTANCE::toDto);
    }

    @Override
    public OrderResponseDto findById(int id) {
        return OrderMapper.INSTANCE.toDto(orderRepository.findById(id)
                .orElseThrow(()-> new MyNotFoundException("No se encontró la orden")));
    }

    @Override
    public List<OrderResponseDto> findByCustomerId(int id) {
        return orderRepository.findByCustomerCustomerId(id)
                .stream()
                .map(OrderMapper.INSTANCE::toDto)
                .toList();
    }

    // todo: terminar esta función
    @Override
    public void saveOrder(OrderRequestDto orderRequest) {
        // Verificar Cliente
        // Verificar Producto
        // Verificar Categoria

        Order orderEntity = OrderMapper.INSTANCE.toEntity(orderRequest);

        for (OrderItems item : orderEntity.getOrderItems()) {
            item.setOrder(orderEntity);
            // Buscar producto en BBDD y sumar el total
            Long total = item.getQuantity() * item.getProduct().getPrice();
        }

        orderRepository.save(orderEntity);
    }

}
