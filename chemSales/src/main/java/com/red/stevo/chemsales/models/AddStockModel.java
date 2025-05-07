package com.red.stevo.chemsales.models;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;


import java.time.LocalDate;

@Data
@Builder
public class AddStockModel {

    private String productId;

    private String image;

    private String productName;

    private String productCategory;

    private String location;

    private LocalDate expiryDate;

    private Double buyingPrice;

    private Double sellingPrice;

    private String type;

    private Integer noOfBoxes;

    private Integer noOfPacketsPerBox;

    private Integer noOfTabletPerPacket;

}
