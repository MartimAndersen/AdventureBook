package com.martim.adventure_book.game.dto;

import com.martim.adventure_book.book.domain.ConsequenceType;

public record ConsequenceDto(
        ConsequenceType type,
        int value,
        String text
) {
}
