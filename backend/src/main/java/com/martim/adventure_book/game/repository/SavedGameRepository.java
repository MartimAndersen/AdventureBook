package com.martim.adventure_book.game.repository;

import com.martim.adventure_book.game.domain.SavedGameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface SavedGameRepository extends JpaRepository<SavedGameEntity, UUID> {
	Optional<SavedGameEntity> findByBookId(String bookId);
}

