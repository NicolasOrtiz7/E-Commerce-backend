package com.nicolasortiz.ecommerce.service.impl;

import com.nicolasortiz.ecommerce.exception.MyNotFoundException;
import com.nicolasortiz.ecommerce.model.entity.Product;
import com.nicolasortiz.ecommerce.repository.IProductRepository;
import com.nicolasortiz.ecommerce.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final IProductRepository productRepository;

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Optional<Product> findById(int id) {
        return Optional.ofNullable(productRepository.findById(id)
                .orElseThrow(() -> new MyNotFoundException("Producto no encontrado")));
    }

    @Override
    public Page<Product> findByCategoryName(Pageable pageable, String name) {
        return productRepository.findByCategoryName(pageable, name);
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public void update(int id, Product product) {
        Optional<Product> productFound = findById(id);
        product.setProductId(id);
        save(product);
    }

    @Override
    public void deleteById(int id) {
        Optional<Product> productFound = findById(id);
        productRepository.deleteById(id);
    }
}
