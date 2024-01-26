package com.nicolasortiz.ecommerce.service;

import com.nicolasortiz.ecommerce.model.dto.UserDto;
import com.nicolasortiz.ecommerce.model.entity.User;

import java.util.List;

public interface IUserService {

    List<UserDto> findAll();

    UserDto findById(int id);

    void save(User user);

    void deleteById(int id);

}
