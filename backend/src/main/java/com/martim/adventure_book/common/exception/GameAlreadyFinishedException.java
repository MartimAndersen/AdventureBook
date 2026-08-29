package com.martim.adventure_book.common.exception;

public class GameAlreadyFinishedException extends RuntimeException {

    public GameAlreadyFinishedException() {
        super("Game is already finished");
    }
}