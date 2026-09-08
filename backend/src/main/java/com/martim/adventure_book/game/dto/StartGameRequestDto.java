package com.martim.adventure_book.game.dto;

import jakarta.validation.constraints.NotBlank;

public record StartGameRequestDto(
        @NotBlank
        String bookId
) {
}