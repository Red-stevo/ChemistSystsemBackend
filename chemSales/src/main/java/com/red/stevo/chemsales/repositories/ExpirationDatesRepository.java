package com.red.stevo.chemsales.repositories;

import com.red.stevo.chemsales.entities.ExpirationDatesEntity;
import com.red.stevo.chemsales.entities.ProductCurrentStocksEntity;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface ExpirationDatesRepository extends CrudRepository<ExpirationDatesEntity, String> {

    Optional<ExpirationDatesEntity> findByProductCurrentStocksEntityAndExpiryDate(
            ProductCurrentStocksEntity entity, LocalDate expiryDate);
}
