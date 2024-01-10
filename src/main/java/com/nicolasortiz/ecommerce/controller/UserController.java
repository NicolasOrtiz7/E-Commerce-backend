package com.nicolasortiz.ecommerce.controller;

import com.nicolasortiz.ecommerce.model.dto.UserDto;
import com.nicolasortiz.ecommerce.model.entity.User;
import com.nicolasortiz.ecommerce.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll(){
        return ResponseEntity.ok()
                .body(userService.findAll());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> findById(@PathVariable int userId){
        return ResponseEntity.ok()
                .body(userService.findById(userId));
    }

    @PostMapping // Este no se usará. Se usará el de seguridad para crear usuarios.
    public ResponseEntity<Void> save(@RequestBody User user){

        userService.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteById(@PathVariable int userId){

        userService.deleteById(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
