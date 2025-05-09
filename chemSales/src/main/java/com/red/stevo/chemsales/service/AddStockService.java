package com.red.stevo.chemsales.service;

import com.red.stevo.chemsales.Helpers.classes.SaveImage;
import com.red.stevo.chemsales.Helpers.interfaces.DataTransfer;
import com.red.stevo.chemsales.entities.*;
import com.red.stevo.chemsales.models.AddStockModel;
import com.red.stevo.chemsales.repositories.MedicineCategoryRepository;
import com.red.stevo.chemsales.repositories.ProductsRepository;
import jakarta.annotation.PostConstruct;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.misc.Pair;
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

    private final CurrentStockServices currentStockServices;

    private final ProductsRepository productsRepo;

    private final SaveImage saveImage;

    @PostConstruct()
    public void handleDataSetup() {
        if (productsRepo.countItems() > 100) return;

        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/setupData/categoryInit.txt"))) {
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

        /*Save product type details.*/
        saveProductTypeDetails(addStockModel);

        /*Save Product current stock details.*/
        updateCurrentStock(addStockModel, product);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void saveProductTypeDetails(AddStockModel addStockModel) {
        /*Save or update product type details.*/
        productTypeService.saveProducts(() -> ProductTypeEntity
                .builder()
                .noOfBoxes(addStockModel.getNoOfBoxes())
                .noOfPacketsPerBox(addStockModel.getNoOfPacketsPerBox())
                .noOfTabletPerPacket(addStockModel.getNoOfTabletPerPacket())
                .build());
    }

    private void updateCurrentStock(AddStockModel addStockModel, ProductsEntity product) {
        /*Saving the expiration details.*/
        currentStockServices.updateOnRestock(() -> {

            /*Prepare the current stock entity class.*/
            ProductCurrentStocksEntity currentStocksEntity = ProductCurrentStocksEntity
                    .builder()
                    .productsEntity(product)
                    /*Calculating product total cost.The selling price passed
                     from the frontend is the unit price i.e., price per box so the total cost,
                    (am sure you can go that. mmmh?)
                    */
                    .totalCost(addStockModel.getSellingPrice() * addStockModel.getNoOfBoxes())
                    .build();


            /* Calculating the Number of products.
               This one is very easy -> number of boxes * number of packets.
               The above formulae will work for general type products
               For Tablet type product will have to multiply the number of tablets per packet.
             */

            int productsCount = addStockModel.getNoOfBoxes() * addStockModel.getNoOfPacketsPerBox();

            if (addStockModel.getType().equalsIgnoreCase("Tablet"))
                productsCount *= addStockModel.getNoOfTabletPerPacket();

            return ExpirationDatesEntity
                    .builder()
                    .productCurrentStocksEntity(currentStocksEntity)
                    .expiryDate(addStockModel.getExpiryDate())
                    .stockCount(productsCount)
                    .build();
        });
    }
}
