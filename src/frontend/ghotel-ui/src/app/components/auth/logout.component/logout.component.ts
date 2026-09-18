import {Component, inject, OnInit} from '@angular/core';
import {AuthService} from '../../../services/auth.service';
import {Router} from '@angular/router';

@Component({
  imports: [],
  selector: 'app-logout',
  styleUrl: './logout.component.css',
  templateUrl: './logout.component.html',
})
export class LogoutComponent implements OnInit {

  authService: AuthService = inject(AuthService)
  router:Router=inject(Router)

  ngOnInit(): void {
    if (this.authService.isLoggedIn()) {
      this.authService.logout();
      this.router.navigate(['/login'])
    }
  }
}
