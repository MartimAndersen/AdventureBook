package com.martim.adventure_book.common.dto;

import java.time.LocalDateTime;

public record ApiErrorDto(
        LocalDateTime timestamp,
        int status,
        String message
) {
}