package com.red.stevo.chemsales.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Objects;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HomeRowDataModel {

    private String productId;

    private String productName;

    private String productCategory;

    private StockStatus stockStatus;

    private String type;

    private Map<String, String> priceDetails;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        HomeRowDataModel dataModel = (HomeRowDataModel) o;

        return Objects.equals(getProductId(), dataModel.getProductId())
                && Objects.equals(getType(), dataModel.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductId(), getType());
    }

}
