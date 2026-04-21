import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { BookService } from '../../services/book.service';
import { CartService } from '../../services/cart.service';
import { Book } from '../../models/book.model';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit {
  books: Book[] = [];
  featuredBooks: Book[] = [];
  loading = true;

  constructor(private bookService: BookService, private cartService: CartService) {}

  ngOnInit(): void {
    this.loadBooks();
    this.loadFeatured();
  }

  loadBooks(): void {
    this.bookService.getAllBooks(0, 8).subscribe({
      next: (data) => {
        this.books = data.content;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error loading books:', err);
        this.loading = false;
      }
    });
  }

  loadFeatured(): void {
    this.bookService.getFeaturedBooks().subscribe({
      next: (data) => this.featuredBooks = data,
      error: (err) => {
        console.error('Error loading featured books:', err);
      }
    });
  }

  quickAddToCart(bookId: number): void {
    this.cartService.addToCart(bookId, 1).subscribe({
      next: () => alert('Book added to cart!'),
      error: (err) => alert('Please login to add to cart.')
    });
  }
}
