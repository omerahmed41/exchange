package com.exchange.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Trade {
    @Id
    private String tradeId; // A unique identifier for each trade
    private Long aggressorOrderId;
    private Long restingOrderId;
    private int tradePrice;
    private int tradeQuantity;
}
