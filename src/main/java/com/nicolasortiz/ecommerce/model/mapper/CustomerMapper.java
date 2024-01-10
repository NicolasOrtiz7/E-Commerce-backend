package com.nicolasortiz.ecommerce.model.mapper;

import com.nicolasortiz.ecommerce.model.dto.CustomerDto;
import com.nicolasortiz.ecommerce.model.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerDto toDto(Customer customer);

}
