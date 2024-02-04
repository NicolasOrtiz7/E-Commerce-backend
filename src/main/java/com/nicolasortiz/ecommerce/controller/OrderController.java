package com.nicolasortiz.ecommerce.controller;

import com.nicolasortiz.ecommerce.model.dto.order.OrderRequestDto;
import com.nicolasortiz.ecommerce.model.dto.order.OrderResponseDto;
import com.nicolasortiz.ecommerce.service.IOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService orderService;


    @GetMapping
    public ResponseEntity<Page<OrderResponseDto>> findAll(@PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok()
                .body(orderService.findAll(pageable));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> findByOrderId(@PathVariable int orderId){
        return ResponseEntity.ok()
                .body(orderService.findById(orderId));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderResponseDto>> findByCustomerId(@PathVariable int customerId){
        return ResponseEntity.ok()
                .body(orderService.findByCustomerId(customerId));
    }

    @PostMapping
    public ResponseEntity<Void> saveOrder(@RequestBody @Valid OrderRequestDto orderRequest){
        orderService.saveOrder(orderRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
