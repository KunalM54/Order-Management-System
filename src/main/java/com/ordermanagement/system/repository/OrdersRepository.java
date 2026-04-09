package com.ordermanagement.system.repository;

import com.ordermanagement.system.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository
extends JpaRepository<Orders, Long> {
}
