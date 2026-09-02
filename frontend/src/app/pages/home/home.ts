import { Component, OnInit } from '@angular/core';
import { BooksService } from '../../core/services/books.service';
import { BookSummary } from '../../core/models/book-summary';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { GamesService } from '../../core/services/games.service';
import { SavedGame } from '../../core/models/saved-game';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  imports: [MatCardModule, MatButtonModule],
  templateUrl: './home.html',
  styleUrl: './home.scss',
})
export class Home implements OnInit {
  books: BookSummary[] = [];
  savedGames: SavedGame[] = [];

  constructor(
    private booksService: BooksService,
    private gamesService: GamesService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.booksService.getBooks().subscribe((books) => {
      this.books = books;
    });

    this.gamesService.getSavedGames().subscribe((savedGames) => {
      this.savedGames = savedGames;
    });
  }

  startGame(bookId: string): void {
    this.gamesService.startGame(bookId).subscribe((game) => {
      this.gamesService.currentGame = game;

      this.router.navigate(['/game', game.gameId]);
    });
  }

  getSavedGame(bookId: string): SavedGame | undefined {
    return this.savedGames.find((game) => game.bookId === bookId);
  }

  resumeGame(gameId: string): void {
    this.gamesService.getGame(gameId).subscribe((game) => {
      this.gamesService.currentGame = game;

      this.router.navigate(['/game', gameId]);
    });
  }
}
