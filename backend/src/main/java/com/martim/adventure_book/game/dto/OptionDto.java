package com.martim.adventure_book.game.dto;

public record OptionDto(
        int index,
        String description,
        ConsequenceDto consequence
) {
}
