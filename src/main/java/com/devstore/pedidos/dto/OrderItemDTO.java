package com.devstore.pedidos.dto;

public record OrderItemDTO(
        Long id,
        ProductDTO product,
        int quantity,
        double price
) {}
