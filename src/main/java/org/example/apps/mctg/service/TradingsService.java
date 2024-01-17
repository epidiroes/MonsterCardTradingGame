package org.example.apps.mctg.service;

import org.example.apps.mctg.entity.Trade;
import org.example.apps.mctg.repository.TradingsRepository;

import java.util.List;

public class TradingsService {
    private final TradingsRepository tradingsRepository;
    public TradingsService(TradingsRepository tradingsRepository) {
        this.tradingsRepository = tradingsRepository;
    }

    public List<Trade> readAll() {
        return tradingsRepository.findAll();
    }

    public Trade find(String id) {
        return tradingsRepository.findById(id);
    }

    public Trade create(Trade trade) {
        return tradingsRepository.save(trade);
    }

    public void delete(String id) {
        tradingsRepository.delete(id);
    }


}
