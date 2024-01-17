package com.nicolasortiz.ecommerce.service;

import com.nicolasortiz.ecommerce.model.dto.product.ProductStockDto;
import com.nicolasortiz.ecommerce.model.dto.stock.StockDto;
import com.nicolasortiz.ecommerce.model.entity.ProductStock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IStockService {

    Page<StockDto> findAll(Pageable pageable);

    StockDto findByProductId(int id);

    void save(ProductStock stock);

    void update(int productId, int quantity);

}
