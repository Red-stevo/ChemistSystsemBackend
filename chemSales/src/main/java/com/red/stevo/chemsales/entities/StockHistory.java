package com.red.stevo.chemsales.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "fk_stock_history_type", referencedColumnName = "type_id")
    private ProductTypeEntity productTypeEntity;

}

