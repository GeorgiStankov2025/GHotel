import {Component, inject, input, InputSignal} from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-reservation-success',
  templateUrl: './success.component.html',
  standalone: true,
  imports: []
})
export class SuccessComponent {
  private readonly router = inject(Router);

  public message:InputSignal<string> = input.required<string>();

  toHomePage(): void {
    this.router.navigate(['/']);
  }
}
