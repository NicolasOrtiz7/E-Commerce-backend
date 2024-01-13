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

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ProductStock productStock;

    // ---------------------------------------
    @PrePersist
    public void prePersist() {
        if (this.productStock == null) {
            this.productStock = new ProductStock();
            this.productStock.setQuantity(0);
            this.productStock.setProduct(this);
        }
    }
}
