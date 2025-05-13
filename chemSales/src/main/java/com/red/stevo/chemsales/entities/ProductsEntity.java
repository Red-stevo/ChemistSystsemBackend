package com.red.stevo.chemsales.entities;


import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="products_table")
public class ProductsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id")
    private String productId;

    private String productName;

    @ManyToOne()
    @JoinColumn(name = "fk_product_category", referencedColumnName = "category_id")
    private MedicineCategoriesEntity categories;

}
