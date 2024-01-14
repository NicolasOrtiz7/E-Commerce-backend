package com.nicolasortiz.ecommerce.service.impl;

import com.nicolasortiz.ecommerce.exception.MyNotFoundException;
import com.nicolasortiz.ecommerce.exception.NoStockException;
import com.nicolasortiz.ecommerce.model.dto.order.OrderRequestDto;
import com.nicolasortiz.ecommerce.model.dto.order.OrderResponseDto;
import com.nicolasortiz.ecommerce.model.dto.order.request.OrderItemsReq;
import com.nicolasortiz.ecommerce.model.entity.*;
import com.nicolasortiz.ecommerce.model.mapper.OrderMapper;
import com.nicolasortiz.ecommerce.repository.ICustomerRepository;
import com.nicolasortiz.ecommerce.repository.IOrderRepository;
import com.nicolasortiz.ecommerce.repository.IProductRepository;
import com.nicolasortiz.ecommerce.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {

    private final IOrderRepository orderRepository;
    private final IProductRepository productRepository;
    private final ICustomerRepository customerRepository;

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

    @Override
    public void saveOrder(OrderRequestDto orderRequest) {
        // Mapear OrderRequestDto a Order
        Order orderEntity = OrderMapper.INSTANCE.toEntity(orderRequest);

        // Verificar que el cliente está registrado
        verifyCustomer(orderEntity.getCustomer().getCustomerId());

        // Verificar que todos los productos existen y tienen stock
        List<Product> productList = verifyProduct(orderEntity.getOrderItems());

        // Calcular el total de la orden
        calculateOrderTotal(orderEntity, productList);

        // Asignar la fecha actual y el order_id a la tabla de order_details
        orderEntity.setDatetime(LocalDateTime.now());
        orderEntity.getOrderDetails().setOrder(orderEntity);

        orderRepository.save(orderEntity);
    }

    // --------------------------------------------------------

    // Verificar que el cliente está registrado
    private void verifyCustomer(int customerId){
        customerRepository.findById(customerId)
                .orElseThrow(()-> new MyNotFoundException("El cliente no está registrado"));
    }

    // Verificar si los productos existen y tienen stock
    private List<Product> verifyProduct(List<OrderItems> productIds){
        List<Product> productList = new ArrayList<>();
        Map<Integer, String> outOfStock = new HashMap<>();

        productIds.forEach(prod -> {
            // Verificar si existe el Producto
            Product product = productRepository.findById(prod.getProduct().getProductId())
                    .orElseThrow(()-> new MyNotFoundException("No existe el producto: " + prod.getProduct()));

            // Verificar si el producto tiene Stock
            if (prod.getQuantity() > product.getProductStock().getQuantity()){
                outOfStock.put(prod.getProduct().getProductId(), product.getName());
            }

            // Actualizar Stock
            int updatedStock = product.getProductStock().getQuantity() - prod.getQuantity();
            product.getProductStock().setQuantity(updatedStock);

            productList.add(product);
        });

        if (!outOfStock.isEmpty()){
            throw new NoStockException("No existe stock de los siguientes productos: " + outOfStock);
        }
        return productList;
    }

    private void calculateOrderTotal(Order orderEntity, List<Product> productList) {
        long total = 0;

        // Recorrer la lista de productos
        for (int i = 0; i < orderEntity.getOrderItems().size(); i++) {
            OrderItems item = orderEntity.getOrderItems().get(i);
            item.setOrder(orderEntity); // Asignar order_id a la tabla de order_items

            total += item.getQuantity() * productList.get(i).getPrice(); // Calcular total
        }
        orderEntity.getOrderDetails().setTotal(total);
    }
}
