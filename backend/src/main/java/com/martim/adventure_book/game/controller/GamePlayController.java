package com.martim.adventure_book.game.controller;

import com.martim.adventure_book.game.dto.GameResponseDto;
import com.martim.adventure_book.game.dto.MakeChoiceRequestDto;
import com.martim.adventure_book.game.service.GamePlayService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
public class GamePlayController {

    private final GamePlayService gamePlayService;

    @PostMapping("/choices")
    public GameResponseDto makeChoice(@RequestBody MakeChoiceRequestDto request) {
        return gamePlayService.makeChoice(request.optionIndex());
    }

    @PostMapping("/save")
    public void saveGame() {
        gamePlayService.saveGame();
    }
}

