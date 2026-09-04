package com.martim.adventure_book.game.dto;

import com.martim.adventure_book.game.domain.GameStatus;

import java.util.UUID;

public record GameResponseDto(
        UUID gameId,
        String bookTitle,
        int health,
        GameStatus status,
        SectionDto section
) {
}