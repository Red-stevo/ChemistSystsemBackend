package com.red.stevo.chemsales.repositories;

import com.red.stevo.chemsales.entities.ProductsEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository()
public interface ProductsRepository extends CrudRepository<ProductsEntity, String> {

    @Query("SELECT COUNT (*) FROM ProductsEntity ")
    Integer countItems();

    Optional<List<ProductsEntity>> findAllByProductNameContainingIgnoreCase(String searchText);

}
