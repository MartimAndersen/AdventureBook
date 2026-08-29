package com.martim.adventure_book.game.service;

import com.martim.adventure_book.book.domain.Book;
import com.martim.adventure_book.book.domain.Option;
import com.martim.adventure_book.book.domain.Section;
import com.martim.adventure_book.book.service.BookCatalog;
import com.martim.adventure_book.common.exception.BookNotFoundException;
import com.martim.adventure_book.common.exception.GameAlreadyFinishedException;
import com.martim.adventure_book.common.exception.GameNotFoundException;
import com.martim.adventure_book.common.exception.InvalidChoiceException;
import com.martim.adventure_book.game.domain.Game;
import com.martim.adventure_book.game.domain.GameStatus;
import com.martim.adventure_book.game.dto.GameResponseDto;
import com.martim.adventure_book.game.dto.OptionDto;
import com.martim.adventure_book.game.dto.SectionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GameService {

    private final BookCatalog bookCatalog;
    private final GameEngine gameEngine;
    private final Map<UUID, Game> games = new HashMap<>();

    public GameResponseDto startGame(String bookId) {
        Book book = bookCatalog.getBook(bookId);

        if (book == null) {
            throw new BookNotFoundException(bookId);
        }

        Game game = gameEngine.startGame(book);
        games.put(game.getId(), game);

        return toResponse(game, book);
    }

    private GameResponseDto toResponse(Game game, Book book) {
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
                game.getId(),
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

    public GameResponseDto makeChoice(UUID gameId,int optionIndex){

        Game game = games.get(gameId);

        if (game == null) {
            throw new GameNotFoundException(gameId);
        }

        if (game.getStatus() != GameStatus.IN_PROGRESS) {
            throw new GameAlreadyFinishedException();
        }

        Book book = bookCatalog.getBook(game.getBookId());
        Section currentSection = book.getSection(game.getCurrentSectionId());

        if (optionIndex < 0 || optionIndex >= currentSection.getOptions().size()) {
            throw new InvalidChoiceException(optionIndex);
        }

        Option chosenOption = currentSection.getOptions().get(optionIndex);
        gameEngine.applyConsequence(game, chosenOption.consequence());
        game.setCurrentSectionId(chosenOption.gotoId());
        Section nextSection = book.getSection(chosenOption.gotoId());
        gameEngine.updateStatus(game, nextSection);

        return toResponse(game, book);
    }
}