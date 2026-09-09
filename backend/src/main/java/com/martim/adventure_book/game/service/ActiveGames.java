package com.martim.adventure_book.game.service;

import com.martim.adventure_book.common.exception.GameNotFoundException;
import com.martim.adventure_book.game.domain.Game;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ActiveGames {

    private final ConcurrentHashMap<UUID, Game> activeGames = new ConcurrentHashMap<>();

    public void put(Game game) {
        activeGames.put(game.getGameId(), game);
    }

    public Game get(UUID gameId) {
        Game game = activeGames.get(gameId);
        if (game == null) {
            throw new GameNotFoundException(gameId);
        }
        return game;
    }

    public Optional<Game> find(UUID gameId) {
        return Optional.ofNullable(activeGames.get(gameId));
    }
}

