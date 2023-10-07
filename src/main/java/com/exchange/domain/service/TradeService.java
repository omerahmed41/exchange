package com.exchange.domain.service;

import com.exchange.domain.entity.Order;
import com.exchange.domain.entity.Trade;
import com.exchange.infrastructure.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public final class TradeService {

    @Autowired
    private TradeRepository tradeRepository;

    public List<Trade> getAllTrades() {
        return tradeRepository.findAll();
    }

    public Trade createTrade(Order buyOrder, Order sellOrder, int tradePrice, int tradeQuantity) {
        Trade trade = new Trade(UUID.randomUUID().toString(),
                buyOrder.getOrderId(), sellOrder.getOrderId(), tradePrice, tradeQuantity);
        return tradeRepository.save(trade);
    }

    public List<String> formatTrades(List<Trade> trades) {
        return trades.stream()
                .map(trade -> String.format("trade %d,%d,%d,%d",
                        trade.getAggressorOrderId(),
                        trade.getRestingOrderId(),
                        trade.getTradePrice(),
                        trade.getTradeQuantity()))
                .collect(Collectors.toList());
    }
    public String formatTradesString(List<Trade> trades) {
        StringBuilder sb = new StringBuilder();
        for (Trade trade : trades) {
            sb.append("trade ");
            sb.append(trade.getAggressorOrderId()).append(",");
            sb.append(trade.getRestingOrderId()).append(",");
            sb.append(trade.getTradePrice()).append(",");
            sb.append(trade.getTradeQuantity());
            sb.append("\n");  // Add newline to separate each trade
        }
        return sb.toString();
    }

}
