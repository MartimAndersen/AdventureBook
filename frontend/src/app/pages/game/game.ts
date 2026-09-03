import { Component, OnInit, signal } from '@angular/core';
import { GamesService } from '../../core/services/games.service';
import { Game as GameModel } from '../../core/models/game';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-game',
  imports: [MatCardModule, MatButtonModule],
  templateUrl: './game.html',
  styleUrl: './game.scss',
})
export class Game implements OnInit {
  game = signal<GameModel | undefined>(undefined);

  constructor(
    private gamesService: GamesService,
    private snackBar: MatSnackBar,
  ) {}

  ngOnInit(): void {
    this.game.set(this.gamesService.currentGame);
  }

  makeChoice(optionIndex: number): void {
    this.gamesService.makeChoice(optionIndex).subscribe((game) => {
      this.game.set(game);
      this.gamesService.currentGame = game;
    });
  }

  saveGame(): void {
    this.gamesService.saveGame().subscribe(() => {
      this.snackBar.open(
        'Game saved successfully',
        'Close',
        {
          duration: 3000
        }
      );
    });
  }
}
