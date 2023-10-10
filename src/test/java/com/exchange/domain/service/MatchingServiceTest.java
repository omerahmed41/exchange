package com.exchange.domain.service;

import com.exchange.domain.entity.Order;
import com.exchange.domain.entity.Trade;
import com.exchange.domain.value.object.OrderBook;
import com.exchange.infrastructure.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MatchingServiceTest {


        @InjectMocks
        @Spy
        private MatchingService matchingService;
        @Mock
        private TradeService tradeService;

        @Mock
        private OrderRepository orderRepository;


        @Mock
        private OrderBook orderBook;

        @Mock
        private OrderService orderService;

        @Mock
        private List<Trade> trades;

    @Test
    public void testAddMultipleOrders() {
            // Arrange
            Order mockOrder1 = mock(Order.class);
            Order mockOrder2 = mock(Order.class);
            List<Order> orders = Arrays.asList(mockOrder1, mockOrder2);

            // Mock addOrder to do nothing
            doNothing().when(matchingService).addOrder(any());

            // Act
            List<Trade> result = matchingService.addMultipleOrders(orders);

            // Assert
            verify(matchingService, times(1)).addOrder(mockOrder1);
            verify(matchingService, times(1)).addOrder(mockOrder2);

        }

    @Test
    public void testAddMultipleOrdersReturnOrderBookWithTrades() {
        // Arrange
        Order mockOrder1 = mock(Order.class);
        Order mockOrder2 = mock(Order.class);
        List<Trade> mockTrades = new ArrayList<>();

        List<Order> orders = Arrays.asList(mockOrder1, mockOrder2);

        // Mock addMultipleOrders to do nothing
        doReturn(mockTrades).when(matchingService).addMultipleOrders(anyList());
        // Act
        Map<String, Object> result = matchingService.addMultipleOrdersReturnOrderBookWithTrades(orders);

        // Assert
        verify(matchingService, times(1)).addMultipleOrders(orders);

        // Verify that the map contains the expected keys and values
        Assertions.assertSame(matchingService.getTrades(), result.get("trades"));
        Assertions.assertSame(matchingService.getOrderBook(), result.get("orderBook"));
    }


    @Test
    public void testAddOrder() {

        // Arrange
        Order mockOrder = mock(Order.class);

        // prevent real method invocation for matchOrders
        doNothing().when(matchingService).matchOrders();
        doReturn(mockOrder).when(orderService).createOrder(mockOrder);

        // Act
        matchingService.addOrder(mockOrder);

        // Assert
        verify(orderService, times(1)).createOrder(mockOrder);
        verify(matchingService, times(1)).matchOrders();
    }

    @Test
    public void testMatchOrders() {
        // Arrange
        Order buyOrder = new Order(1L,'B',23,23,50, LocalDateTime.now());
        Order sellOrder = new Order(1L,'S',23,23,50, LocalDateTime.now());
        orderBook = new OrderBook();
        orderBook.addOrderToOrderBook(buyOrder);
        orderBook.addOrderToOrderBook(sellOrder);
        trades = new ArrayList<>();
        matchingService = new MatchingService(orderRepository,
                orderService,
                tradeService,
                orderBook,
                trades);

        Trade mockTrade = mock(Trade.class);


        doReturn(mockTrade).when(tradeService).createTrade(any(), any(), anyInt(), anyInt());

        // Act
        matchingService.matchOrders();

        // Assert
        verify(tradeService, times(1)).createTrade(any(), any(), anyInt(), anyInt());
        verify(orderRepository, times(1)).save(buyOrder);
        verify(orderRepository, times(1)).save(sellOrder);
    }

}
