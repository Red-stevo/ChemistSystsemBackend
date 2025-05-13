package com.red.stevo.chemsales.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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
@Table(name="products_stock_history_table")
public class StockHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String stockId;

    private Double totalCost;

    private Integer stockCount;

    private LocalDate dateOfStockUpdate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_stock_history_product", referencedColumnName = "product_id")
    private ProductsEntity productsEntity;

}

