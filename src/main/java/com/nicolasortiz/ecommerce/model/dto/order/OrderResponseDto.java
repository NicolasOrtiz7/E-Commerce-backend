package com.nicolasortiz.ecommerce.model.dto.order;

import com.nicolasortiz.ecommerce.model.dto.customer.CustomerDto;
import com.nicolasortiz.ecommerce.model.dto.order.response.OrderDetailsRes;
import com.nicolasortiz.ecommerce.model.dto.order.response.OrderItemsRes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor @NoArgsConstructor
public class OrderResponseDto {

    private int orderId;
    private CustomerDto customer;
    private LocalDateTime datetime;
    private List<OrderItemsRes> orderItems;
    private OrderDetailsRes orderDetails;
}
