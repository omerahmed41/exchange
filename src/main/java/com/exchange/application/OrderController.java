package com.exchange.application;

import com.exchange.domain.entity.Order;
import com.exchange.domain.entity.Trade;
import com.exchange.domain.service.OrderService;
import com.exchange.domain.OrderBook;
import com.exchange.domain.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public final class OrderController {



    @Autowired
    private OrderService orderService;
    @Autowired
    private TradeService tradeService;
    @Autowired
    private Parser parser;

    @PostMapping("/add")
    public ResponseEntity<String> addOrder(@RequestBody Order order) {
        try {
            orderService.addOrder(order);
            return new ResponseEntity<>("Order added and processed successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while processing the order.", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addMultiple")
    public ResponseEntity<?> addOrders(@RequestBody List<Order> orders) {
        try {
            List<Trade> trades =  orderService.addMultiple(orders);
            return new ResponseEntity<>(trades, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while processing the orders.", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/addBulk", consumes = "text/plain")
    public ResponseEntity<?> addOrders(@RequestBody String rawOrders) {
        try {
            List<Order> orders = Parser.parseOrders(rawOrders);

            Map<String, Object> result = orderService.addMultipleOrdersReturnOutput(orders);

            String trades = Formatter.formatTradesString((List<Trade>) result.get("trades"));
            String orderBook = Formatter.formatOrderBook((OrderBook) result.get("orderBook"));

            return new ResponseEntity<>(trades + orderBook, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while processing the orders.", HttpStatus.BAD_REQUEST);
        }
    }



    @GetMapping("/orderBook")
    public ResponseEntity<OrderBook> getOrderBook() {
        try {
            OrderBook orderBook = orderService.getOrderBook();
            return new ResponseEntity<>(orderBook, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/orderBookFormatted")
    public ResponseEntity<String> getOrderBookFormatted() {
        try {
            OrderBook orderBook = orderService.getOrderBook();
            String formattedOrderBook = Formatter.formatOrderBook(orderBook);
            return new ResponseEntity<>(formattedOrderBook, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while fetching the order book.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/ordersHistory")
    public List<Order> getOrderHistory() {

            return orderService.getOrdersHistory();


    }

}
