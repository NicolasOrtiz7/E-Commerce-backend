package com.nicolasortiz.ecommerce.model.dto.order.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class OrderDetailsReq {

    // Datos de pago
    private String payment;

    // Datos de envío
    private String city;
    private String address;
}
