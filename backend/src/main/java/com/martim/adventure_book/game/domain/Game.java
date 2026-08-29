package com.martim.adventure_book.game.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Game {

    private UUID id;
    private String bookId;
    private Integer currentSectionId;
    private int health;
    private GameStatus status;
}
