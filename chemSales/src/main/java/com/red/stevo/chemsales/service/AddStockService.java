package com.red.stevo.chemsales.service;

import com.red.stevo.chemsales.Helpers.classes.SaveImage;
import com.red.stevo.chemsales.Helpers.interfaces.DataTransfer;
import com.red.stevo.chemsales.entities.MedicineCategoriesEntity;
import com.red.stevo.chemsales.entities.ProductTypeEntity;
import com.red.stevo.chemsales.entities.ProductsEntity;
import com.red.stevo.chemsales.models.AddStockModel;
import com.red.stevo.chemsales.repositories.MedicineCategoryRepository;
import com.red.stevo.chemsales.repositories.ProductsRepository;
import jakarta.annotation.PostConstruct;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;

@Data
@Slf4j
@Builder
@Service
@RequiredArgsConstructor
public class AddStockService {

    private final MedicineCategoryRepository categoryRepo;

    private final ProductTypeService productTypeService;

    private final ProductsRepository productsRepo;

    private final SaveImage saveImage;

    @PostConstruct()
    public void handleDataSetup()  {
        if(productsRepo.countItems() > 100) return;

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

            log.info("setup default products to the database.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public ResponseEntity<HttpStatus> saveProduct(AddStockModel addStockModel) {

        ProductsEntity product = new ProductsEntity();

        /*handle new category and existing categories for new ad existing products.*/
        categoryRepo.findAllByCategoryName(addStockModel.getProductCategory()).ifPresentOrElse(
                product::setCategories,
                () -> {
                    MedicineCategoriesEntity medicineCategory = MedicineCategoriesEntity.
                            builder().categoryName(addStockModel.getProductCategory()).build();

                    categoryRepo.save(medicineCategory);
                    product.setCategories(medicineCategory);
                }
        );


        /*Save or update product details.*/
        if (addStockModel.getProductId() != null) {
            product.setProductId(addStockModel.getProductId());

            /*Handle image deletion if image was updated*/
            if (!productsRepo.existsAllByProductImageUrl(addStockModel.getImage()))
                saveImage.deleteImage(
                        productsRepo.findProductImageUrlByProductId(addStockModel.getProductId()));

        }

        product.setProductImageUrl(addStockModel.getImage());
        product.setProductImageUrl(addStockModel.getImage());
        product.setProductName(addStockModel.getProductName());
        product.setProductBuyingPrice(addStockModel.getBuyingPrice());
        product.setProductLocation(addStockModel.getLocation());
        product.setProductSellingPrice(addStockModel.getSellingPrice());

        productsRepo.save(product);

        /*Save or update product type details.*/
        productTypeService.saveProducts(() -> ProductTypeEntity
                .builder()
                .noOfBoxes(addStockModel.getNoOfBoxes())
                .noOfPacketsPerBox(addStockModel.getNoOfPacketsPerBox())
                .noOfTabletPerPacket(addStockModel.getNoOfTabletPerPacket())
                .build());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
