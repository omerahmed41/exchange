package com.exchange.domain.service;

import com.exchange.domain.entity.Order;
import com.exchange.domain.entity.Trade;
import com.exchange.infrastructure.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public final class TradeService {


    private final TradeRepository tradeRepository;

    @Autowired
    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    public List<Trade> getAllTrades() {
        return tradeRepository.findAll();
    }

    public Trade createTrade(Order buyOrder, Order sellOrder, int tradePrice, int tradeQuantity) {
        Trade trade = Trade.builder()
                .tradeId(UUID.randomUUID().toString())
                .aggressorOrderId(buyOrder.getOrderId())
                .restingOrderId(sellOrder.getOrderId())
                .tradePrice(tradePrice)
                .tradeQuantity(tradeQuantity)
                .build();

        return tradeRepository.save(trade);
    }



}
