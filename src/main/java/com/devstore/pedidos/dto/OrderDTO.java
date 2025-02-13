package com.devstore.pedidos.dto;

import com.devstore.pedidos.enums.OrderStatus;

import java.util.List;

public record OrderDTO(
        Long id,
        String user,
        OrderStatus status,
        java.time.LocalDateTime createdAt,
        List<OrderItemDTO> items
) {}
