package com.red.stevo.chemsales.controllers.searchController;

import com.red.stevo.chemsales.entities.MedicineCategoriesEntity;
import com.red.stevo.chemsales.entities.ProductsEntity;
import com.red.stevo.chemsales.models.ProductModel;
import com.red.stevo.chemsales.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@RestController()
@RequiredArgsConstructor()
@RequestMapping("/api/v1/search")
public class searchProductController {

    private final SearchService searchService;

    @GetMapping("/product")
    public ResponseEntity<Set<ProductModel>> searchProduct(
            @RequestParam("name") String productName){
        return searchService.searchProductName(productName);
    }

    @GetMapping("/category")
    public ResponseEntity<List<MedicineCategoriesEntity>> searchCategory(
            @RequestParam("name") String categoryName) {
        return searchService.searchCategories(categoryName);
    }

    @GetMapping("/gen")
    public ResponseEntity<List<Object>> searchProductNameAndCategory(
            @RequestParam("name") String name){
        return searchService.genSearch(name);
    }

}
