package com.martim.adventure_book.game.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record SavedGameSummaryDto(
        UUID gameId,
        String bookId,
        LocalDateTime updatedAt
) {
}

