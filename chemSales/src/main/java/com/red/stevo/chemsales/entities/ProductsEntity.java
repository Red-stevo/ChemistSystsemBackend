package com.red.stevo.chemsales.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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

    @Size(min = 2, max = 250, message = "Please Choose a Different Product Name.")
    @Column(unique = true)
    private String productName;

    @Size(message = "System Does Not Support This Amount. Please Contact Your Developer.")
    private String productImageUrl;

    @Size(message = "System Does Not Support This Amount. Please Contact Your Developer.")
    private Double productBuyingPrice;

    @Size(message = "System Does Not Support This Amount. Please Contact Your Developer.")
    private Double productSellingPrice;

    @Size(max = 250, message = "Location Name Too Long.")
    private String productLocation;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "fk_product_category", referencedColumnName = "category_id")
    private MedicineCategoriesEntity categories;

}
