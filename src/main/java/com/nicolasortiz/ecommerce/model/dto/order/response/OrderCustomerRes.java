package com.nicolasortiz.ecommerce.model.dto.order.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class OrderCustomerRes {

    private int customerId;
    private String email;

}
