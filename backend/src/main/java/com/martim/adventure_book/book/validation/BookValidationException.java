package com.martim.adventure_book.book.validation;

public class BookValidationException extends RuntimeException {

    public BookValidationException(String message) {
        super(message);
    }
}