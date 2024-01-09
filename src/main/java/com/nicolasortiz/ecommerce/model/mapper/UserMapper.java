package com.nicolasortiz.ecommerce.model.mapper;

import com.nicolasortiz.ecommerce.model.dto.UserDto;
import com.nicolasortiz.ecommerce.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toDto(User user);

}
