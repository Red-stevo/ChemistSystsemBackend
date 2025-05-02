package com.red.stevo.chemsales.repositories;

import com.red.stevo.chemsales.entities.ProductCurrentStocks;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface CurrentStockRepository extends CrudRepository<ProductCurrentStocks, String> {
}
