package com.nicolasortiz.ecommerce.controller;

import com.nicolasortiz.ecommerce.model.dto.CustomerDto;
import com.nicolasortiz.ecommerce.model.dto.ResponseDto;
import com.nicolasortiz.ecommerce.model.entity.Customer;
import com.nicolasortiz.ecommerce.model.entity.Product;
import com.nicolasortiz.ecommerce.service.ICustomerService;
import com.nicolasortiz.ecommerce.service.IFavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final ICustomerService customerService;
    private final IFavoriteService favoriteService;

    @GetMapping
    public ResponseEntity<ResponseDto> findAll(
            @PageableDefault(size = 20) Pageable pageable){
        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .message("Clientes encontrados")
                        .response(customerService.findAll(pageable))
                        .build());
    }

    @GetMapping("/{email}")
    public ResponseEntity<ResponseDto> findByEmail(@PathVariable String email){
        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .message("Cliente encontrado")
                        .response(customerService.findByEmail(email))
                        .build());
    }

    // Este no se usará. Se usará el de seguridad.
    @PostMapping
    public ResponseEntity<ResponseDto> save(@RequestBody Customer customer){
        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .message("Cliente encontrado")
                        .response(customerService.save(customer))
                        .build());
    }

    // ---------------- Favorites ----------------

    @GetMapping("/favorites/{customerId}")
    public ResponseEntity<ResponseDto> findAllFavorites(@PathVariable int customerId){
        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .message("Favoritos encontrados")
                        .response(favoriteService.findFavoritesByCustomerId(customerId))
                        .build());
    }

    @PostMapping("/favorites/{customerId}/{productId}")
    public ResponseEntity<ResponseDto> saveFavorite(@PathVariable int customerId,
                                                    @PathVariable int productId){
        favoriteService.save(productId, customerId);
        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .message("Producto agregado a favoritos!")
                        .response("OK")
                        .build());
    }

    @DeleteMapping("/favorites/{customerId}/{productId}")
    public ResponseEntity<ResponseDto> deleteFavorite(@PathVariable int customerId,
                                                      @PathVariable int productId){
        favoriteService.delete(productId, customerId);
        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .message("Producto eliminado de favoritos!")
                        .response("OK")
                        .build());
    }

}
