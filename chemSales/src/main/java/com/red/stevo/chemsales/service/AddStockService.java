package com.red.stevo.chemsales.service;

import com.red.stevo.chemsales.entities.MedicineCategoriesEntity;
import com.red.stevo.chemsales.entities.ProductsEntity;
import com.red.stevo.chemsales.repositories.MedicineCategoryRepository;
import com.red.stevo.chemsales.repositories.ProductsRepository;
import jakarta.annotation.PostConstruct;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;

@Data
@Slf4j
@Builder
@Service
@RequiredArgsConstructor
public class AddStockService {

    private final MedicineCategoryRepository categoryRepo;

    private final ProductsRepository productsRepo;

    @PostConstruct()
    public void handleDataSetup()  {
        if(categoryRepo.countItems() > 100) return;

        try(BufferedReader br = new BufferedReader(new FileReader("src/main/resources/setupData/categoryInit.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] arr = line.split(", ");
                String productCategory = arr[0], productName = arr[1];
                categoryRepo.findAllByCategoryName(productCategory).ifPresentOrElse(
                        (item) -> productsRepo.save(ProductsEntity
                                .builder()
                                .productName(productName)
                                .categories(item)
                                .build()
                        ),
                        () -> {

                            MedicineCategoriesEntity categoriesEntity =
                                    categoryRepo.save(MedicineCategoriesEntity
                                            .builder()
                                            .categoryName(productCategory)
                                            .build());

                            productsRepo.save(ProductsEntity
                                    .builder()
                                    .productName(productName)
                                    .categories(categoriesEntity)
                                    .build());
                        }
                );

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
