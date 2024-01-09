package com.nicolasortiz.ecommerce.model.dto;

import com.nicolasortiz.ecommerce.model.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class UserDto {

    private int userId;
    private String username;
    private String email;
    private Role role;

}
