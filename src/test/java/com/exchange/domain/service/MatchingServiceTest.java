package com.exchange.domain.service;
import static org.mockito.Mockito.*;

import com.exchange.domain.entity.Order;
import com.exchange.domain.entity.Trade;
import com.exchange.domain.value.object.OrderBook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class MatchingServiceTest {


        @InjectMocks
        @Spy
        private MatchingService matchingService;

        @InjectMocks
        @Spy
        private TradeService tradeService;


        @Mock
        private OrderBook orderBook;

        @InjectMocks
        @Spy
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
        orderService = mock(OrderService.class);
        orderBook = mock(OrderBook.class);
        matchingService.setOrderService(orderService);

        // Arrange
        Order mockOrder = mock(Order.class);

        // prevent real method invocation for matchOrders
        doNothing().when(matchingService).matchOrders();
        doReturn(mockOrder).when(orderService).createOrder(mockOrder);

        // Act
        matchingService.addOrder(mockOrder);

        // Assert
        verify(orderService, times(1)).createOrder(mockOrder);
        verify(matchingService, times(1)).matchOrders(); //verify if matchOrders was called
    }

}
