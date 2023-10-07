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

    @Autowired
    private TradeRepository tradeRepository;

    public List<Trade> getAllTrades() {
        return tradeRepository.findAll();
    }

    public void createTrade(Order buyOrder, Order sellOrder, int tradePrice, int tradeQuantity) {
        Trade trade = new Trade(UUID.randomUUID().toString(),
                buyOrder.getOrderId(), sellOrder.getOrderId(), tradePrice, tradeQuantity);
        tradeRepository.save(trade);
    }

}
