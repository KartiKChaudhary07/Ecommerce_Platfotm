import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { CartService } from '../../services/cart.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent implements OnInit {
  isLoggedIn = false;
  isAdmin = false;
  username?: string;
  cartCount = 0;

  constructor(
    private authService: AuthService, 
    private router: Router,
    private cartService: CartService
  ) {}

  ngOnInit(): void {
    this.authService.currentUser.subscribe(user => {
      this.isLoggedIn = !!user.token;
      this.isAdmin = this.authService.isAdmin();
      this.username = user.username;
      if (this.isLoggedIn) {
        this.updateCartCount();
      } else {
        this.cartCount = 0;
      }
    });
  }

  updateCartCount(): void {
    this.cartService.getCart().subscribe(items => {
      this.cartCount = items.length;
    });
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
