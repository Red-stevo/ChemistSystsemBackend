package com.red.stevo.chemsales.repositories;

import com.red.stevo.chemsales.entities.ProductTypeEntity;
import com.red.stevo.chemsales.entities.ProductsEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository()
public interface ProductsTypeRepository extends CrudRepository<ProductTypeEntity, String> {

    @Query("SELECT T.typeId FROM ProductTypeEntity T WHERE T.productsEntity =:products")
    Optional<String> findTypeIdByProductsEntity(@Param("products") ProductsEntity products);
}
