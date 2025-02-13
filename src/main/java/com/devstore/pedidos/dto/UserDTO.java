package com.devstore.pedidos.dto;

import com.devstore.pedidos.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UserDTO(
        Long id,
        @NotBlank String username,
        @NotBlank String password,
        @Email String email,
        UserRole role,
        List<OrderDTO> orders
) {}
