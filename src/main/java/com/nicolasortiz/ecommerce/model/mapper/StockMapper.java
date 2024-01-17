package com.nicolasortiz.ecommerce.model.mapper;

import com.nicolasortiz.ecommerce.model.dto.stock.StockDto;
import com.nicolasortiz.ecommerce.model.entity.ProductStock;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StockMapper {

    StockMapper INSTANCE = Mappers.getMapper(StockMapper.class);

    StockDto toDto(ProductStock stock);

    ProductStock toEntity(StockDto dto);

}
