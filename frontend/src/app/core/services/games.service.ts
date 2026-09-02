import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SavedGame } from '../models/saved-game';

@Injectable({
  providedIn: 'root',
})
export class GamesService {
  private readonly apiUrl = "http://localhost:8080/api";

  constructor(private http: HttpClient) {}

  getSavedGames() {
    return this.http.get<SavedGame[]>(`${this.apiUrl}/saved-games`);
  }
}
