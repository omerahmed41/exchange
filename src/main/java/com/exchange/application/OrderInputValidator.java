package com.exchange.application;

import com.exchange.domain.exceptions.InvalidOrderInputException;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class OrderInputValidator {

    private static final Pattern VALID_INPUT_PATTERN = Pattern.compile("^\\d+,(B|S),\\d+\\.?\\d*,\\d+$");

    public static void isValidLine(String inputLine) {
        Matcher matcher = VALID_INPUT_PATTERN.matcher(inputLine);
        boolean isValidInputLine = matcher.matches();
        if (!isValidInputLine) {
            throw new InvalidOrderInputException("The provided order input is invalid. Line: " + inputLine);
        }

    }

}
