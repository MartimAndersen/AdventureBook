package com.martim.adventure_book.book.controller;

import com.martim.adventure_book.book.dto.BookSummaryDto;
import com.martim.adventure_book.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public List<BookSummaryDto> getBooks() {
        return bookService.getAllBooks();
    }
}
