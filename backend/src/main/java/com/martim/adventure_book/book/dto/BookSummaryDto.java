package com.martim.adventure_book.book.dto;

import com.martim.adventure_book.book.domain.Difficulty;

/**
 * Home page only lists books' metadata, not their sections
 */
public record BookSummaryDto(
        String id,
        String title,
        String author,
        Difficulty difficulty,
        String type
) {
}
