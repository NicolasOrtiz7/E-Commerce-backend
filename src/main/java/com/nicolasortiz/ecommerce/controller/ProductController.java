package com.nicolasortiz.ecommerce.controller;

import com.nicolasortiz.ecommerce.model.dto.product.ProductDto;
import com.nicolasortiz.ecommerce.model.entity.Product;
import com.nicolasortiz.ecommerce.model.entity.ProductCategory;
import com.nicolasortiz.ecommerce.model.entity.ProductStock;
import com.nicolasortiz.ecommerce.service.ICategoryService;
import com.nicolasortiz.ecommerce.service.IProductService;
import com.nicolasortiz.ecommerce.service.IStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService;
    private final ICategoryService categoryService;
    private final IStockService stockService;

    // ------------ Products ------------

    // Buscar todos con paginación
    @GetMapping
    public ResponseEntity<Page<ProductDto>> findAll2(@PageableDefault(size = 15) Pageable pageable){
        return ResponseEntity.ok()
                .body(productService.findAll(pageable));
    }

    // Buscar producto por su ID
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> findProductById(@PathVariable int productId){
        return ResponseEntity.ok()
                .body(productService.findById(productId));
    }

    // Buscar productos por nombre de categoría y con paginación (param. categoryName required)
    @GetMapping("/search")
    public ResponseEntity<Page<ProductDto>> findProductsByCategoryName(
            @RequestParam("category") String category,
            @PageableDefault(size = 15) Pageable pageable){

        return ResponseEntity.ok()
                .body(productService.findByCategoryName(pageable, category));
    }

    // Guardar un producto
    @PostMapping
    public ResponseEntity<Void> saveProduct(@RequestBody Product product){

        productService.save(product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // Actualizar un producto
    @PutMapping("/{productId}")
    public ResponseEntity<Void> updateProduct(@PathVariable int productId,
                                              @RequestBody Product product){
        productService.update(productId, product);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Eliminar un producto por su ID
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProductById(@PathVariable int productId){

        productService.deleteById(productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // ------------ Categories ------------

    // Buscar todas las categorías
    @GetMapping("/categories")
    public ResponseEntity<List<ProductCategory>> findAllCategories(){
        return ResponseEntity.ok()
                .body(categoryService.findAll());
    }

    // Buscar categoría por su ID
    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<ProductCategory> findCategoryById(@PathVariable int categoryId){
        return ResponseEntity.ok()
                .body(categoryService.findById(categoryId));
    }

    // Guardar una categoría
    @PostMapping("/categories")
    public ResponseEntity<Void> saveCategory(@RequestBody ProductCategory category){

        categoryService.save(category);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // Actualizar una categoría
    @PutMapping("/categories/{categoryId}")
    public ResponseEntity<Void> updateCategory(@PathVariable int categoryId,
                                                      @RequestBody ProductCategory category){
        categoryService.update(categoryId, category);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Eliminar una categoría
    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable int categoryId){

        categoryService.delete(categoryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // ------------ Stock ------------

    // Buscar el stock de todos los productos
    @GetMapping("/stock")
    public ResponseEntity<Page<ProductStock>> findAllStock(@PageableDefault(size = 15) Pageable pageable){
        return ResponseEntity.ok()
                .body(stockService.findAll(pageable));
    }

    // Buscar el stock de un producto por su productId
    @GetMapping("/stock/{productId}")
    public ResponseEntity<ProductStock> findStockByProductId(@PathVariable int productId){
        return ResponseEntity.ok()
                .body(stockService.findByProductId(productId));
    }

    // Actualizar stock de un producto
    @PutMapping("/stock/product/{productId}/{quantity}")
    public ResponseEntity<Void> updateStock(@PathVariable int productId,
                                                   @PathVariable int quantity){
        stockService.update(productId, quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
