import {Component} from '@angular/core';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-footer',
  styleUrl: './footer.component.css',
  templateUrl: './footer.component.html',
  imports: [
    RouterLink
  ],
})
export class FooterComponent {
  protected readonly currentYear: number = new Date().getFullYear();
}
