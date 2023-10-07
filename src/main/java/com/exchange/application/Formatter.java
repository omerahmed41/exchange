package com.exchange.application;

import com.exchange.domain.OrderBook;
import com.exchange.domain.entity.Order;
import com.exchange.domain.entity.Trade;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Queue;

@Service
public class Formatter {
    public static String formatOrderBook(OrderBook orderBook) {
        OrderBook clonedOrderBook = orderBook.clone();
        Queue<Order> buyOrders = clonedOrderBook.getBuyOrders();
        Queue<Order> sellOrders = clonedOrderBook.getSellOrders();
        StringBuilder formattedOutput = new StringBuilder();

        while (!buyOrders.isEmpty() || !sellOrders.isEmpty()) {
            Order buyOrder = buyOrders.peek(); // Peek at the head of buyOrders
            Order sellOrder = sellOrders.peek(); // Peek at the head of sellOrders

            String buyQuantity = buyOrder != null
                    ? String.format("%,9d", buyOrder.getRemainingQuantity()) : "         ";
            String buyPrice = buyOrder != null
                    ? String.format("%6d", buyOrder.getPrice()) : "      ";

            String sellQuantity = sellOrder != null
                    ? String.format("%,9d", sellOrder.getRemainingQuantity()) : "         ";
            String sellPrice = sellOrder != null
                    ? String.format("%6d", sellOrder.getPrice()) : "      ";

            formattedOutput.append(String.format("%s %s | %s %s\n", buyQuantity, buyPrice, sellPrice, sellQuantity));

            if (buyOrder != null) {
                buyOrders.poll();
            }
            if (sellOrder != null) {
                sellOrders.poll();
            }
        }

        return formattedOutput.toString();

    }

    public static String formatTradesString(List<Trade> trades) {
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
