package com.red.stevo.chemsales.models;


import com.red.stevo.chemsales.entities.MedicineCategoriesEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

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


    @Override
    public boolean equals(Object newObj) {
        if (newObj == null || getClass() != newObj.getClass()) return false;
        ProductModel productModel = (ProductModel) newObj;
        return Objects.equals(this.productId, productModel.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.productId);
    }
}