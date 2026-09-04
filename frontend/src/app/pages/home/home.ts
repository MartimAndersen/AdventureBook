import { Component, OnInit, signal, computed } from '@angular/core';
import { BooksService } from '../../core/services/books.service';
import { BookSummary } from '../../core/models/book-summary';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatMenuModule } from '@angular/material/menu';
import { GamesService } from '../../core/services/games.service';
import { SavedGame } from '../../core/models/saved-game';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  imports: [MatCardModule, MatButtonModule, MatCheckboxModule, MatMenuModule],
  templateUrl: './home.html',
  styleUrl: './home.scss',
})
export class Home implements OnInit {
  books = signal<BookSummary[]>([]);
  difficulties = signal<string[]>([]);
  types = signal<string[]>([]);
  selectedDifficulties = signal<string[]>([]);
  selectedTypes = signal<string[]>([]);
  searchQuery = signal('');
  filteredBooks = computed(() => {
    const selectedDifficulties = this.selectedDifficulties().map((value) => value.toLowerCase());
    const selectedTypes = this.selectedTypes().map((value) => value.toLowerCase());

    return this.books().filter((book) => {
      const matchesDifficulty =
        selectedDifficulties.length === 0 ||
        selectedDifficulties.includes(String(book.difficulty).toLowerCase());
      const matchesType =
        selectedTypes.length === 0 || selectedTypes.includes(book.type.toLowerCase());

      const normalizedTitle = book.title.toLowerCase();
      const normalizedSearch = this.searchQuery().toLowerCase().trim();
      const matchesTitle = normalizedTitle.includes(normalizedSearch);

      return matchesDifficulty && matchesType && matchesTitle;
    });
  });
  savedGames = signal<SavedGame[]>([]);

  constructor(
    private booksService: BooksService,
    private gamesService: GamesService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.booksService.getBooks().subscribe((books) => {
      this.books.set(books);
    });

    this.booksService.getDifficulties().subscribe((difficulties) => {
      this.difficulties.set(difficulties);
    });

    this.booksService.getTypes().subscribe((types) => {
      this.types.set(types);
    });

    this.gamesService.getSavedGames().subscribe((savedGames) => {
      this.savedGames.set(savedGames);
    });
  }

  toggleDifficulty(difficulty: string): void {
    this.selectedDifficulties.update((selected) => this.toggleValue(selected, difficulty));
  }

  toggleType(type: string): void {
    this.selectedTypes.update((selected) => this.toggleValue(selected, type));
  }

  onSearchChange(event: Event): void {
    this.searchQuery.set((event.target as HTMLInputElement).value);
  }

  isDifficultySelected(difficulty: string): boolean {
    return this.selectedDifficulties().some(
      (selected) => selected.toLowerCase() === difficulty.toLowerCase(),
    );
  }

  isTypeSelected(type: string): boolean {
    return this.selectedTypes().some((selected) => selected.toLowerCase() === type.toLowerCase());
  }

  private toggleValue(values: string[], value: string): string[] {
    const index = values.findIndex((selected) => selected.toLowerCase() === value.toLowerCase());

    return index === -1
      ? [...values, value]
      : values.filter((_, valueIndex) => valueIndex !== index);
  }

  startGame(bookId: string): void {
    this.gamesService.startGame(bookId).subscribe((game) => {
      this.gamesService.currentGame = game;

      this.router.navigate(['/game', game.gameId]);
    });
  }

  getSavedGame(bookId: string): SavedGame | undefined {
    return this.savedGames().find((game) => game.bookId === bookId);
  }

  resumeGame(gameId: string): void {
    this.gamesService.getGame(gameId).subscribe((game) => {
      this.gamesService.currentGame = game;

      this.router.navigate(['/game', gameId]);
    });
  }
}
