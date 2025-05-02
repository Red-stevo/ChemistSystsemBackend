package com.red.stevo.chemsales.repositories;

import com.red.stevo.chemsales.entities.MedicineCategoriesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface MedicineCategoryRepository extends CrudRepository<MedicineCategoriesEntity, String> {
}
