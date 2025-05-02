package com.red.stevo.chemsales.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @GeneratedValue
    private String typeId;

    @Size(max = 250, message = "The Product Type is Too Long. Product Type Not Support.")
    private String type;

    @Size(message = "Invalid Count.The Count is Not Supported")
    private Integer noOfBoxes;

    @Size(message = "Invalid Count.The Count is Not Supported")
    private Integer noOfPacketsPerBox;

    @Size(message = "Invalid Count.The Count is Not Supported")
    private Integer noOfTabletPerPacket;

}
