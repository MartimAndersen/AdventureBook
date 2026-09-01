package com.martim.adventure_book.game.mapper;

import com.martim.adventure_book.game.domain.Game;
import com.martim.adventure_book.game.domain.SavedGameEntity;
import org.springframework.stereotype.Component;

@Component
public class SavedGameMapper {

    public SavedGameEntity toEntity(Game game) {
        if (game == null) {
            return null;
        }
        return SavedGameEntity.builder()
                .gameId(game.getGameId())
                .bookId(game.getBookId())
                .currentSectionId(game.getCurrentSectionId())
                .health(game.getHealth())
                .status(game.getStatus())
                .createdAt(game.getCreatedAt())
                .updatedAt(game.getUpdatedAt())
                .build();
    }

    public SavedGameEntity updateEntity(SavedGameEntity entity, Game game) {
        entity.setCurrentSectionId(game.getCurrentSectionId());
        entity.setHealth(game.getHealth());
        entity.setStatus(game.getStatus());
        entity.setUpdatedAt(game.getUpdatedAt());
        return entity;
    }

    public Game toDomain(SavedGameEntity entity) {
        if (entity == null) {
            return null;
        }
        return Game.builder()
                .gameId(entity.getGameId())
                .bookId(entity.getBookId())
                .currentSectionId(entity.getCurrentSectionId())
                .health(entity.getHealth())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}

