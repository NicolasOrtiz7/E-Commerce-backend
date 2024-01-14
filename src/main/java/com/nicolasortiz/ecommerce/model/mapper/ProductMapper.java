package com.nicolasortiz.ecommerce.model.mapper;

import com.nicolasortiz.ecommerce.model.dto.product.ProductDto;
import com.nicolasortiz.ecommerce.model.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDto toDto(Product product);
}
