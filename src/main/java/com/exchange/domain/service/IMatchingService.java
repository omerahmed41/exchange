package com.exchange.domain.service;


import com.exchange.domain.entity.Trade;
import com.exchange.domain.value.object.IOrderBook;

import java.util.List;

public interface IMatchingService {

    List<Trade> getTrades();
    IOrderBook getOrderBook();
    void matchOrders();

}
