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
    private String typeId;

    private String type;

    private Integer noOfBoxes;

    private Integer noOfPacketsPerBox;

    private Integer noOfTabletPerPacket;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk-product_type", referencedColumnName = "product_id", unique = true)
    private ProductsEntity productsEntity;

}