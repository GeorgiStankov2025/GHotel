import {Component, inject, input, InputSignal} from '@angular/core';
import {ReservationResponseDTO} from '../../../model/reservationResponseDTO';
import {Router} from '@angular/router';
import {DatePipe} from '@angular/common';

@Component({
  imports: [
    DatePipe
  ],
  selector: 'app-reservation-card',
  styleUrl: './reservation-card.component.css',
  templateUrl: './reservation-card.component.html',
})
export class ReservationCardComponent {
  public reservation: InputSignal<ReservationResponseDTO|undefined> = input.required<ReservationResponseDTO|undefined>()
  public detailsIsAvailable:InputSignal<boolean>= input<boolean>(true)

  private readonly router: Router = inject(Router)

  onSubmit(): void {
    this.router.navigate(["/reservations/"+this.reservation()?.id])
  }
}
