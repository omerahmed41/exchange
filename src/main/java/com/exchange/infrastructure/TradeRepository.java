package com.exchange.infrastructure;

import com.exchange.domain.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeRepository extends JpaRepository<Trade, String> {

}
