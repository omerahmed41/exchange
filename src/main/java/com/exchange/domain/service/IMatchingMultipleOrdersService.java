package com.exchange.domain.service;

import com.exchange.domain.entity.Order;
import com.exchange.domain.entity.Trade;

import java.util.List;
import java.util.Map;

public interface IMatchingMultipleOrdersService extends IMatchingService {
    Map<String, Object> addMultipleOrdersReturnOrderBookWithTrades(List<Order> orders);
    void addOrder(Order order);
    List<Trade> addMultipleOrders(List<Order> orders);

}
