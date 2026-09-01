package com.martim.adventure_book.game.service;

import com.martim.adventure_book.book.domain.Book;
import com.martim.adventure_book.book.domain.Option;
import com.martim.adventure_book.book.domain.Section;
import com.martim.adventure_book.book.service.BookCatalog;
import com.martim.adventure_book.common.exception.GameAlreadyFinishedException;
import com.martim.adventure_book.common.exception.InvalidChoiceException;
import com.martim.adventure_book.game.domain.Game;
import com.martim.adventure_book.game.domain.GameStatus;
import com.martim.adventure_book.game.domain.SavedGameEntity;
import com.martim.adventure_book.game.dto.GameResponseDto;
import com.martim.adventure_book.game.mapper.GameResponseMapper;
import com.martim.adventure_book.game.mapper.SavedGameMapper;
import com.martim.adventure_book.game.repository.SavedGameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class GamePlayService {

    private final BookCatalog bookCatalog;
    private final GameEngine gameEngine;
    private final SavedGameRepository savedGameRepository;
    private final SavedGameMapper savedGameMapper;
    private final GameResponseMapper gameResponseMapper;
    private final CurrentGameHolder currentGameHolder;

    public GameResponseDto makeChoice(int optionIndex) {
        Game game = currentGameHolder.get();

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
        game.setUpdatedAt(LocalDateTime.now());

        return gameResponseMapper.toResponse(game, book);
    }

    public void saveGame() {
        Game game = currentGameHolder.get();
        game.setUpdatedAt(LocalDateTime.now());

        SavedGameEntity entity = savedGameRepository.findByBookId(game.getBookId())
                .map(existing -> savedGameMapper.updateEntity(existing, game))
                .orElseGet(() -> savedGameMapper.toEntity(game));
        savedGameRepository.save(entity);
    }
}

