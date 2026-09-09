package com.martim.adventure_book.game.service;

import com.martim.adventure_book.book.domain.Book;
import com.martim.adventure_book.book.service.BookCatalog;
import com.martim.adventure_book.common.exception.BookNotFoundException;
import com.martim.adventure_book.common.exception.GameNotFoundException;
import com.martim.adventure_book.game.domain.Game;
import com.martim.adventure_book.game.dto.GameResponseDto;
import com.martim.adventure_book.game.dto.SavedGameSummaryDto;
import com.martim.adventure_book.game.mapper.GameResponseMapper;
import com.martim.adventure_book.game.mapper.SavedGameMapper;
import com.martim.adventure_book.game.repository.SavedGameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameSetupService {

    private final BookCatalog bookCatalog;
    private final GameEngine gameEngine;
    private final SavedGameRepository savedGameRepository;
    private final SavedGameMapper savedGameMapper;
    private final GameResponseMapper gameResponseMapper;
    private final ActiveGames activeGames;

    public GameResponseDto startGame(String bookId) {
        Book book = bookCatalog.getBook(bookId);

        if (book == null) {
            throw new BookNotFoundException(bookId);
        }
        
        Game game = gameEngine.startGame(book);
        activeGames.put(game);

        return gameResponseMapper.toResponse(game, book);
    }

    public GameResponseDto resumeGame(UUID gameId) {
        Game game = activeGames.find(gameId)
                .orElseGet(() -> savedGameRepository.findById(gameId)
                        .map(savedGameMapper::toDomain)
                        .orElseThrow(() -> new GameNotFoundException(gameId)));

        Book book = bookCatalog.getBook(game.getBookId());

        if (book == null) {
            throw new BookNotFoundException(
                    game.getBookId()
            );
        }

        activeGames.put(game);

        return gameResponseMapper.toResponse(game, book);
    }

    public List<SavedGameSummaryDto> getSavedGames() {
        return savedGameRepository.findAll()
                .stream()
                .map(entity -> new SavedGameSummaryDto(
                            entity.getGameId(),
                            entity.getBookId(),
                            entity.getUpdatedAt()
                    ))
                .toList();
    }
}

