package com.martim.adventure_book.book.validation;

import com.martim.adventure_book.book.domain.Book;
import com.martim.adventure_book.book.domain.Option;
import com.martim.adventure_book.book.domain.Section;
import org.springframework.stereotype.Component;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Set;

@Component
public class BookValidator {

    /**
     * Validations that do not require sectionsById map.
     * Must be executed before building the map so duplicate section IDs are reported
     * by the validator instead of the map creation (JsonBookRepository.buildSectionIndex()).
     */
    public void validateBeforeIndex(Book book) {
        validateMetadata(book); // extra
        validateHasSections(book); // extra
        validateRequiredAndUniqueFields(book); // extra
    }

    /**
     * Validations that depend on the sectionsById map.
     * Must be executed after the map is built because it performs section lookups by ID.
     */
    public void validateAfterIndex(Book book) {
        validateSingleBeginning(book);
        validateEnding(book);
        validateNonEndingSections(book);
        validateNextSectionIds(book);
        validateEndReachability(book); // extra
    }

    private void validateMetadata(Book book) {

        if (book.getTitle() == null || book.getTitle().isBlank()) {
            throw new BookValidationException("Book title is required");
        }

        if (book.getAuthor() == null || book.getAuthor().isBlank()) {
            throw new BookValidationException("Book author is required");
        }

        if (book.getDifficulty() == null) {
            throw new BookValidationException("Book difficulty is required");
        }
    }

    private void validateHasSections(Book book) {
        if (book.getSections() == null || book.getSections().isEmpty()) {
            throw new BookValidationException(
                    "Book has no sections"
            );
        }
    }

    private void validateRequiredAndUniqueFields(Book book) {

        Set<Integer> ids = new HashSet<>();

        for (Section section : book.getSections()) {

            if (section.getId() == null) {
                throw new BookValidationException(
                        "Section id cannot be null"
                );
            }

            if (!ids.add(section.getId())) {
                throw new BookValidationException(
                        "Duplicate section id: " + section.getId()
                );
            }

            if (section.getType() == null) {
                throw new BookValidationException(
                        "Section type cannot be null"
                );
            }

            if (section.getOptions() == null) {
                continue;
            }

            for (Option option : section.getOptions()) {

                if (option.gotoId() == null) {
                    throw new BookValidationException(
                            "Option gotoId cannot be null"
                    );
                }
                if (option.description() == null ||
                        option.description().isBlank()) {

                    throw new BookValidationException(
                            "Option description cannot be null or blank"
                    );
                }
            }
        }
    }

    private void validateSingleBeginning(Book book) {
        long beginCount = book.getSections()
                .stream()
                .filter(Section::isBeginning)
                .count();

        if (beginCount != 1) {
            throw new BookValidationException(
                    "Book has none, or more than one beginning"
            );
        }
    }

    private void validateEnding(Book book) {
        long endCount = book.getSections()
                .stream()
                .filter(Section::isEnding)
                .count();

        if (endCount == 0) {
            throw new BookValidationException(
                    "Book has no ending"
            );
        }
    }

    private void validateNextSectionIds(Book book) {
        for (Section section : book.getSections()) {

            if (section.getOptions() == null) {
                continue;
            }

            for (Option option : section.getOptions()) {

                if (book.getSection(option.gotoId()) == null) { // build sectionsById map before validation
                    throw new BookValidationException(
                            "Book has invalid next section id: "
                                    + option.gotoId()
                    );
                }
            }
        }
    }

    private void validateNonEndingSections(Book book) {
        for (Section section : book.getSections()) {

            if (!section.isEnding() && !section.hasOptions()) {
                throw new BookValidationException(
                        "A non-ending section has no options. Section id: "
                                + section.getId()
                );
            }
        }
    }

    /**
     * Validates that at least one END section is reachable from the BEGIN section.
     * Uses depth-first search (DFS) to traverse the sections.
     */
    private void validateEndReachability(Book book) {
        Section beginning = book.getBeginning(); // never null because of validateSingleBeginning
        Set<Integer> visited = new HashSet<>();
        ArrayDeque<Section> stack = new ArrayDeque<>();

        stack.push(beginning);

        while (!stack.isEmpty()) {
            Section current = stack.pop();

            if (!visited.add(current.getId())) {
                continue;
            }

            if (current.isEnding()) {
                return;
            }

            if (current.getOptions() == null) {
                continue;
            }

            for (Option option : current.getOptions()) {
                Section nextSection = book.getSection(option.gotoId());

                if (nextSection != null) {
                    stack.push(nextSection);
                }
            }
        }

        throw new BookValidationException(
                "No END section is reachable from BEGIN"
        );
    }
}