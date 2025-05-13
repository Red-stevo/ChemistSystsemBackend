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
@Table(name = "expiration_dates_table")
public class ExpirationDatesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String expirationDateId;

    private LocalDate expiryDate;

    private Integer stockCount;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "fk_expirations_date_current_stock", referencedColumnName = "stock_id")
    private ProductCurrentStocksEntity productCurrentStocksEntity;

}
