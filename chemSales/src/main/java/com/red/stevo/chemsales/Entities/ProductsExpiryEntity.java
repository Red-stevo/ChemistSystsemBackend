package com.red.stevo.chemsales.Entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="products_types_table")
public class ProductsExpiryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String expiryId;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate expiryDate;

}
