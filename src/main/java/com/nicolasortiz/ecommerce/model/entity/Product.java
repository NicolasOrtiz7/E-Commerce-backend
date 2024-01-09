package com.nicolasortiz.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;
    private String name;
    private String description;
    private String image;
    private Long price;
    private Integer important;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private ProductCategory category;

}
