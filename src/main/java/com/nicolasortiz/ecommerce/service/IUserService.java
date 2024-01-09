package com.nicolasortiz.ecommerce.service;

import com.nicolasortiz.ecommerce.model.dto.UserDto;
import com.nicolasortiz.ecommerce.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    List<UserDto> findAll();

    UserDto findById(int id);

    UserDto save(User user);

    UserDto deleteById(int id);

}
