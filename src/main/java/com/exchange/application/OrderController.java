package com.exchange.application;

import com.exchange.domain.entity.Order;
import com.exchange.domain.service.OrderService;
import com.exchange.domain.OrderBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public final class OrderController {

    private static final int EXPECTED_FIELDS_COUNT = 4;
    private static final int QUANTITY_FIELD_ORDER = 3;
    private static final int PRICE_FIELD_ORDER = 2;
    private static final int SIDE_FIELD_ORDER = 1;
    private static final int ID_FIELD_ORDER = 0;

    @Autowired
    private OrderService orderService;

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
    public ResponseEntity<String> addOrders(@RequestBody List<Order> orders) {
        try {
            for (Order order : orders) {
                orderService.addOrder(order);
            }
            return new ResponseEntity<>("Orders added and processed successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while processing the orders.", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/addBulk", consumes = "text/plain")
    public ResponseEntity<String> addOrders(@RequestBody String rawOrders) {
        try {
            String[] lines = rawOrders.split("\n");
            List<Order> orders = new ArrayList<>();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

            for (String line : lines) {
                String[] fields = line.split(",");
                if (fields.length != EXPECTED_FIELDS_COUNT) {
                    return new ResponseEntity<>("Invalid input format.", HttpStatus.BAD_REQUEST);
                }

                Order order = new Order();
                order.setOrderId(Long.parseLong(fields[ID_FIELD_ORDER].trim()));
                order.setSide(fields[SIDE_FIELD_ORDER].trim().charAt(0));
                order.setPrice(Integer.parseInt(fields[PRICE_FIELD_ORDER].trim()));
                order.setQuantity(Integer.parseInt(fields[QUANTITY_FIELD_ORDER].trim()));
                order.setTimestamp(LocalDateTime.now());

                orders.add(order);
            }

            for (Order order : orders) {
                orderService.addOrder(order);
            }

            return new ResponseEntity<>("Orders added and processed successfully.", HttpStatus.CREATED);
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

    @GetMapping("/ordersHistory")
    public List<Order> getOrderHistory() {

            return orderService.getOrdersHistory();


    }

}
