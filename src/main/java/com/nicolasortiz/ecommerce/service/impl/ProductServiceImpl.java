package com.nicolasortiz.ecommerce.service.impl;

import com.nicolasortiz.ecommerce.exception.MyNotFoundException;
import com.nicolasortiz.ecommerce.model.dto.product.ProductDto;
import com.nicolasortiz.ecommerce.model.entity.Product;
import com.nicolasortiz.ecommerce.model.entity.ProductStock;
import com.nicolasortiz.ecommerce.model.mapper.ProductMapper;
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
    public Page<ProductDto> findByNameContaining(Pageable pageable, String keyword) {
        return productRepository.findByNameContaining(pageable, keyword).map(ProductMapper.INSTANCE::toDto);
    }

    @Override
    public List<ProductDto> findByImportant() {
        List<Product> productList = productRepository.findByImportant(true);
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

    @Override
    public void deleteById(int id) {
        findById(id);
        productRepository.deleteById(id);
    }
}
