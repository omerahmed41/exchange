package com.exchange.infrastructure;

import com.exchange.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {

}
