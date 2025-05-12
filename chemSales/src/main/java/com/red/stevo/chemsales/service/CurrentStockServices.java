package com.red.stevo.chemsales.service;


import com.red.stevo.chemsales.Helpers.interfaces.DataTransfer;
import com.red.stevo.chemsales.entities.ExpirationDatesEntity;
import com.red.stevo.chemsales.entities.ProductCurrentStocksEntity;
import com.red.stevo.chemsales.entities.StockHistory;
import com.red.stevo.chemsales.repositories.CurrentStockRepository;
import com.red.stevo.chemsales.repositories.ExpirationDatesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrentStockServices {

    private final CurrentStockRepository currentStockRepo;

    private final ExpirationDatesRepository expirationDatesRepo;

    private final StockHistoryService historyService;

    public void updateOnRestock(DataTransfer<ExpirationDatesEntity> getData) {
        ExpirationDatesEntity expirationDatesEntity = getData.getDataModel();

        ProductCurrentStocksEntity currentStocks = expirationDatesEntity.getProductCurrentStocksEntity();

        /*Check whether the product current stock details already exist; if so, get the id to
         *ensure that when we apply save, the entity details are updated not inserting new one.
         */
        currentStockRepo.findStockEntityByProductsEntity(currentStocks.getProductsEntity())
                .ifPresent(stockEntity -> {
                    currentStocks.setStockId(stockEntity.getStockId());
                    currentStocks.setTotalCost(currentStocks.getTotalCost() + stockEntity.getTotalCost());
                });

        currentStockRepo.save(currentStocks);
        log.info("Saved the stock details.");

        /* Saving the current stock expiration date.These may be several for a single product, hence the
         * reaction we have this entity.
         *
         * We need to check whether there is a stock
         * which is linked to the same product has the same expiration date
         * as the one we are about to add.
         * If so,We handle the update logic, get the id and update the count.
         * */
        expirationDatesRepo.findByProductCurrentStocksEntityAndExpiryDate(
                        expirationDatesEntity.getProductCurrentStocksEntity(),
                        expirationDatesEntity.getExpiryDate())
                .ifPresent(entity -> {
                    expirationDatesEntity.setExpirationDateId(entity.getExpirationDateId());
                    expirationDatesEntity.setStockCount(
                            entity.getStockCount() + expirationDatesEntity.getStockCount());
                });

        expirationDatesRepo.save(expirationDatesEntity);
        log.info("Saved the expiry dates.");

        //Save the Stock History Details.
        saveStockHistory(expirationDatesEntity);

    }

    private void saveStockHistory(ExpirationDatesEntity expirationDatesEntity) {
        /* Stock history update
        *
        * The stock history table holds the data to feed to a line chart in the frontend.
        * An instance of the stock history is the current history every time it changes.
        * Thus well will update the stock history every time the current stock changes.
        *
        * */
        historyService.saveStockHistory(() -> StockHistory
                .builder()
                .dateOfStockUpdate(LocalDateTime.now().toLocalDate())
                .productsEntity(expirationDatesEntity.getProductCurrentStocksEntity().getProductsEntity())
                .stockCount(expirationDatesEntity.getStockCount())
                .totalCost(expirationDatesEntity.getProductCurrentStocksEntity().getTotalCost())
                .build());
    }
}