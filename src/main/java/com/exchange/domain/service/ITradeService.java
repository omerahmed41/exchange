package com.exchange.domain.service;

import com.exchange.domain.entity.Order;
import com.exchange.domain.entity.Trade;

import java.util.List;

public interface ITradeService {
    List<Trade> getAllTrades();

    Trade createTrade(Order buyOrder, Order sellOrder, int tradePrice, int tradeQuantity);
}
