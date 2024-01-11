package com.nicolasortiz.ecommerce.model.dto.order.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class OrderDetailsRes {

    // Datos de pago
    private Long total;
    private String payment;

    // Datos de envío
    private String city;
    private String address;
}
