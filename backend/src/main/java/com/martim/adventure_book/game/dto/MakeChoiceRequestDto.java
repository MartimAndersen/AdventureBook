package com.martim.adventure_book.game.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record MakeChoiceRequestDto(
        @NotNull
        @Min(0)
        Integer optionIndex
) {
}