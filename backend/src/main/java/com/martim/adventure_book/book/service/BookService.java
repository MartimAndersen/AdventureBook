package com.martim.adventure_book.book.service;

import com.martim.adventure_book.book.dto.BookSummaryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookCatalog bookCatalog;

    public List<BookSummaryDto> getAllBooks() {
        return bookCatalog.getAllBooks()
                .stream()
                .map(book -> new BookSummaryDto(
                        book.getId(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getDifficulty(),
                        book.getType()
                ))
                .toList();
    }
}