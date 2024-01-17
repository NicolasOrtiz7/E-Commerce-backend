package com.nicolasortiz.ecommerce.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank
    @Column(nullable = false)
    private String name;
    private String description;
    private String image;

    @NotNull
    @Column(nullable = false)
    private Long price;
    private Integer important;

    @NotNull
    @Column(nullable = false)
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private ProductCategory category;

    @JsonIgnore // Para que no se carguen al hacer peticion a /products/stock
    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ProductStock productStock;

}
