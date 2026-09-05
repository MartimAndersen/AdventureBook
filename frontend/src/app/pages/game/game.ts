import { Component, OnInit, signal } from '@angular/core';
import { GamesService } from '../../core/services/games.service';
import { Game as GameModel } from '../../core/models/game';
import { GameStatus } from '../../core/models/game-status';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { SaveGameDialog, SaveGameDialogResult } from './save-game-dialog';

@Component({
  selector: 'app-game',
  imports: [MatCardModule, MatButtonModule, MatIconModule, MatDialogModule],
  templateUrl: './game.html',
  styleUrl: './game.scss',
})
export class Game implements OnInit {
  game = signal<GameModel | undefined>(undefined);
  readonly gameStatus = GameStatus;

  constructor(
    private gamesService: GamesService,
    private snackBar: MatSnackBar,
    private dialog: MatDialog,
    private router: Router,
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
      this.showSaveSuccess();
    });
  }

  backToLibrary(): void {
    this.dialog
      .open(SaveGameDialog, { autoFocus: 'dialog' })
      .afterClosed()
      .subscribe((result: SaveGameDialogResult | undefined) => {
        if (result === 'save') {
          this.gamesService.saveGame().subscribe(() => {
            this.showSaveSuccess();
            this.navigateToLibrary();
          });
        } else if (result === 'discard') {
          this.navigateToLibrary();
        }
      });
  }

  private showSaveSuccess(): void {
    this.snackBar.open('Game saved successfully', 'Close', { duration: 3000 });
  }

  private navigateToLibrary(): void {
    this.router.navigate(['/']);
  }
}
