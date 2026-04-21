import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

const ORDER_API = 'http://localhost:8080/api/v1/orders';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  constructor(private http: HttpClient) {}

  placeOrder(shippingAddress: string, paymentMethod: string): Observable<any> {
    let params = new HttpParams()
      .set('shippingAddress', shippingAddress)
      .set('paymentMethod', paymentMethod);
    return this.http.post(`${ORDER_API}/place`, {}, { params });
  }

  getMyOrders(): Observable<any[]> {
    return this.http.get<any[]>(`${ORDER_API}/my-orders`);
  }

  getOrderById(id: number): Observable<any> {
    return this.http.get<any>(`${ORDER_API}/${id}`);
  }

  getAllOrders(): Observable<any[]> {
    return this.http.get<any[]>(`${ORDER_API}/all`);
  }

  updateOrderStatus(id: number, status: string): Observable<any> {
    let params = new HttpParams().set('status', status);
    return this.http.put(`${ORDER_API}/${id}/status`, {}, { params });
  }
}
