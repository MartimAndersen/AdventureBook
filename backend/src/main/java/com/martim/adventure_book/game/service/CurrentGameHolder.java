package com.martim.adventure_book.game.service;

import com.martim.adventure_book.common.exception.GameNotFoundException;
import com.martim.adventure_book.game.domain.Game;
import org.springframework.stereotype.Component;

@Component
public class CurrentGameHolder {

    private Game currentGame;

    public void set(Game game) {
        this.currentGame = game;
    }

    public Game get() {
        if (currentGame == null) {
            throw new GameNotFoundException();
        }
        return currentGame;
    }
}

