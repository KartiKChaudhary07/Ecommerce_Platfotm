import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { OrderService } from '../../services/order.service';
import { CartService } from '../../services/cart.service';

@Component({
  selector: 'app-checkout',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './checkout.component.html',
  styleUrl: './checkout.component.css'
})
export class CheckoutComponent implements OnInit {
  shippingAddress = '';
  paymentMethod = 'COD';
  total = 0;

  constructor(
    private orderService: OrderService,
    private cartService: CartService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.cartService.getTotal().subscribe(t => this.total = t);
  }

  placeOrder(): void {
    if (!this.shippingAddress) {
      alert('Please enter shipping address');
      return;
    }

    this.orderService.placeOrder(this.shippingAddress, this.paymentMethod).subscribe({
      next: () => {
        alert('Order placed successfully!');
        this.router.navigate(['/orders']);
      },
      error: (err) => alert('Failed to place order: ' + err.error.message)
    });
  }
}
