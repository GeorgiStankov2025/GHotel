import { Component } from '@angular/core';
import {NgOptimizedImage} from '@angular/common';

@Component({
  imports: [
    NgOptimizedImage
  ],
  selector: 'app-carousel',
  styleUrl: './carousel.component.css',
  templateUrl: './carousel.component.html',
})
export class CarouselComponent {}
