import { Component, OnInit, signal } from '@angular/core';
import { GamesService } from '../../core/services/games.service';
import { Game as GameModel } from '../../core/models/game';
import { MatCardModule } from '@angular/material/card';

@Component({
  selector: 'app-game',
  imports: [MatCardModule],
  templateUrl: './game.html',
  styleUrl: './game.scss',
})
export class Game implements OnInit {
  game = signal<GameModel | undefined>(undefined);

  constructor(private gamesService: GamesService) {}

  ngOnInit(): void {
    this.game.set(this.gamesService.currentGame);
  }

  makeChoice(optionIndex: number): void {
    this.gamesService.makeChoice(optionIndex).subscribe((game) => {
      this.game.set(game);
      this.gamesService.currentGame = game;
    });
  }
}
