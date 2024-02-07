package com.nicolasortiz.ecommerce.service.impl;

import com.nicolasortiz.ecommerce.exception.MyNotFoundException;
import com.nicolasortiz.ecommerce.model.dto.product.ProductDto;
import com.nicolasortiz.ecommerce.model.entity.OrderItems;
import com.nicolasortiz.ecommerce.model.entity.Product;
import com.nicolasortiz.ecommerce.model.entity.ProductStock;
import com.nicolasortiz.ecommerce.model.mapper.ProductMapper;
import com.nicolasortiz.ecommerce.repository.IOrderItemsRepository;
import com.nicolasortiz.ecommerce.repository.IProductRepository;
import com.nicolasortiz.ecommerce.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final IProductRepository productRepository;
    private final IOrderItemsRepository itemsRepository;

    @Override
    public Page<ProductDto> findAll(Pageable pageable) {
        return productRepository.findAll(pageable).map(ProductMapper.INSTANCE::toDto);
    }

    @Override
    public Page<ProductDto> findAllActives(Pageable pageable) {
        return productRepository.findAllByActiveIsTrue(pageable).map(ProductMapper.INSTANCE::toDto);
    }

    @Override
    public ProductDto findById(int id) {
        return ProductMapper.INSTANCE.toDto(productRepository.findById(id)
                .orElseThrow(() -> new MyNotFoundException("Producto no encontrado")));
    }

    @Override
    public Page<ProductDto> findByCategoryName(Pageable pageable, String name) {
        return productRepository.findByCategoryNameAndActiveIsTrue(pageable, name).map(ProductMapper.INSTANCE::toDto);
    }

    @Override
    public Page<ProductDto> findByNameContaining(Pageable pageable, String keyword) {
        return productRepository.findByNameContainingAndActiveIsTrue(pageable, keyword).map(ProductMapper.INSTANCE::toDto);
    }

    @Override
    public List<ProductDto> findByImportant() {
        List<Product> productList = productRepository.findByImportantAndActiveIsTrue(true);
        return productList.stream()
                .map(ProductMapper.INSTANCE::toDto).toList();
    }

    @Override
    public void save(Product product) {
        ProductStock stock = new ProductStock();
        stock.setQuantity(product.getProductStock().getQuantity());
        stock.setProduct(product); // Solución temporal hasta hacer dtos

        product.setProductStock(stock);
        product.setActive(true);

        productRepository.save(product);
    }

    @Override
    public void update(int id, Product product) {
        findById(id);
        product.setProductId(id);
        product.setProductStock(null); // Solución temporal hasta hacer dtos. Evita que se edite el stock desde acá
        productRepository.save(product);
    }

    /* Si el producto no tiene ninguna compra, se elimina.
    Si tiene al menos una compra, se deshabilita */
    @Override
    public void disableOrDeleteById(int id) {
        // Verificar si tiene compras (verificar si existe en la tabla order_items)
        List<OrderItems> orderItems = itemsRepository.findByProductProductId(id);

        if (orderItems.isEmpty()) {
            // Eliminarlo definitivamente
            productRepository.deleteById(id);
        } else {
            // Deshabilitarlo
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new MyNotFoundException("No se encontró el producto"));
            product.setActive(false);

            productRepository.save(product);
        }
    }
}
