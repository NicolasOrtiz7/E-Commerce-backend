package com.nicolasortiz.ecommerce.service.impl;

import com.nicolasortiz.ecommerce.exception.MyNotFoundException;
import com.nicolasortiz.ecommerce.model.dto.product.ProductDto;
import com.nicolasortiz.ecommerce.model.entity.Product;
import com.nicolasortiz.ecommerce.model.mapper.ProductMapper;
import com.nicolasortiz.ecommerce.repository.IProductRepository;
import com.nicolasortiz.ecommerce.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final IProductRepository productRepository;

    @Override
    public Page<ProductDto> findAll(Pageable pageable) {
        return productRepository.findAll(pageable).map(ProductMapper.INSTANCE::toDto);
    }

    @Override
    public ProductDto findById(int id) {
        return ProductMapper.INSTANCE.toDto(productRepository.findById(id)
                .orElseThrow(() -> new MyNotFoundException("Producto no encontrado")));
    }

    @Override
    public Page<ProductDto> findByCategoryName(Pageable pageable, String name) {
        return productRepository.findByCategoryName(pageable, name).map(ProductMapper.INSTANCE::toDto);
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public void update(int id, Product product) {
        ProductDto productFound = findById(id);
        product.setProductId(id);
        save(product);
    }

    @Override
    public void deleteById(int id) {
        ProductDto productFound = findById(id);
        productRepository.deleteById(id);
    }
}
