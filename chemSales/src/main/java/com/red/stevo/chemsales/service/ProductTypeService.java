package com.red.stevo.chemsales.service;


import com.red.stevo.chemsales.Helpers.interfaces.DataTransfer;
import com.red.stevo.chemsales.entities.ProductTypeEntity;
import com.red.stevo.chemsales.repositories.ProductsTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductTypeService {

    private final ProductsTypeRepository typeRepo;


    /**
     * @param dataTransfer -> allows us to pass the data from any class using dynamic logic;
     *                     this helps reduce the number of DTO created for data transfer.
     */
    public ProductTypeEntity saveProducts(DataTransfer<ProductTypeEntity> dataTransfer) {

        ProductTypeEntity typeEntity = dataTransfer.getDataModel();

        /*Get the product type details if already exist -> this is to ensure update in the case when the
        * type details already exist*/
        typeRepo.findTypeIdByProductsEntityAndType(typeEntity.getProductsEntity(), typeEntity.getType())
                .ifPresent(typeEntity::setTypeId);

        log.info("Update/Save the product Type details.");
        /*Save/update type entity details.*/
        return typeRepo.save(typeEntity);

    }
}
