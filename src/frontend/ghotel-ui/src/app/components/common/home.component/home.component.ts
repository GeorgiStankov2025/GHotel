import {Component} from '@angular/core';
import {BannerComponent} from '../banner.component/banner.component';
import {CarouselComponent} from '../carousel.component/carousel.component';

@Component({
  imports: [
    BannerComponent,
    CarouselComponent
  ],
  selector: 'app-home',
  styleUrl: './home.component.css',
  templateUrl: './home.component.html',
})
export class HomeComponent {
}
