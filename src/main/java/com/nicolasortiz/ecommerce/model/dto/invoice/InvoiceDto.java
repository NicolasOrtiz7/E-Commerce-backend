package com.nicolasortiz.ecommerce.model.dto.invoice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class InvoiceDto {

    private int productId;
    private String name; // productName
    private int quantity; // orderItems quantity
    private Long price; // productPrice
    private Long subtotal; // quantity * price
    private Long total; // orderDetails total

}
