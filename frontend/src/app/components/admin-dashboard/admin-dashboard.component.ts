import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BookService } from '../../services/book.service';
import { OrderService } from '../../services/order.service';
import { Book } from '../../models/book.model';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.css'
})
export class AdminDashboardComponent implements OnInit {
  books: Book[] = [];
  orders: any[] = [];
  activeTab = 'books';
  
  newBook: any = {
    title: '',
    author: '',
    isbn: '',
    description: '',
    genre: '',
    price: 0,
    stockQuantity: 0,
    imageUrl: '',
    featured: false
  };

  constructor(private bookService: BookService, private orderService: OrderService) {}

  ngOnInit(): void {
    this.loadBooks();
    this.loadOrders();
  }

  loadBooks(): void {
    this.bookService.getAllBooks(0, 100).subscribe(data => this.books = data.content);
  }

  loadOrders(): void {
    this.orderService.getAllOrders().subscribe(data => this.orders = data);
  }

  addBook(): void {
    this.bookService.createBook(this.newBook).subscribe(() => {
      alert('Book added!');
      this.loadBooks();
      this.resetForm();
    });
  }

  deleteBook(id: number): void {
    if (confirm('Are you sure?')) {
      this.bookService.deleteBook(id).subscribe(() => this.loadBooks());
    }
  }

  updateOrderStatus(orderId: number, status: string): void {
    this.orderService.updateOrderStatus(orderId, status).subscribe(() => this.loadOrders());
  }

  resetForm(): void {
    this.newBook = { title: '', author: '', isbn: '', description: '', genre: '', price: 0, stockQuantity: 0, imageUrl: '', featured: false };
  }
}
