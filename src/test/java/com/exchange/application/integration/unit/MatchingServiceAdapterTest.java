package com.exchange.application.integration.unit;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.exchange.application.MatchingServiceAdapter;
import com.exchange.domain.exceptions.InvalidOrderInputException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.io.IOException;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Tag("IntegrationTest")
public class MatchingServiceAdapterTest {
    @Autowired
    MatchingServiceAdapter matchingServiceAdapter;



    @Test
    public void testProcessOrdersFromStringNoTrades() throws IOException {
        String input = "10000,B,98,25500\n10005,S,105,20000\n10001,S,100,500\n10002,S,100,10000\n10003,B,99,50000\n10004,S,103,100";
        String expectedOutput = "     50,000     99 |    100         500\n     25,500     98 |    100      10,000\n                   |    103         100\n                   |    105      20,000\n";

        String actualOutput = matchingServiceAdapter.processOrdersFromString(input);

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testProcessOrdersFromStringThrowsException() throws IOException {
        String input = "10000,wrongChar,98,25500\n10005,S,105,20000\n10001,S,100,500\n10002,S,100,10000\n10003,B,99,50000\n10004,S,103,100";
        assertThrows(InvalidOrderInputException.class, () -> {
            matchingServiceAdapter.processOrdersFromString(input);
        });

    }

    @Test
    public void testProcessOrdersFromString() throws IOException {
        String input = "10000,B,98,25500\n" +
                "10005,S,105,20000\n" +
                "10001,S,100,500\n" +
                "10002,S,100,10000\n" +
                "10003,B,99,50000\n" +
                "10004,S,103,100\n" +
                "10006,B,105,16000";
        String expectedOutput = "trade 10006,10001,100,500\n" +
                "trade 10006,10002,100,10000\n" +
                "trade 10006,10004,103,100\n" +
                "trade 10006,10005,105,5400\n" +
                "     50,000     99 |    105      14,600\n" +
                "     25,500     98 |                   \n";

        String actualOutput = matchingServiceAdapter.processOrdersFromString(input);

        assertEquals(expectedOutput, actualOutput);
    }

}
