package com.nicolasortiz.ecommerce.service.impl;

import com.nicolasortiz.ecommerce.exception.MyNotFoundException;
import com.nicolasortiz.ecommerce.model.entity.ProductStock;
import com.nicolasortiz.ecommerce.repository.IStockRepository;
import com.nicolasortiz.ecommerce.service.IStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements IStockService {

    private final IStockRepository stockRepository;

    @Override
    public Page<ProductStock> findAll(Pageable pageable) {
        return stockRepository.findAll(pageable);
    }

    @Override
    public Optional<ProductStock> findByProductId(int id) {
        Optional<ProductStock> productStock = Optional.ofNullable(
                stockRepository.findByProductProductId(id));
        if (productStock.isEmpty()){
            throw new MyNotFoundException("No se encontró el producto");
        }
        return productStock;
    }

    @Override
    public void save(ProductStock stock) {
         // Se guarda desde el UserServiceImpl
    }

    @Override
    public void update(int productId, int quantity) {
        Optional<ProductStock> stock = findByProductId(productId);
        if (quantity < 0) quantity = 0;

        stock.get().setQuantity(quantity);
        stockRepository.save(stock.get());
    }
}
