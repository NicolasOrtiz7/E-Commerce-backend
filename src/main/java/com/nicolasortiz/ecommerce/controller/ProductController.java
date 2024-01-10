package com.nicolasortiz.ecommerce.controller;

import com.nicolasortiz.ecommerce.model.dto.ResponseDto;
import com.nicolasortiz.ecommerce.model.entity.Product;
import com.nicolasortiz.ecommerce.model.entity.ProductCategory;
import com.nicolasortiz.ecommerce.service.ICategoryService;
import com.nicolasortiz.ecommerce.service.IProductService;
import com.nicolasortiz.ecommerce.service.IStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
    public ResponseEntity<Page<Product>> findAll2(@PageableDefault(size = 15) Pageable pageable){
        return ResponseEntity.ok()
                .body(productService.findAll(pageable));
    }

    // Buscar producto por su ID
    @GetMapping("/{productId}")
    public ResponseEntity<ResponseDto> findProductById(@PathVariable int productId){
        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .message("Producto encontrado")
                        .response(productService.findById(productId))
                        .build());
    }

    // Buscar productos por nombre de categoría y con paginación (param. categoryName required)
    @GetMapping("/search")
    public ResponseEntity<ResponseDto> findProductsByCategoryName(
            @RequestParam("category") String category,
            @PageableDefault(size = 15) Pageable pageable){
        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .message("Productos encontrados")
                        .response(productService.findByCategoryName(pageable, category))
                        .build());
    }

    // Guardar un producto
    @PostMapping
    public ResponseEntity<ResponseDto> saveProduct(@RequestBody Product product){

        productService.save(product);
        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .message("Producto creado correctamente")
                        .response("OK")
                        .build());
    }

    // Actualizar un producto
    @PutMapping("/{productId}")
    public ResponseEntity<ResponseDto> updateProduct(@PathVariable int productId,
                                                     @RequestBody Product product){
        productService.update(productId, product);
        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .message("Producto actualizado correctamente")
                        .response("OK")
                        .build());
    }

    // Eliminar un producto por su ID
    @DeleteMapping("/{productId}")
    public ResponseEntity<ResponseDto> deleteProductById(@PathVariable int productId){

        productService.deleteById(productId);
        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .message("Producto eliminado correctamente")
                        .response("OK")
                        .build());
    }

    // ------------ Categories ------------

    // Buscar todas las categorías
    @GetMapping("/categories")
    public ResponseEntity<ResponseDto> findAllCategories(){
        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .message("Categorias encontradas")
                        .response(categoryService.findAll())
                        .build());
    }

    // Buscar categoría por su ID
    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<ResponseDto> findCategoryById(@PathVariable int categoryId){
        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .message("Categoria encontrada")
                        .response(categoryService.findById(categoryId))
                        .build());
    }

    // Guardar una categoría
    @PostMapping("/categories")
    public ResponseEntity<ResponseDto> saveCategory(@RequestBody ProductCategory category){

        categoryService.save(category);
        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .message("Categoria guardada correctamente")
                        .response("OK")
                        .build());
    }

    // Actualizar una categoría
    @PutMapping("/categories/{categoryId}")
    public ResponseEntity<ResponseDto> updateCategory(@PathVariable int categoryId,
                                                      @RequestBody ProductCategory category){
        categoryService.update(categoryId, category);
        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .message("Categoria editada correctamente")
                        .response("OK")
                        .build());
    }

    // Eliminar una categoría
    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<ResponseDto> deleteCategoryById(@PathVariable int categoryId){

        categoryService.delete(categoryId);
        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .message("Categoria eliminada correctamente")
                        .response("OK")
                        .build());
    }

    // ------------ Stock ------------

    // Buscar el stock de todos los productos
    @GetMapping("/stock")
    public ResponseEntity<ResponseDto> findAllStock(@PageableDefault(size = 15) Pageable pageable){
        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .message("Stock de productos encontrados")
                        .response(stockService.findAll(pageable))
                        .build());
    }

    // Buscar el stock de un producto por su productId
    @GetMapping("/stock/{productId}")
    public ResponseEntity<ResponseDto> findStockByProductId(@PathVariable int productId){
        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .message("Stock de producto encontrado")
                        .response(stockService.findByProductId(productId))
                        .build());
    }

    // Actualizar stock de un producto
    @PutMapping("/stock/product/{productId}/{quantity}")
    public ResponseEntity<ResponseDto> updateStock(@PathVariable int productId,
                                                   @PathVariable int quantity){
        stockService.update(productId, quantity);
        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .message("Stock de producto encontrado")
                        .response("OK")
                        .build());
    }

}
