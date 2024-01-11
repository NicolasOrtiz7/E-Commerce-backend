package com.nicolasortiz.ecommerce.model.dto.order;


import com.nicolasortiz.ecommerce.model.dto.order.request.OrderCustomerReq;
import com.nicolasortiz.ecommerce.model.dto.order.request.OrderDetailsReq;
import com.nicolasortiz.ecommerce.model.dto.order.request.OrderItemsReq;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {

    private OrderCustomerReq customer;
    private LocalDateTime datetime;
    private List<OrderItemsReq> orderItems;
    private OrderDetailsReq orderDetails;
}
