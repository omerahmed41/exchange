package com.exchange;


import com.exchange.application.Formatter;
import com.exchange.application.Parser;
import com.exchange.domain.value.object.OrderBook;
import com.exchange.domain.entity.Order;
import com.exchange.domain.entity.Trade;
import com.exchange.domain.service.MatchingService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Configuration
public class CommandLineInterface {

    /**
     * This bean is intended to be a CommandLineRunner for initial application setup.
     * If extending this class, make sure to call this method at a suitable point in your subclass.
     */
    @Bean
    public CommandLineRunner runCommand(ApplicationContext ctx, OrderService orderService,
                                        MatchingService matchingService)  throws Exception {
        return args -> {
            writeOutput("", "output.txt");
            writeOutput("", "error.txt");
            if (args.length != 0) {


                if (args.length != 2) {
                    writeOutput("Usage: ./exchange <filename>", "error.txt");
                    System.exit(SpringApplication.exit(ctx));
                }



                String filename = args[1];
                List<String> lines = new ArrayList<>();

                try {
                    lines = Files.readAllLines(Paths.get(filename));

                } catch (IOException e) {
                    writeOutput("An error occurred while reading the file.", "error.txt");
                    System.exit(SpringApplication.exit(ctx));
                }
                String joinedLines = String.join(" \n ", lines);

                List<Order> orders = Parser.parseOrders(joinedLines);

                Map<String, Object> result = matchingService.addMultipleOrdersReturnSummary(orders);

                String trades = Formatter.formatTradesString((List<Trade>) result.get("trades"));
                String orderBook = Formatter.formatOrderBook((OrderBook) result.get("orderBook"));

                writeOutput(trades + orderBook, "output.txt");

                System.exit(SpringApplication.exit(ctx));
            }
        };
    }

    private static void writeOutput(String response, String filename) {
        try {
            FileWriter writer = new FileWriter(filename);
            writer.write(response);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
