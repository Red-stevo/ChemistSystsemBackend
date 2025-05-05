package com.red.stevo.chemsales.service;

import com.red.stevo.chemsales.entities.MedicineCategoriesEntity;
import com.red.stevo.chemsales.entities.ProductsEntity;
import com.red.stevo.chemsales.repositories.MedicineCategoryRepository;
import com.red.stevo.chemsales.repositories.ProductsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService {

    private final MedicineCategoryRepository categoryRepo;

    private final ProductsRepository productsRepo;

    public ResponseEntity<List<MedicineCategoriesEntity>> searchCategories(String category) {
        return new ResponseEntity<>(
                categoryRepo.findAllByCategoryNameContainingIgnoreCase(category).orElse(new ArrayList<>()),
                        HttpStatus.OK);
    }

    public ResponseEntity<List<ProductsEntity>> searchProductName(String name){
        return new ResponseEntity<>(
                productsRepo.findAllByProductNameContainingIgnoreCase(name).orElse(new ArrayList<>()),
                HttpStatus.OK);
    }

    public ResponseEntity<List<Object>> genSearch(String text) {
        log.info("General Search.");
        return new ResponseEntity<>(
                List.of(searchProductName(text), searchCategories(text)),
                HttpStatus.OK);
    }
}
