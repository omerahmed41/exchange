package com.exchange.domain.service;

import com.exchange.domain.value.object.OrderBook;
import com.exchange.domain.entity.Order;
import com.exchange.domain.entity.Trade;
import com.exchange.infrastructure.OrderRepository;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Data
public class MatchingService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderService orderService;

    @Autowired
    private TradeService tradeService;

    @Getter
    private List<Trade> trades;

    @Getter
    private final OrderBook orderBook;

    public MatchingService() {
        this.orderBook = new OrderBook();
        this.trades = new ArrayList<>();

    }


    public Map<String, Object> addMultipleOrdersReturnOrderBookWithTrades(List<Order> orders) {
        addMultipleOrders(orders);
        Map<String, Object> response = new HashMap<>();
        response.put("trades", this.trades);
        response.put("orderBook", this.orderBook);
        return response;
    }

    public void addOrder(Order order) {
        orderBook.addOrderToOrderBook(order);
        orderService.createOrder(order);
        matchOrders();
    }

    public List<Trade> addMultipleOrders(List<Order> orders) {
        for (Order order : orders) {
            addOrder(order);
        }
        return this.trades;
    }

    void matchOrders() {
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

}
