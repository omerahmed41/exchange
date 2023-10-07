package com.exchange.domain.service;

import com.exchange.domain.OrderBook;
import com.exchange.domain.entity.Order;
import com.exchange.infrastructure.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TradeService tradeService;

    private final OrderBook orderBook;

    public OrderBook getOrderBook() {
        return orderBook;
    }
    public OrderService() {
        this.orderBook = new OrderBook();
    }

    public void addOrder(Order order) {
        orderBook.addOrder(order);
        createOrder(order);
        matchOrders();
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
            tradeService.createTrade(buyOrder, sellOrder, tradePrice, tradeQuantity);

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
