package com.nicolasortiz.ecommerce.service.impl;

import com.nicolasortiz.ecommerce.exception.MyNotFoundException;
import com.nicolasortiz.ecommerce.model.dto.stock.StockDto;
import com.nicolasortiz.ecommerce.model.entity.ProductStock;
import com.nicolasortiz.ecommerce.model.mapper.StockMapper;
import com.nicolasortiz.ecommerce.repository.IStockRepository;
import com.nicolasortiz.ecommerce.service.IStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class StockServiceImpl implements IStockService {

    private final IStockRepository stockRepository;

    @Override
    public Page<StockDto> findAll(Pageable pageable) {
        return stockRepository.findAll(pageable).map(StockMapper.INSTANCE::toDto);
    }

    @Override
    public StockDto findByProductId(int id) {
        return StockMapper.INSTANCE.toDto(stockRepository.findByProductProductId(id)
                .orElseThrow(()-> new MyNotFoundException("No se encontró el producto")));
    }

    @Override
    public void save(ProductStock stock) {
         // Se guarda desde el UserServiceImpl
    }

    @Override
    public void update(int productId, int quantity) {
        ProductStock stock = StockMapper.INSTANCE.toEntity(findByProductId(productId));
        if (quantity < 0) quantity = 0;

        stock.setQuantity(quantity);
        stockRepository.save(stock);
    }
}
