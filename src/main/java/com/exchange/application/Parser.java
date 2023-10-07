package com.exchange.application;

import com.exchange.domain.entity.Order;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class Parser {
    private static final int EXPECTED_FIELDS_COUNT = 4;
    private static final int QUANTITY_FIELD_ORDER = 3;
    private static final int PRICE_FIELD_ORDER = 2;
    private static final int SIDE_FIELD_ORDER = 1;
    private static final int ID_FIELD_ORDER = 0;

    public static List<Order> getOrders(String rawOrders) {
        String[] lines = rawOrders.split("\n");
        List<Order> orders = new ArrayList<>();


        for (String line : lines) {
            String[] fields = line.split(",");
//                if (fields.length != EXPECTED_FIELDS_COUNT) {
//                    return new ResponseEntity<>("Invalid input format.", HttpStatus.BAD_REQUEST);
//                }

            Order order = new Order();
            order.setOrderId(Long.parseLong(fields[ID_FIELD_ORDER].trim()));
            order.setSide(fields[SIDE_FIELD_ORDER].trim().charAt(0));
            order.setPrice(Integer.parseInt(fields[PRICE_FIELD_ORDER].trim()));
            order.setQuantity(Integer.parseInt(fields[QUANTITY_FIELD_ORDER].trim()));
            order.setTimestamp(LocalDateTime.now());

            orders.add(order);
        }
        return orders;
    }
}
