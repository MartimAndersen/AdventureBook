package com.martim.adventure_book.game.dto;

import java.util.List;

public record SectionDto(
        Integer id,
        String text,
        List<OptionDto> options
) {
}