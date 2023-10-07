package com.exchange.application;


import com.exchange.domain.OrderBook;
import com.exchange.domain.entity.Order;
import com.exchange.domain.entity.Trade;
import com.exchange.domain.service.OrderService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;


@Configuration
public class CommandLineInterface {


    /**
     * This bean is intended to be a CommandLineRunner for initial application setup.
     * If extending this class, make sure to call this method at a suitable point in your subclass.
     */
    @Bean
    public CommandLineRunner runCommand(ApplicationContext ctx, OrderService orderService)  throws Exception {
        return args -> {

            if (args.length != 0) {


            if (args.length != 2) {
                System.out.println("Usage: ./exchange <filename>");
                return;
            }



            String filename = args[1];
            List<String> lines;

            try {
                lines = Files.readAllLines(Paths.get(filename));
            } catch (IOException e) {
                System.out.println("An error occurred while reading the file.");
                return;
            }
            String joinedLines = String.join(" \n ", lines);

            List<Order> orders = Parser.parseOrders(joinedLines);



            Map<String, Object> result = orderService.addMultipleOrdersReturnOutput(orders);

            String trades = Formatter.formatTradesString((List<Trade>) result.get("trades"));
            String orderBook = Formatter.formatOrderBook((OrderBook) result.get("orderBook"));

            System.out.println(trades + orderBook);
            try {
                FileWriter writer = new FileWriter("output.txt");
                writer.write(trades + orderBook);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(SpringApplication.exit(ctx));
        }
        };
    };

}
