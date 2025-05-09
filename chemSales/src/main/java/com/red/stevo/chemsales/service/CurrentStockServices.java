package com.red.stevo.chemsales.service;


import com.red.stevo.chemsales.Helpers.interfaces.DataTransfer;
import com.red.stevo.chemsales.entities.ExpirationDatesEntity;
import com.red.stevo.chemsales.entities.ProductCurrentStocksEntity;
import com.red.stevo.chemsales.repositories.CurrentStockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrentStockServices {

    private final CurrentStockRepository currentStockRepo;

    public void updateOnRestock(DataTransfer<ExpirationDatesEntity> getDate){


    }
}
















