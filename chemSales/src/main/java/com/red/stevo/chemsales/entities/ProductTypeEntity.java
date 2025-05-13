package com.red.stevo.chemsales.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="products_types_table")
public class ProductTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "type_id")
    private String typeId;

    private String type;

    private Integer noOfBoxes;

    private Integer noOfPacketsPerBox;

    private Integer noOfTabletPerPacket;

    private String productImageUrl;

    private Double productBuyingPrice;

    private Double productSellingPrice;

    private String productLocation;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk-product_type", referencedColumnName = "product_id")
    private ProductsEntity productsEntity;

}