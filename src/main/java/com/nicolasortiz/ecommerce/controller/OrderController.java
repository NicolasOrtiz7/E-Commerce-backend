package com.nicolasortiz.ecommerce.controller;

import com.nicolasortiz.ecommerce.model.dto.OrderDto;
import com.nicolasortiz.ecommerce.model.entity.Order;
import com.nicolasortiz.ecommerce.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService orderService;


    @GetMapping
    public ResponseEntity<Page<OrderDto>> findAll(@PageableDefault(size = 15) Pageable pageable){
        return ResponseEntity.ok()
                .body(orderService.findAll(pageable));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> findByOrderId(@PathVariable int orderId){
        return ResponseEntity.ok()
                .body(orderService.findById(orderId));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderDto>> findByCustomerId(@PathVariable int customerId){
        return ResponseEntity.ok()
                .body(orderService.findByCustomerId(customerId));
    }

}
