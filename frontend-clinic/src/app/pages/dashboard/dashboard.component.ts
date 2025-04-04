import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent {
  username: string | null = '';

  constructor(private authService: AuthService, private router: Router) {
    this.username = this.authService.getUsername();
  }

  goToUsers(): void {
    this.router.navigate(['/users']);
  }

  goToCategories(): void {
    this.router.navigate(['/categories']);
  }

  goToProducts(): void {
    this.router.navigate(['/products']);
  }  

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
