package com.martim.adventure_book.book.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Section {

    private Integer id; // JSON creates id=null if not present, so we use Integer instead of int
    private String text;
    private SectionType type;
    private List<Option> options;

    public boolean isBeginning() {
        return type == SectionType.BEGIN;
    }

    public boolean isEnding() {
        return type == SectionType.END;
    }

    public boolean hasOptions() {
        return options != null && !options.isEmpty();
    }
}