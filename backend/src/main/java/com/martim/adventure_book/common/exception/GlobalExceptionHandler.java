package com.martim.adventure_book.common.exception;

import com.martim.adventure_book.common.dto.ApiErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorDto handleBookNotFound(BookNotFoundException e) {
        return new ApiErrorDto(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler(GameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorDto handleGameNotFound(GameNotFoundException e) {
        return new ApiErrorDto(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler(InvalidChoiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorDto handleInvalidChoice(InvalidChoiceException e) {
        return new ApiErrorDto(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(GameAlreadyFinishedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorDto handleGameAlreadyFinished(GameAlreadyFinishedException e) {
        return new ApiErrorDto(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
}