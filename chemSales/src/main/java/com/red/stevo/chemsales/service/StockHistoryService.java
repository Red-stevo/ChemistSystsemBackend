package com.red.stevo.chemsales.service;

import com.red.stevo.chemsales.Helpers.interfaces.DataTransfer;
import com.red.stevo.chemsales.entities.StockHistory;
import com.red.stevo.chemsales.repositories.StockHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockHistoryService {

    private final StockHistoryRepository stockHistoryRepo;


    /**
     *
     * @param getData -> is a functional interface instance passed by the caller.
     *                it defines the logic that populates and returns the stock history object.
     */
    public void saveStockHistory(DataTransfer<StockHistory> getData) {
        stockHistoryRepo.save(getData.getDataModel());
    }
}
