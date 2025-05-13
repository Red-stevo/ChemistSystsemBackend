package com.red.stevo.chemsales.service;

import com.red.stevo.chemsales.entities.MedicineCategoriesEntity;
import com.red.stevo.chemsales.models.ProductModel;
import com.red.stevo.chemsales.repositories.MedicineCategoryRepository;
import com.red.stevo.chemsales.repositories.ProductsRepository;
import com.red.stevo.chemsales.repositories.ProductsTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService {

    private final MedicineCategoryRepository categoryRepo;

    private final ProductsRepository productsRepo;

    private final ProductsTypeRepository productsTypeRepo;

    public ResponseEntity<List<MedicineCategoriesEntity>> searchCategories(String category) {
        return new ResponseEntity<>(
                categoryRepo.findAllByCategoryNameContainingIgnoreCase(category).orElse(new ArrayList<>()),
                        HttpStatus.OK);
    }

    public ResponseEntity<Set<ProductModel>> searchProductName(String name){

        Set<ProductModel> productModelList = new HashSet<>();

        productsTypeRepo.findAllByProductsEntity_ProductNameContainingIgnoreCase(name).ifPresent(
                entities -> {
                    entities.forEach(entity -> productModelList.add(
                            ProductModel
                                    .builder()
                                    .categories(entity.getProductsEntity().getCategories())
                                    .productBuyingPrice(entity.getProductBuyingPrice())
                                    .productSellingPrice(entity.getProductSellingPrice())
                                    .productId(entity.getProductsEntity().getProductId())
                                    .productLocation(entity.getProductLocation())
                                    .productName(entity.getProductsEntity().getProductName())
                                    .productImageUrl(entity.getProductImageUrl())
                                    .build()
                    ));
                }
        );

        productsRepo.findAllByProductNameContainingIgnoreCase(name).ifPresent(
                entities -> {
                    entities.forEach(entity -> productModelList.add(
                            ProductModel
                                    .builder()
                                    .categories(entity.getCategories())
                                    .productId(entity.getProductId())
                                    .productName(entity.getProductName())
                                    .build()
                    ));
                }
        );

        return new ResponseEntity<>(productModelList, HttpStatus.OK);

    }

    public ResponseEntity<List<Object>> genSearch(String text) {
        log.info("General Search.");
        return new ResponseEntity<>(
                List.of(searchProductName(text), searchCategories(text)),
                HttpStatus.OK);
    }
}
