package com.devstore.pedidos.repositories;

import com.devstore.pedidos.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
