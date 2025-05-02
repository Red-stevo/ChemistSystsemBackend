package com.red.stevo.chemsales.repositories;

import com.red.stevo.chemsales.entities.ProductTypeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface ProductsTypeRepository extends CrudRepository<ProductTypeEntity, String> {

}
