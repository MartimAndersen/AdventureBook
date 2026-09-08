package com.martim.adventure_book.game.controller;

import com.martim.adventure_book.game.dto.GameResponseDto;
import com.martim.adventure_book.game.dto.SavedGameSummaryDto;
import com.martim.adventure_book.game.dto.StartGameRequestDto;
import com.martim.adventure_book.game.service.GameSetupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class GameSetupController {

    private final GameSetupService gameSetupService;

    @PostMapping("/api/games")
    public GameResponseDto startGame(@Valid @RequestBody StartGameRequestDto request) {
        return gameSetupService.startGame(request.bookId());
    }

    @GetMapping("/api/games/{gameId}")
    public GameResponseDto resumeGame(@PathVariable UUID gameId) {
        return gameSetupService.resumeGame(gameId);
    }

    @GetMapping("/api/saved-games")
    public List<SavedGameSummaryDto> getSavedGames() {
        return gameSetupService.getSavedGames();
    }
}
