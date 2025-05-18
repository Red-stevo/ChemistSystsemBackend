package com.red.stevo.chemsales.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder()
@AllArgsConstructor
public class ProductDetailsModel {

    private String productName;

    private String productId;

    private String categoryName;

    private String type;

    private Integer noOfPacketsPerBox;

    private Integer noOfTabletPerPacket;

    private Double sellingPrice;

    private Integer stockCount;


}
