package com.martim.adventure_book.game.controller;

import com.martim.adventure_book.game.dto.GameResponseDto;
import com.martim.adventure_book.game.dto.MakeChoiceRequestDto;
import com.martim.adventure_book.game.dto.StartGameRequestDto;
import com.martim.adventure_book.game.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping
    public GameResponseDto startGame(@RequestBody StartGameRequestDto request) {
        return gameService.startGame(request.bookId());
    }

    @PostMapping("/{gameId}/choices")
    public GameResponseDto makeChoice(@PathVariable UUID gameId, @RequestBody MakeChoiceRequestDto request) {
        return gameService.makeChoice(gameId, request.optionIndex());
    }
}