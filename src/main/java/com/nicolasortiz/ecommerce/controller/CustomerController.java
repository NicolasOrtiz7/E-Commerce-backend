package com.nicolasortiz.ecommerce.controller;

import com.nicolasortiz.ecommerce.model.dto.customer.CustomerDto;
import com.nicolasortiz.ecommerce.model.dto.product.FavoriteDto;
import com.nicolasortiz.ecommerce.model.entity.Customer;
import com.nicolasortiz.ecommerce.service.ICustomerService;
import com.nicolasortiz.ecommerce.service.IFavoriteService;
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
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final ICustomerService customerService;
    private final IFavoriteService favoriteService;

    // ---------------- Customers ----------------

    @GetMapping
    public ResponseEntity<Page<CustomerDto>> findAll(
            @PageableDefault(size = 20) Pageable pageable){
        return ResponseEntity.ok()
                .body(customerService.findAll(pageable));
    }

    @GetMapping("/{email}")
    public ResponseEntity<CustomerDto> findByEmail(@PathVariable String email){
        return ResponseEntity.ok()
                .body(customerService.findByEmail(email));
    }

    // Este no se usará. Se usará el de seguridad.
    @PostMapping
    public ResponseEntity<Void> saveCustomer(@RequestBody @Valid Customer customer){

        customerService.save(customer);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // ---------------- Favorites ----------------

    @GetMapping("/favorites/{customerId}")
    public ResponseEntity<List<FavoriteDto>> findAllFavorites(@PathVariable int customerId){
        return ResponseEntity.ok()
                .body(favoriteService.findFavoritesByCustomerId(customerId));
    }

    @PostMapping("/favorites/{customerId}/{productId}")
    public ResponseEntity<Void> saveFavorite(@PathVariable int customerId,
                                             @PathVariable int productId){
        favoriteService.save(productId, customerId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/favorites/{customerId}/{productId}")
    public ResponseEntity<Void> deleteFavorite(@PathVariable int customerId,
                                               @PathVariable int productId){
        favoriteService.delete(productId, customerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
