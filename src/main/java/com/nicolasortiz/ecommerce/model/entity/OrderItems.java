package com.nicolasortiz.ecommerce.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_items")
public class OrderItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderItemsId;

    @NotNull
    @Column(nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "orderId")
    private Order order; // FK

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product; // FK

}
