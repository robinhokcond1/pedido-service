package com.devstore.pedidos.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record ProductDTO(
        Long id,
        @NotBlank String name,
        @Min(0) double price,
        @Min(0) int stockQuantity
) {}
