package com.nicolasortiz.ecommerce.model.mapper;

import com.nicolasortiz.ecommerce.model.dto.OrderDto;
import com.nicolasortiz.ecommerce.model.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderDto toDto(Order order);

}
