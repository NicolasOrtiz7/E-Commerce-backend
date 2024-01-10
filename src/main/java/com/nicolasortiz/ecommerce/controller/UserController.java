package com.nicolasortiz.ecommerce.controller;

import com.nicolasortiz.ecommerce.model.dto.ResponseDto;
import com.nicolasortiz.ecommerce.model.entity.User;
import com.nicolasortiz.ecommerce.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @GetMapping
    public ResponseEntity<ResponseDto> findAll(){
        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .message("Usuarios encontrados")
                        .response(userService.findAll())
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> findById(@PathVariable int id){
        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .message("Usuario encontrado")
                        .response(userService.findById(id))
                        .build());
    }

    @PostMapping // Este no se usará. Se usará el de seguridad para crear usuarios.
    public ResponseEntity<ResponseDto> save(@RequestBody User user){

        userService.save(user);
        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .message("Usuario guardado correctamente")
                        .response("OK")
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteById(@PathVariable int id){

        userService.deleteById(id);
        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .message("Usuario eliminado correctamente")
                        .response("OK")
                        .build());
    }

}
