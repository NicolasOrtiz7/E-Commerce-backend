package com.nicolasortiz.ecommerce.controller;

import com.nicolasortiz.ecommerce.model.dto.ResponseDto;
import com.nicolasortiz.ecommerce.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService orderService;


    @GetMapping
    public ResponseEntity<ResponseDto> findAll(@PageableDefault(size = 15) Pageable pageable){
        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .message("Ordenes encontradas")
                        .response(orderService.findAll(pageable))
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> findByOrderId(@PathVariable int id){
        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .message("Orden encontrada")
                        .response(orderService.findById(id))
                        .build());
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<ResponseDto> findByCustomerId(@PathVariable int id){
        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .message("Ordenes encontradas")
                        .response(orderService.findByCustomerId(id))
                        .build());
    }

}