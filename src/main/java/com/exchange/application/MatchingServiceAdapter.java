package com.exchange.application;
import com.exchange.domain.entity.Order;
import com.exchange.domain.entity.Trade;
import com.exchange.domain.service.MatchingService;
import com.exchange.domain.value.object.OrderBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class MatchingServiceAdapter implements IMatchingServiceAdapter {


    @Autowired
    private MatchingService matchingService;


    @Autowired
    private Parser parser;


    @Override
    public String processOrdersFromString(String inputString) throws IOException {
            List<Order> orders = Parser.parseOrders(inputString);

            Map<String, Object> result = matchingService.addMultipleOrdersReturnSummary(orders);

            String trades = Formatter.formatTradesString((List<Trade>) result.get("trades"));
            String orderBook = Formatter.formatOrderBook((OrderBook) result.get("orderBook"));


            return trades + orderBook;
    }
}
