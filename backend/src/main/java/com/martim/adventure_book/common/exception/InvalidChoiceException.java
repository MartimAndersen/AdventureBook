package com.martim.adventure_book.common.exception;

public class InvalidChoiceException extends RuntimeException {

    public InvalidChoiceException(int optionIndex) {
        super("Invalid option index: " + optionIndex);
    }
}