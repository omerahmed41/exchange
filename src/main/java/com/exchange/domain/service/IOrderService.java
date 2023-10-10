package com.exchange.domain.service;

import com.exchange.domain.entity.Order;

import java.util.List;

public interface IOrderService {
    Order createOrder(Order order);

    List<Order> getOrdersHistory();
}
