package com.nicolasortiz.ecommerce.service.impl;

import com.nicolasortiz.ecommerce.exception.MyNotFoundException;
import com.nicolasortiz.ecommerce.model.dto.UserDto;
import com.nicolasortiz.ecommerce.model.entity.User;
import com.nicolasortiz.ecommerce.model.mapper.UserMapper;
import com.nicolasortiz.ecommerce.repository.IUserRepository;
import com.nicolasortiz.ecommerce.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;

    @Override
    public List<UserDto> findAll() {
        List<User> userList = userRepository.findAll();
        return userList.stream()
                .map(UserMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findById(int id) {
        Optional<User> userFound = Optional.ofNullable(userRepository.findById(id)
                .orElseThrow(() -> new MyNotFoundException("Usuario no encontrado")));
        return UserMapper.INSTANCE.toDto(userFound.get());
    }

    @Override
    public void save(User user) {
        findById(user.getUserId());
        userRepository.save(user);
    }

    @Override
    public void deleteById(int id) {
        findById(id);
        userRepository.deleteById(id);
    }
}
