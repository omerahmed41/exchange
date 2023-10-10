package com.exchange.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Trade {
    @Id
    private String tradeId;
    private Long aggressorOrderId;
    private Long restingOrderId;
    private int tradePrice;
    private int tradeQuantity;
}
