import {Component, signal} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {HeaderComponent} from './components/common/header.component/header.component';
import {FooterComponent} from './components/common/footer.component/footer.component';

@Component({
  imports: [RouterOutlet, HeaderComponent, FooterComponent],
  selector: 'app-root',
  styleUrl: './app.css',
  templateUrl: './app.html',
})
export class App {
  protected readonly title = signal('ghotel-ui');
}
