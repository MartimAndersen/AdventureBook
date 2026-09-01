package com.martim.adventure_book.game.service;

import com.martim.adventure_book.book.domain.Book;
import com.martim.adventure_book.book.domain.Consequence;
import com.martim.adventure_book.book.domain.ConsequenceType;
import com.martim.adventure_book.book.domain.Section;
import com.martim.adventure_book.game.domain.Game;
import com.martim.adventure_book.game.domain.GameStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class GameEngine {

    private static final int INITIAL_HEALTH = 10;

    public Game startGame(Book book) {
        LocalDateTime now = LocalDateTime.now();
        return Game.builder()
                .gameId(UUID.randomUUID())
                .bookId(book.getId())
                .currentSectionId(book.getBeginning().getId())
                .health(INITIAL_HEALTH)
                .status(GameStatus.IN_PROGRESS)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    public void applyConsequence(Game game, Consequence consequence) {
        if (consequence == null) {
            return;
        }
        if (consequence.type() == ConsequenceType.LOSE_HEALTH) {
            game.setHealth(game.getHealth() - consequence.value());
        }
        if (consequence.type() == ConsequenceType.GAIN_HEALTH) {
            game.setHealth(game.getHealth() + consequence.value());
        }
    }

    public boolean isGameLost(Game game) {
        return game.getHealth() <= 0;
    }

    public boolean isGameWon(Section section) {
        return section.isEnding();
    }

    public void updateStatus(Game game, Section currentSection) {
        if (isGameLost(game)) {
            game.setStatus(GameStatus.LOST);
            return;
        }
        if (isGameWon(currentSection)) {
            game.setStatus(GameStatus.WON);
            return;
        }
        game.setStatus(GameStatus.IN_PROGRESS);
    }
}