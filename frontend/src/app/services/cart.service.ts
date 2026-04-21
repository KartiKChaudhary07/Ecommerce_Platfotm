import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

const CART_API = 'http://localhost:8080/api/v1/cart';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  constructor(private http: HttpClient) {}

  getCart(): Observable<any[]> {
    return this.http.get<any[]>(CART_API);
  }

  addToCart(bookId: number, quantity: number): Observable<any> {
    let params = new HttpParams().set('bookId', bookId).set('quantity', quantity);
    return this.http.post(`${CART_API}/add`, {}, { params });
  }

  updateQuantity(id: number, quantity: number): Observable<any> {
    let params = new HttpParams().set('quantity', quantity);
    return this.http.put(`${CART_API}/update/${id}`, {}, { params });
  }

  removeItem(id: number): Observable<any> {
    return this.http.delete(`${CART_API}/remove/${id}`);
  }

  clearCart(): Observable<any> {
    return this.http.delete(`${CART_API}/clear`);
  }

  getTotal(): Observable<number> {
    return this.http.get<number>(`${CART_API}/total`);
  }
}
