package com.martim.adventure_book.game.controller;

import com.martim.adventure_book.game.dto.GameResponseDto;
import com.martim.adventure_book.game.dto.MakeChoiceRequestDto;
import com.martim.adventure_book.game.service.GamePlayService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
public class GamePlayController {

    private final GamePlayService gamePlayService;

    @PostMapping("/{gameId}/choices")
    public GameResponseDto makeChoice(@PathVariable UUID gameId,
                                      @Valid @RequestBody MakeChoiceRequestDto request) {
        return gamePlayService.makeChoice(gameId, request.optionIndex());
    }

    @PostMapping("/{gameId}/save")
    public void saveGame(@PathVariable UUID gameId) {
        gamePlayService.saveGame(gameId);
    }
}

