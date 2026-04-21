import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { CartService } from '../../services/cart.service';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css'
})
export class CartComponent implements OnInit {
  cartItems: any[] = [];
  total = 0;
  loading = true;

  constructor(private cartService: CartService, private router: Router) {}

  ngOnInit(): void {
    this.loadCart();
  }

  loadCart(): void {
    this.cartService.getCart().subscribe({
      next: (data) => {
        this.cartItems = data;
        this.calculateTotal();
        this.loading = false;
      },
      error: (err) => {
        console.error(err);
        this.loading = false;
      }
    });
  }

  calculateTotal(): void {
    this.total = this.cartItems.reduce((acc, item) => acc + (item.book.price * item.quantity), 0);
  }

  updateQuantity(id: number, quantity: number): void {
    if (quantity < 1) return;
    this.cartService.updateQuantity(id, quantity).subscribe(() => this.loadCart());
  }

  removeItem(id: number): void {
    this.cartService.removeItem(id).subscribe(() => this.loadCart());
  }

  checkout(): void {
    this.router.navigate(['/checkout']);
  }
}
