import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { BookSummary } from '../models/book-summary';

@Injectable({
  providedIn: 'root',
})
export class BooksService {
  private readonly apiUrl = 'http://localhost:8080/api/books';

  constructor(private http: HttpClient) {}

  getBooks(): Observable<BookSummary[]> {
    return this.http.get<BookSummary[]>(this.apiUrl);
  }

  getDifficulties(): Observable<string[]> {
    return this.http.get<string[]>(`${this.apiUrl}/difficulties`);
  }

  getTypes(): Observable<string[]> {
    return this.http.get<string[]>(`${this.apiUrl}/types`);
  }
}
