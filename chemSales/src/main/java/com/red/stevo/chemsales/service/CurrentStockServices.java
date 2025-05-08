package com.red.stevo.chemsales.service;


import com.red.stevo.chemsales.repositories.CurrentStockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrentStockServices {

    private final CurrentStockRepository currentStockRepo;

    public void updateCurrentStock() {

    }
}
