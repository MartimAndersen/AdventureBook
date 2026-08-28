package com.martim.adventure_book.book.repository;

import com.martim.adventure_book.book.domain.Book;
import com.martim.adventure_book.book.domain.Section;
import com.martim.adventure_book.book.validation.BookValidationException;
import com.martim.adventure_book.book.validation.BookValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Repository;
import tools.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class JsonBookRepository {

    private final ObjectMapper objectMapper;
    private final BookValidator validator;

    public List<Book> loadBooks() {
        List<Book> books = new ArrayList<>();
        try {
            Resource[] resources = new PathMatchingResourcePatternResolver()
                    .getResources("classpath:books/*.json");
            for (Resource resource : resources) {
                try {
                    books.add(loadBook(resource));
                } catch (BookValidationException e) {
                    log.warn("Skipping invalid book '{}': {}", resource.getFilename(), e.getMessage());
                }  catch (RuntimeException e) {
                    log.warn("Skipping unreadable book '{}': {}", resource.getFilename(), e.getMessage());
                }
            }

            if (books.isEmpty()) {
                log.warn("No valid books were loaded");
            }

            return books;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load books", e);
        }
    }

    private Book loadBook(Resource resource) {
        try {
            Book book = objectMapper.readValue(resource.getInputStream(), Book.class);
            String fileName = resource.getFilename();

            if (fileName != null) {
                book.setId(fileName.replace(".json", ""));
            }

            validator.validateBeforeSectionMap(book);
            buildSectionMap(book);
            validator.validateAfterSectionMap(book);
            log.info("Loaded book '{}'", book.getId());

            return book;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load book: " + resource.getFilename(), e);
        }
    }

    /**
     * Builds a map of sections by their ID for O(1) lookup used in validation and navigation.
     */
    private void buildSectionMap(Book book) {
        book.setSectionsById(
                book.getSections()
                        .stream()
                        .collect(Collectors.toMap(
                                Section::getId,
                                Function.identity()
                        ))
        );
    }
}