package com.martim.adventure_book.book.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.martim.adventure_book.book.domain.Book;
import com.martim.adventure_book.book.domain.Section;
import com.martim.adventure_book.book.validation.BookValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Repository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class JsonBookRepository {

    private final ObjectMapper objectMapper;
    private final BookValidator validator;

    public List<Book> loadBooks() {
        List<Book> books = new ArrayList<>();

        try {
            Resource[] resources =
                    new PathMatchingResourcePatternResolver()
                            .getResources("classpath:books/*.json");

            for (Resource resource : resources) {
                books.add(loadBook(resource));
            }

            return books;

        } catch (IOException e) {
            throw new RuntimeException("Failed to load books", e);
        }
    }

    private Book loadBook(Resource resource) {
        try {
            Book book = objectMapper.readValue(
                    resource.getInputStream(),
                    Book.class
            );

            String fileName = resource.getFilename();

            if (fileName != null) {
                book.setId(fileName.replace(".json", ""));
            }

            validator.validateBeforeIndex(book);

            buildSectionIndex(book);

            validator.validateAfterIndex(book);

            return book;

        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to load book: " + resource.getFilename(),
                    e
            );
        }
    }

    private void buildSectionIndex(Book book) {
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