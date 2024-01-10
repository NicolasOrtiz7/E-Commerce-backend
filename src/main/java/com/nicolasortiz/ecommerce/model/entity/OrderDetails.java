package com.nicolasortiz.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_details")
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderDetailsId;

    // Datos de pago
    private Long total;
    private String payment;

    // Datos de envío
    private String city;
    private String address;

    @OneToOne
    @JoinColumn(name = "orderId")
    private Order order; // FK

}
