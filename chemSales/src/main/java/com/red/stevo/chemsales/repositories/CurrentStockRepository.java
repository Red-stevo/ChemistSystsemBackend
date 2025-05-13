package com.red.stevo.chemsales.repositories;

import com.red.stevo.chemsales.entities.ProductCurrentStocksEntity;
import com.red.stevo.chemsales.entities.ProductTypeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository()
public interface CurrentStockRepository extends CrudRepository<ProductCurrentStocksEntity, String> {

    @Query("SELECT T FROM ProductCurrentStocksEntity T WHERE T.productTypeEntity.typeId =:typeId")
    Optional<ProductCurrentStocksEntity> findStockEntityByProductTypeEntityId(
            @Param("typeId") String typeId);

}
