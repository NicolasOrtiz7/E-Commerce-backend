package com.nicolasortiz.ecommerce.controller;

import com.nicolasortiz.ecommerce.model.dto.ResponseDto;
import com.nicolasortiz.ecommerce.model.entity.Product;
import com.nicolasortiz.ecommerce.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> findById(@PathVariable int id){
        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .message("Producto encontrado")
                        .response(productService.findById(id))
                        .build());
    }
    
    @PostMapping
    public ResponseEntity<ResponseDto> save(@RequestBody Product product){
        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .message("Producto creado correctamente")
                        .response(productService.save(product))
                        .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> update(@PathVariable int id, @RequestBody Product product){
        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .message("Producto actualizado correctamente")
                        .response(productService.update(id, product))
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> delete(@PathVariable int id){
        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .message("Producto eliminado correctamente")
                        .response(productService.deleteById(id))
                        .build());
    }

}
