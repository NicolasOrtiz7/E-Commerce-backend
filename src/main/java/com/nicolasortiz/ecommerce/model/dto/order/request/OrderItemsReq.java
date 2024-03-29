package com.nicolasortiz.ecommerce.model.dto.order.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemsReq {

    private int quantity;
    private int productId;
}
