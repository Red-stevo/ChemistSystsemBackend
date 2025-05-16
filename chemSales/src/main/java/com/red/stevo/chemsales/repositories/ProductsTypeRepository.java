package com.red.stevo.chemsales.repositories;

import com.red.stevo.chemsales.entities.ProductTypeEntity;
import com.red.stevo.chemsales.entities.ProductsEntity;
import com.red.stevo.chemsales.models.ProductDetailsModel;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository()
public interface ProductsTypeRepository extends PagingAndSortingRepository<ProductTypeEntity, String> {

    @Query("SELECT T.typeId FROM ProductTypeEntity T WHERE T.productsEntity =:products AND T.type =:type")
    Optional<String> findTypeIdByProductsEntityAndType(
            @Param("products") ProductsEntity products, @Param("type") String type);


    Optional<List<ProductTypeEntity>> findAllByProductsEntity_ProductNameContainingIgnoreCase(String searchText);


    @Query(
            value =
"""
SELECT T.productsEntity.productName AS productName,
T.productsEntity.productId AS productId,
T.productsEntity.categories.categoryName as catergoryName,
T.noOfBoxes,
T.noOfPacketsPerBox,
T.noOfTabletPerPacket,
T.productSellingPrice as sellingPrice,
V.stockCount FROM ProductTypeEntity T JOIN ProductCurrentStocksEntity U ON
U.productTypeEntity.typeId = T.typeId
JOIN ExpirationDatesEntity V ON V.productCurrentStocksEntity.stockId = U.stockId
WHERE (:filter IS NULL OR  LOWER(T.productsEntity.productName) LIKE LOWER(CONCAT('%', :filter, '%'))
OR LOWER(T.productsEntity.categories.categoryName) LIKE LOWER(CONCAT('%', :filter, '%') ))
ORDER BY T.productsEntity.productName asc
""",
            countQuery =
"""
SELECT COUNT (*) FROM ProductTypeEntity T JOIN ProductCurrentStocksEntity U ON
U.productTypeEntity.typeId = T.typeId
JOIN ExpirationDatesEntity V ON V.productCurrentStocksEntity.stockId = U.stockId
WHERE (:filter IS NULL OR  LOWER(T.productsEntity.productName) LIKE LOWER(CONCAT('%', :filter, '%'))\s
OR LOWER(T.productsEntity.categories.categoryName) LIKE LOWER(CONCAT('%', :filter, '%') ))
"""
    )
    Page<ProductDetailsModel> findPageableDisplayProducts(@Param("filter") String filter, Pageable pageable);
}
