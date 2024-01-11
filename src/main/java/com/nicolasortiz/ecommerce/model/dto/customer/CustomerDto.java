package com.nicolasortiz.ecommerce.model.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class CustomerDto {

    private int customerId;
    private String name;
    private String email;
    private String phone;

}
