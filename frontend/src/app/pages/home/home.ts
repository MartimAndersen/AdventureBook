import { Component, OnInit } from '@angular/core';

import { BooksService } from '../../core/services/books.service';
import { BookSummary } from '../../core/models/book-summary';

@Component({
  selector: 'app-home',
  imports: [],
  templateUrl: './home.html',
  styleUrl: './home.scss',
})
export class Home implements OnInit {
  books: BookSummary[] = [];

  constructor(private booksService: BooksService) {}

  ngOnInit(): void {
    this.booksService.getBooks().subscribe((books) => {
      this.books = books;
      console.log(books);
    });
  }
}
