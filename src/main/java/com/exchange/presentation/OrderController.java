package com.exchange.presentation;

import com.exchange.application.Formatter;
import com.exchange.application.MatchingServiceAdapter;
import com.exchange.domain.entity.Order;
import com.exchange.domain.entity.Trade;
import com.exchange.domain.service.MatchingService;
import com.exchange.domain.service.IOrderService;
import com.exchange.domain.value.object.IOrderBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public final class OrderController {



    private final IOrderService orderService;

    private final MatchingService matchingService;

    private final MatchingServiceAdapter matchingServiceAdapter;



    @Autowired
    public OrderController(IOrderService orderService,
                           MatchingService matchingService,
                           MatchingServiceAdapter matchingServiceAdapter) {
        this.orderService = orderService;
        this.matchingService = matchingService;
        this.matchingServiceAdapter = matchingServiceAdapter;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addOrder(@RequestBody Order order) {
        try {
            matchingService.addOrder(order);
            return new ResponseEntity<>("Order added and processed successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while processing the order.", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addMultiple")
    public ResponseEntity<?> addOrders(@RequestBody List<Order> orders) {
        try {
            List<Trade> trades =  matchingService.addMultipleOrders(orders);
            return new ResponseEntity<>(trades, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while processing the orders.", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/addBulk", consumes = "text/plain")
    public ResponseEntity<?> addOrders(@RequestBody String rawOrders) {
        try {
            return new ResponseEntity<>(matchingServiceAdapter.processOrdersFromString(rawOrders),
                    HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while processing the orders."
                    + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }



    @GetMapping("/orderBook")
    public ResponseEntity<IOrderBook> getOrderBook() {
        try {
            IOrderBook orderBook = matchingService.getOrderBook();
            return new ResponseEntity<>(orderBook, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/orderBookFormatted")
    public ResponseEntity<String> getOrderBookFormatted() {
        try {
            IOrderBook orderBook = matchingService.getOrderBook();
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
