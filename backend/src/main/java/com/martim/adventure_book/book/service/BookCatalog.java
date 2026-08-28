package com.martim.adventure_book.book.service;

import com.martim.adventure_book.book.domain.Book;
import com.martim.adventure_book.book.repository.JsonBookRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BookCatalog {

    private final JsonBookRepository jsonBookRepository;

    private final Map<String, Book> booksById = new HashMap<>();

    @PostConstruct
    public void init() {
        for (Book book : jsonBookRepository.loadBooks()) {
            booksById.put(book.getId(), book);
        }
    }

    public Book getBook(String id) {
        return booksById.get(id);
    }

    public Collection<Book> getAllBooks() {
        return booksById.values();
    }
}