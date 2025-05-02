package com.red.stevo.chemsales.Entities;


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
    private String productId;

    @Size(min = 2, max = 250, message = "Please Choose a Different Product Name.")
    private String productName;

    private String productImageUrl;

    private Double productBuyingPrice;

    private Double productSellingPrice;

    @Size(max = 250, message = "Location Name Too Long.")
    private String productLocation;

}
