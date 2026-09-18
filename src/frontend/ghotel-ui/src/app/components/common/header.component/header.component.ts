import {Component, computed, inject, signal, Signal, WritableSignal} from '@angular/core';
import {NavigationEnd, Router, RouterLink} from '@angular/router';
import {AuthService} from '../../../services/auth.service';
import {toSignal} from '@angular/core/rxjs-interop';
import {filter, map} from 'rxjs';

@Component({
  imports: [
    RouterLink
  ],
  selector: 'app-header',
  styleUrl: './header.component.css',
  templateUrl: './header.component.html',
})
export class HeaderComponent {

  private readonly authService: AuthService = inject(AuthService)
  private readonly router: Router = inject(Router);

  protected readonly isLoggedIn:Signal<boolean> = toSignal(
    this.router.events.pipe(
      filter((event): event is NavigationEnd => event instanceof NavigationEnd),
      map(() => this.authService.isLoggedIn())
    ),
    { initialValue: this.authService.isLoggedIn() }
  );

}
