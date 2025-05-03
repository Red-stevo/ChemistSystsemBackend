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

    @Size(max = 250, message = "The Product Type is Too Long. Product Type Not Support.Please Contact Your Developer.")
    private String type;

    @Size(message = "Invalid Count.The Count is Not Supported. Please Contact Your Developer.")
    private Integer noOfBoxes;

    @Size(message = "Invalid Count.The Count is Not Supported.Please Contact Your Developer.")
    private Integer noOfPacketsPerBox;

    @Size(message = "Invalid Count.The Count is Not Supported.Please Contact Your Developer.")
    private Integer noOfTabletPerPacket;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk-product_type", referencedColumnName = "product_id")
    private ProductsEntity productsEntity;

}