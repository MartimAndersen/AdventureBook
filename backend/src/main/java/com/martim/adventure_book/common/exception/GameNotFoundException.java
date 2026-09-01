package com.martim.adventure_book.common.exception;

import java.util.UUID;

public class GameNotFoundException extends RuntimeException {

    public GameNotFoundException(UUID gameId) {
        super("Game not found: " + gameId);
    }

    public GameNotFoundException() {
        super("No active game in memory");
    }
}