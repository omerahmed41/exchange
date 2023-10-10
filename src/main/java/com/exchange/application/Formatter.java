package com.exchange.application;

import com.exchange.domain.value.object.IOrderBook;
import com.exchange.domain.entity.Order;
import com.exchange.domain.entity.Trade;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Queue;

@Service
public class Formatter {
    public static String formatOrderBook(IOrderBook orderBook) {
        IOrderBook clonedIOrderBook = orderBook.clone();
        Queue<Order> buyOrders = clonedIOrderBook.getBuyOrders();
        Queue<Order> sellOrders = clonedIOrderBook.getSellOrders();
        StringBuilder formattedOutput = new StringBuilder();

        while (!buyOrders.isEmpty() || !sellOrders.isEmpty()) {
            Order buyOrder = buyOrders.peek();
            Order sellOrder = sellOrders.peek();

            String buyQuantity = buyOrder != null
                    ? String.format("%,11d", buyOrder.getRemainingQuantity()) : "           ";
            String buyPrice = buyOrder != null
                    ? String.format("%6d", buyOrder.getPrice()) : "      ";

            String sellQuantity = sellOrder != null
                    ? String.format("%,11d", sellOrder.getRemainingQuantity()) : "           ";
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
            sb.append("\n");
        }
        return sb.toString();
    }
}
