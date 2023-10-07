package com.exchange.application;

import com.exchange.domain.OrderBook;
import com.exchange.domain.entity.Order;
import com.exchange.domain.entity.Trade;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class Formatter {
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
