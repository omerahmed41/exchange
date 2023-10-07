package com.exchange.domain.value.objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceQuantityPair {
    private int price;
    private int quantity;
}
