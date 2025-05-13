package com.red.stevo.chemsales.models;


import com.red.stevo.chemsales.entities.MedicineCategoriesEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductModel {

    private String productId;

    private String productName;

    private String productImageUrl;

    private Double productBuyingPrice;

    private Double productSellingPrice;

    private String productLocation;

    private MedicineCategoriesEntity categories;

}