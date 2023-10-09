package com.exchange.domain.enums;

import lombok.Getter;

public enum OrderSide {
    BUY('B'),
    SELL('S');

    @Getter
    private final char code;

    OrderSide(char code) {
        this.code = code;
    }


}
