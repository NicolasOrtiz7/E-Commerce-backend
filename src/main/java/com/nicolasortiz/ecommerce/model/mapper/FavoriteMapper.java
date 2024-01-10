package com.nicolasortiz.ecommerce.model.mapper;

import com.nicolasortiz.ecommerce.model.dto.FavoriteDto;
import com.nicolasortiz.ecommerce.model.entity.CustomerFavorites;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FavoriteMapper {

    FavoriteMapper INSTANCE = Mappers.getMapper(FavoriteMapper.class);

    FavoriteDto toDto(CustomerFavorites customerFavorites);

}
