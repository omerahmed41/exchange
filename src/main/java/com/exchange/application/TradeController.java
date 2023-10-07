package com.exchange.application;

import com.exchange.domain.entity.Trade;
import com.exchange.domain.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trades")
public final class TradeController {

    @Autowired
    private TradeService tradeService;

    @GetMapping("/all")
    public ResponseEntity<List<Trade>> getAllTrades() {
        try {
            List<Trade> trades = tradeService.getAllTrades();
            return new ResponseEntity<>(trades, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
