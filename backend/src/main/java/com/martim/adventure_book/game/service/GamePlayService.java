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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GamePlayService {

    private final BookCatalog bookCatalog;
    private final GameEngine gameEngine;
    private final SavedGameRepository savedGameRepository;
    private final SavedGameMapper savedGameMapper;
    private final GameResponseMapper gameResponseMapper;
    private final ActiveGames activeGames;

    public GameResponseDto makeChoice(UUID gameId, int optionIndex) {
        Game game = activeGames.get(gameId);

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

        return gameResponseMapper.toResponse(game, book);
    }

    @Transactional
    public void saveGame(UUID gameId) {
        Game game = activeGames.get(gameId);
        game.setUpdatedAt(LocalDateTime.now());

        SavedGameEntity entity = savedGameRepository.findByBookId(game.getBookId())
                .map(existing -> savedGameMapper.updateEntity(existing, game))
                .orElseGet(() -> savedGameMapper.toEntity(game));
        savedGameRepository.save(entity);
    }
}

