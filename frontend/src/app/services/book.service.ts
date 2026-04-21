import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Book } from '../models/book.model';

const BOOK_API = 'http://localhost:8080/api/v1/books';

@Injectable({
  providedIn: 'root'
})
export class BookService {
  constructor(private http: HttpClient) {}

  getAllBooks(page: number = 0, size: number = 10): Observable<any> {
    let params = new HttpParams().set('page', page).set('size', size);
    return this.http.get(BOOK_API, { params });
  }

  getBookById(id: number): Observable<Book> {
    return this.http.get<Book>(`${BOOK_API}/${id}`);
  }

  searchBooks(query: string, page: number = 0, size: number = 10): Observable<any> {
    let params = new HttpParams().set('query', query).set('page', page).set('size', size);
    return this.http.get(`${BOOK_API}/search`, { params });
  }

  getFeaturedBooks(): Observable<Book[]> {
    return this.http.get<Book[]>(`${BOOK_API}/featured`);
  }

  createBook(book: any): Observable<Book> {
    return this.http.post<Book>(BOOK_API, book);
  }

  updateBook(id: number, book: any): Observable<Book> {
    return this.http.put<Book>(`${BOOK_API}/${id}`, book);
  }

  deleteBook(id: number): Observable<any> {
    return this.http.delete(`${BOOK_API}/${id}`);
  }
}
