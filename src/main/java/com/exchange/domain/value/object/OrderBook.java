package com.exchange.domain.value.object;

import com.exchange.domain.entity.Order;

import java.util.PriorityQueue;
import java.util.Comparator;


public final class OrderBook implements Cloneable {

    private PriorityQueue<Order> buyOrders;
    private PriorityQueue<Order> sellOrders;

    public OrderBook() {
        buyOrders = new PriorityQueue<>(Comparator.comparing(Order::getPrice).reversed()
                .thenComparing(Order::getTimestamp));
        sellOrders = new PriorityQueue<>(Comparator.comparing(Order::getPrice)
                .thenComparing(Order::getTimestamp));
    }

    public void addOrder(Order order) {
        if (order.getSide() == 'B') {
            buyOrders.add(order);
        } else if (order.getSide() == 'S') {
            sellOrders.add(order);
        }
    }



    public PriorityQueue<Order> getBuyOrders() {
        return buyOrders;
    }

    public PriorityQueue<Order> getSellOrders() {
        return sellOrders;
    }

    public OrderBook clone() {
        OrderBook newOrderBook = new OrderBook();
        for (Order buyOrder : this.buyOrders) {
            newOrderBook.getBuyOrders().add(buyOrder);
        }
        for (Order sellOrder : this.sellOrders) {
            newOrderBook.getSellOrders().add(sellOrder);
        }
        return newOrderBook;
    }
}
