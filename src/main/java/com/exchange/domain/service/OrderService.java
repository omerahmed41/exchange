package com.exchange.domain.service;

import com.exchange.domain.OrderBook;
import com.exchange.domain.entity.Order;
import com.exchange.domain.entity.Trade;
import com.exchange.infrastructure.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public final class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TradeService tradeService;

    private List<Trade> trades;
    private final OrderBook orderBook;

    public List<Trade> getTrades() {
        return trades;
    }
    public OrderBook getOrderBook() {
        return orderBook;
    }
    public OrderService() {
        this.orderBook = new OrderBook();
        this.trades = new ArrayList<>();
    }

    public void addOrder(Order order) {
        orderBook.addOrder(order);
        createOrder(order);
        matchOrders();
    }

    public List<Trade> addMultiple(List<Order> orders) {
        for (Order order : orders) {
            addOrder(order);
        }
        return this.trades;
    }

    public Map<String, Object> addMultipleOrdersReturnOutput(List<Order> orders) {
        for (Order order : orders) {
            addOrder(order);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("trades", this.trades);
        response.put("orderBook", this.orderBook);
        return response;
    }



    private void createOrder(Order order) {
        order.setRemainingQuantity(order.getQuantity());
        orderRepository.save(order);
    }

    private void matchOrders() {
        while (true) {
            Order buyOrder = orderBook.getBuyOrders().peek();
            Order sellOrder = orderBook.getSellOrders().peek();

            if (buyOrder == null || sellOrder == null || buyOrder.getPrice() < sellOrder.getPrice()) {
                break;
            }

            orderBook.getBuyOrders().poll();
            orderBook.getSellOrders().poll();

            int tradePrice = sellOrder.getPrice();
            int tradeQuantity = Math.min(buyOrder.getRemainingQuantity(), sellOrder.getRemainingQuantity());

            // Create and save a trade
            Trade trade = tradeService.createTrade(buyOrder, sellOrder, tradePrice, tradeQuantity);
            this.trades.add(trade);
            int remainingBuyQuantity = buyOrder.getRemainingQuantity() - tradeQuantity;
            int remainingSellQuantity = sellOrder.getRemainingQuantity() - tradeQuantity;

            buyOrder.setRemainingQuantity(remainingBuyQuantity);
            orderRepository.save(buyOrder);
            if (remainingBuyQuantity > 0) {
                orderBook.getBuyOrders().add(buyOrder);
            }

            sellOrder.setRemainingQuantity(remainingSellQuantity);
            orderRepository.save(sellOrder);
            if (remainingSellQuantity > 0) {
                orderBook.getSellOrders().add(sellOrder);

            }

        }
    }


    public List<Order> getOrdersHistory() {
        return orderRepository.findAll();
    }

}
