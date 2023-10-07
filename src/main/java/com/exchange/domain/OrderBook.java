package com.exchange.domain;

import com.exchange.domain.entity.Order;

import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Comparator;


public final class OrderBook {

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
    public static String formatOrderBook(OrderBook orderBook) {
        StringBuilder formattedOutput = new StringBuilder();

        // This will iterate over the buyOrders and sellOrders queues simultaneously
        Iterator<Order> buyIterator = orderBook.getBuyOrders().iterator();
        Iterator<Order> sellIterator = orderBook.getSellOrders().iterator();

        while (buyIterator.hasNext() || sellIterator.hasNext()) {
            Order buyOrder = buyIterator.hasNext() ? buyIterator.next() : null;
            Order sellOrder = sellIterator.hasNext() ? sellIterator.next() : null;

            String buyQuantity = buyOrder != null
                    ? String.format("%,9d", buyOrder.getRemainingQuantity()) : "         ";
            String buyPrice = buyOrder != null
                    ? String.format("%6d", buyOrder.getPrice()) : "      ";

            String sellQuantity = sellOrder != null
                    ? String.format("%,9d", sellOrder.getRemainingQuantity()) : "         ";
            String sellPrice = sellOrder != null
                    ? String.format("%6d", sellOrder.getPrice()) : "      ";

            formattedOutput.append(String.format("%s %s | %s %s\n", buyQuantity, buyPrice, sellPrice, sellQuantity));
        }

        return formattedOutput.toString();
    }

}
