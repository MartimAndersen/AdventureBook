package com.martim.adventure_book.game.mapper;

import com.martim.adventure_book.book.domain.Book;
import com.martim.adventure_book.book.domain.Section;
import com.martim.adventure_book.game.domain.Game;
import com.martim.adventure_book.game.dto.GameResponseDto;
import com.martim.adventure_book.game.dto.OptionDto;
import com.martim.adventure_book.game.dto.SectionDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Builds the {@link GameResponseDto} returned to the client from the current
 * game and book state. Shared by both {@code GameSetupService} and
 * {@code GamePlayService}.
 */
@Component
public class GameResponseMapper {

    public GameResponseDto toResponse(Game game, Book book) {
        Section section = book.getSection(game.getCurrentSectionId());
        List<OptionDto> options = new ArrayList<>();

        if (section.getOptions() != null) { // can be null if it's an ending section
            options = section.getOptions()
                    .stream()
                    .map(option -> new OptionDto(
                            section.getOptions().indexOf(option),
                            option.description()
                    ))
                    .toList();
        }
        return new GameResponseDto(
                game.getGameId(),
                game.getBookId(),
                game.getHealth(),
                game.getStatus(),
                new SectionDto(
                        section.getId(),
                        section.getText(),
                        options
                )
        );
    }
}

