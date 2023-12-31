package com.exchange.domain.value.object;

import com.exchange.domain.entity.Order;
import com.exchange.domain.enums.OrderSide;

import java.util.PriorityQueue;
import java.util.Comparator;


public final class OrderBook implements IOrderBook {

    private PriorityQueue<Order> buyOrders;
    private PriorityQueue<Order> sellOrders;

    public OrderBook() {
        buyOrders = new PriorityQueue<>(Comparator.comparing(Order::getPrice).reversed()
                .thenComparing(Order::getTimestamp));
        sellOrders = new PriorityQueue<>(Comparator.comparing(Order::getPrice)
                .thenComparing(Order::getTimestamp));
    }

    @Override
    public void addOrderToOrderBook(Order order) {
        if (order.getSide() == OrderSide.BUY.getCode()) {
            buyOrders.add(order);
        } else if (order.getSide() == OrderSide.SELL.getCode()) {
            sellOrders.add(order);
        }
    }



    @Override
    public PriorityQueue<Order> getBuyOrders() {
        return buyOrders;
    }

    @Override
    public PriorityQueue<Order> getSellOrders() {
        return sellOrders;
    }

    @Override
    public IOrderBook clone() {
        IOrderBook newIOrderBook = new OrderBook();
        for (Order buyOrder : this.buyOrders) {
            newIOrderBook.getBuyOrders().add(buyOrder);
        }
        for (Order sellOrder : this.sellOrders) {
            newIOrderBook.getSellOrders().add(sellOrder);
        }
        return newIOrderBook;
    }
}
