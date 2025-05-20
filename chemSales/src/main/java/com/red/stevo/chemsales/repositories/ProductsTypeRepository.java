package com.red.stevo.chemsales.repositories;

import com.red.stevo.chemsales.entities.ProductTypeEntity;
import com.red.stevo.chemsales.entities.ProductsEntity;
import com.red.stevo.chemsales.models.ProductDetailsModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductsTypeRepository extends JpaRepository<ProductTypeEntity, String> {

    @Query("SELECT T.typeId FROM ProductTypeEntity T WHERE T.productsEntity =:products AND T.type =:type")
    Optional<String> findTypeIdByProductsEntityAndType(
            @Param("products") ProductsEntity products, @Param("type") String type);


    Optional<List<ProductTypeEntity>> findAllByProductsEntity_ProductNameContainingIgnoreCase(String searchText);


    @Query(
            value =
"""
SELECT new com.red.stevo.chemsales.models.ProductDetailsModel(T.productsEntity.productName,
T.productsEntity.productId,
T.productsEntity.categories.categoryName,
T.type,
T.noOfPacketsPerBox,
T.noOfTabletPerPacket,
T.productSellingPrice ) FROM ProductTypeEntity T
WHERE (:filter IS NULL OR  LOWER(T.productsEntity.productName) LIKE LOWER(CONCAT('%', :filter, '%'))
OR LOWER(T.productsEntity.categories.categoryName) LIKE LOWER(CONCAT('%', :filter, '%') ))
ORDER BY T.productsEntity.productName
""",
            countQuery =
"""
SELECT COUNT (*) FROM ProductTypeEntity T
WHERE (:filter IS NULL OR  LOWER(T.productsEntity.productName) LIKE LOWER(CONCAT('%', :filter, '%'))
OR LOWER(T.productsEntity.categories.categoryName) LIKE LOWER(CONCAT('%', :filter, '%') ))
"""
    )
    Page<ProductDetailsModel> findPageableDisplayProducts(@Param("filter") String filter, Pageable pageable);

}
