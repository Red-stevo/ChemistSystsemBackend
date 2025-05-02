package com.red.stevo.chemsales.repositories;

import com.red.stevo.chemsales.entities.StockHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface StockHistoryRepository extends CrudRepository<StockHistory, String> {

}
