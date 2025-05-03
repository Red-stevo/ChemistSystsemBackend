package com.red.stevo.chemsales.service;

import com.red.stevo.chemsales.entities.MedicineCategoriesEntity;
import com.red.stevo.chemsales.entities.ProductsEntity;
import com.red.stevo.chemsales.repositories.MedicineCategoryRepository;
import com.red.stevo.chemsales.repositories.ProductsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService {

    private final MedicineCategoryRepository categoryRepo;

    private final ProductsRepository productsRepo;

    public List<MedicineCategoriesEntity> searchCategories(String category) {
        return categoryRepo.findAllByCategoryNameContainingIgnoreCase(category).orElse(new ArrayList<>());
    }

    public List<ProductsEntity> searchProductName(String name){
        return productsRepo.findAllByProductNameContainingIgnoreCase(name).orElse(new ArrayList<>());
    }

    public List<Object> genSearch(String text) {
        log.info("General Search.");
        return List.of(searchProductName(text), searchCategories(text));
    }
}
