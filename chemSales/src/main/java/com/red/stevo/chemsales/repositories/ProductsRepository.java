package com.red.stevo.chemsales.repositories;

import com.red.stevo.chemsales.entities.ProductsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface ProductsRepository extends CrudRepository<ProductsEntity, String> {

}
