package com.exchange.domain.entity;

import io.micrometer.common.lang.Nullable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders_table")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;
    private char side; // 'B' for Buy, 'S' for Sell
    private int price;
    private int quantity;

    @Schema(hidden = true, nullable = true)
    @Nullable
    private int remainingQuantity = 0;
    private LocalDateTime timestamp;
}
