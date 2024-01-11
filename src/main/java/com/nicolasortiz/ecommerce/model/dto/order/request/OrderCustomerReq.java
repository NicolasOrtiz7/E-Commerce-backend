package com.nicolasortiz.ecommerce.model.dto.order.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCustomerReq {

    private int customerId;
    private String email;
}
