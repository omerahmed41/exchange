package com.exchange.application;

import java.io.IOException;

public interface IMatchingServiceAdapter {
    String processOrdersFromString(String filename) throws IOException;
}
