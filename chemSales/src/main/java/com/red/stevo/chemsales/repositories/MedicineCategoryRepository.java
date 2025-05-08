package com.red.stevo.chemsales.repositories;

import com.red.stevo.chemsales.entities.MedicineCategoriesEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository()
public interface MedicineCategoryRepository extends CrudRepository<MedicineCategoriesEntity, String> {


    Boolean existsAllByCategoryName(String categoryName);

    Optional<MedicineCategoriesEntity> findAllByCategoryName(String productCategory);

    Optional<List<MedicineCategoriesEntity>> findAllByCategoryNameContainingIgnoreCase(String searchText);
}
