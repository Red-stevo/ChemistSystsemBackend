package com.red.stevo.chemsales.service;


import com.red.stevo.chemsales.models.*;
import com.red.stevo.chemsales.repositories.ProductsTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductDetailsService {

    private final ProductsTypeRepository productsTypeRepo;

    public ResponseEntity<HomePageDetailsModel> getProductDetails(PageData pageData) {
        log.info("Getting product details.");

        Pageable pageable = PageRequest.of(pageData.getPage(), pageData.getSize());


        Page<ProductDetailsModel> products = productsTypeRepo
                .findPageableDisplayProducts(pageData.getFilter(), pageable);

        System.out.println(products);

        //Process the page to our desired model format and necessary fields.
        HomePageDetailsModel detailsModel = new HomePageDetailsModel();
        detailsModel.setTotalPages(products.getTotalPages());
        detailsModel.setTotalCount(products.getNumberOfElements());

        List<HomeRowDataModel> homeRowData = new ArrayList<>();

        products.get().forEach(product -> {

            Map<String, String> priceMap = getMap(product);

            homeRowData.add(HomeRowDataModel
                    .builder()
                    .productId(product.getProductId())
                    .productName(product.getProductName())
                    .productCategory(product.getCategoryName())
                    .priceDetails(priceMap)
                    .stockStatus(determineStatus(product.getProductId()))
                    .build());

        });

        detailsModel.setHomeRowDataList(homeRowData);

        return new ResponseEntity<>(detailsModel, HttpStatus.OK);
    }


    private Map<String, String> getMap(ProductDetailsModel product) {
        Map<String, String> priceMap = new HashMap<>();
        DecimalFormat format = new DecimalFormat("#.00");

        if (product.getType().equalsIgnoreCase("general")) {
            priceMap.put("Price Per Box", "KSH " + product.getSellingPrice());
            priceMap.put("Price Per Item", "KSH " + format
                    .format((product.getSellingPrice() / product.getNoOfPacketsPerBox())));

        } else {
            priceMap.put("Price Per Box", "KSH " + product.getSellingPrice());
            priceMap.put("Price Per Packet", "KSH " + (
                    format.format((product.getSellingPrice() / product.getNoOfPacketsPerBox()))
            ));
            priceMap.put("Price Per Tablet", "KSH " + (
                            format.format((product
                                                     .getSellingPrice() / product
                                                     .getNoOfPacketsPerBox()) / product.getNoOfTabletPerPacket()
                                    )
                    )
            );
        }
        return priceMap;
    }


    /**
     *
     * @param productId -> help get details about the product.
     *                  What I have in mind is to take the rate at which the product has sold,
     *                  then use that to estimate when the product stock will get depleted
     *                  from that we can give a status of a product.
     * @return I return an ENUM -> present one of the three possible statuses.
     */
    private StockStatus determineStatus(String productId) {

        return StockStatus.IN_STOCK;
    }
}
