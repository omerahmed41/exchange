package com.exchange.domain.service;

import com.exchange.domain.entity.Order;
import com.exchange.infrastructure.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class OrderService implements IOrderService {


    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(Order order) {
        order.setRemainingQuantity(order.getQuantity());
        return orderRepository.save(order);
    }


    @Override
    public List<Order> getOrdersHistory() {
        return orderRepository.findAll();
    }

}
