package com.devstore.pedidos.exceptions;

import java.time.Instant;

public record ErrorResponseExceptions(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path
) {}
