package com.exchange.presentation;

import com.exchange.application.MatchingServiceAdapter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


@Configuration
public class CommandLineInterface {

    /**
     * This bean is intended to be a CommandLineRunner for initial application setup.
     * If extending this class, make sure to call this method at a suitable point in your subclass.
     */
    @Bean
    public CommandLineRunner runCommand(ApplicationContext ctx, MatchingServiceAdapter matchingServiceAdapter)
            throws Exception {
        return args -> {
            writeOutput("", "output.txt");
            writeOutput("", "error.txt");
            try {

            if (args.length != 0) {

                if (args.length != 2) {
                    writeOutput("Usage: ./exchange <filename>", "error.txt");
                    System.exit(SpringApplication.exit(ctx));
                }

                String filename = args[1];
                String inputString = Files.readString(Path.of(filename));

                writeOutput(matchingServiceAdapter.processOrdersFromString(inputString), "output.txt");

                System.exit(SpringApplication.exit(ctx));
            }
            } catch (Exception e) {
                writeOutput("An error occurred while processing the orders." + e.getMessage(), "error.txt");
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
