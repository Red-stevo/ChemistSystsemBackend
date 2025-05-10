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
@Table(name="products_current_stock_table")
public class ProductCurrentStocksEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="stock_id")
    private String stockId;

    @Size(message = "System Does Not Support This Amount. Please Contact Your Developer.")
    private Double totalCost;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_product_current_stock", referencedColumnName = "product_id", unique = true)
    private ProductsEntity productsEntity;

}
