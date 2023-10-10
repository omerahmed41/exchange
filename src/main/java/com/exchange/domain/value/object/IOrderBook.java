package com.exchange.domain.value.object;

import com.exchange.domain.entity.Order;

import java.util.PriorityQueue;

public interface IOrderBook extends Cloneable {
    void addOrderToOrderBook(Order order);

    PriorityQueue<Order> getBuyOrders();

    PriorityQueue<Order> getSellOrders();

    IOrderBook clone();
}
