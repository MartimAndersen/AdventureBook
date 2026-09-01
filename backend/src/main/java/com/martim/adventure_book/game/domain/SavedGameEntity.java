package com.martim.adventure_book.game.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "saved_game")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SavedGameEntity {
    @Id
    @Column(name = "id")
    private UUID gameId;

    @Column(name = "book_id")
    private String bookId;

    @Column(name = "current_section_id")
    private Integer currentSectionId;

    private int health;

    @Enumerated(EnumType.STRING)
    private GameStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

