package com.red.stevo.chemsales.service;

import com.red.stevo.chemsales.Helpers.classes.SaveImage;
import com.red.stevo.chemsales.entities.*;
import com.red.stevo.chemsales.models.AddStockModel;
import com.red.stevo.chemsales.repositories.MedicineCategoryRepository;
import com.red.stevo.chemsales.repositories.ProductsRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
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

    private final CurrentStockServices currentStockServices;

    private final ProductsRepository productsRepo;

    private final SaveImage saveImage;

    @PostConstruct()
    public void handleDataSetup() {
        log.info("Init method called but cause error later....");
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


    @Transactional()
    public ResponseEntity<HttpStatus> saveProduct(AddStockModel addStockModel) {
        ProductsEntity product = productsRepo
                .findById(addStockModel.getProductId()).orElse(new ProductsEntity());


        /*handle new category and existing categories for new ad existing products.*/
        categoryRepo.findAllByCategoryName(addStockModel.getProductCategory()).ifPresentOrElse(
                product::setCategories,
                () -> {
                    MedicineCategoriesEntity medicineCategory = MedicineCategoriesEntity.
                            builder().categoryName(addStockModel.getProductCategory()).build();

                    medicineCategory = categoryRepo.save(medicineCategory);
                    product.setCategories(medicineCategory);
                }
        );

        product.setProductName(addStockModel.getProductName());

        /*Save product type details.*/

        /*Save Product current stock details.*/
        updateCurrentStock(addStockModel, saveProductTypeDetails(addStockModel, productsRepo.save(product)));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private ProductTypeEntity saveProductTypeDetails(AddStockModel addStockModel, ProductsEntity product) {
        /*Save or update product type details.*/
        return productTypeService.saveProducts(() -> ProductTypeEntity
                .builder()
                .noOfBoxes(addStockModel.getNoOfBoxes())
                .noOfPacketsPerBox(addStockModel.getNoOfPacketsPerBox())
                .noOfTabletPerPacket(addStockModel.getNoOfTabletPerPacket())
                .productsEntity(product)
                .type(addStockModel.getType())
                .productBuyingPrice(addStockModel.getBuyingPrice())
                .productSellingPrice(addStockModel.getSellingPrice())
                .productImageUrl(addStockModel.getImage())
                .productLocation(addStockModel.getLocation())
                .build());
    }

    private void updateCurrentStock(AddStockModel addStockModel, ProductTypeEntity typeEntity) {
        /*Saving the expiration details.*/
        currentStockServices.updateOnRestock(() -> {

            /*Prepare the current stock entity class.*/
            ProductCurrentStocksEntity currentStocksEntity = ProductCurrentStocksEntity
                    .builder()
                    .productTypeEntity(typeEntity)
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
