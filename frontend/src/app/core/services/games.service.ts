import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SavedGame } from '../models/saved-game';
import { Game } from '../models/game';

@Injectable({
  providedIn: 'root',
})
export class GamesService {
  private readonly apiUrl = 'http://localhost:8080/api';
  currentGame?: Game;

  constructor(private http: HttpClient) {}

  getSavedGames() {
    return this.http.get<SavedGame[]>(`${this.apiUrl}/saved-games`);
  }

  startGame(bookId: string) {
    return this.http.post<Game>(`${this.apiUrl}/games`, { bookId });
  }

  getGame(gameId: string) {
    return this.http.get<Game>(`${this.apiUrl}/games/${gameId}`);
  }

  makeChoice(optionIndex: number) {
    return this.http.post<Game>(`${this.apiUrl}/games/choices`, { optionIndex });
  }

  saveGame() {
    return this.http.post(`${this.apiUrl}/games/save`, {});
  }
}
