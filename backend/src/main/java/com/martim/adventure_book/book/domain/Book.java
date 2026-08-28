package com.martim.adventure_book.book.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class Book {

    private String id, title, author, type;
    private Difficulty difficulty;
    private List<Section> sections;
    @JsonIgnore
    private Map<Integer, Section> sectionsById;
    @JsonIgnore
    private Section beginning;

    public Section getSection(int id) {
        return sectionsById.get(id);
    }
}