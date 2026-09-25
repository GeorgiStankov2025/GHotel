import {Component, inject, OnInit, Signal, signal, WritableSignal} from '@angular/core';
import {ReservationResponseDTO} from '../../../model/reservationResponseDTO';
import {ReservationService} from '../../../services/reservation.service';
import {RoomCardComponent} from '../../room/room-card.component/room-card.component';
import {ReservationCardComponent} from '../reservation-card.component/reservation-card.component';

@Component({
  imports: [
    RoomCardComponent,
    ReservationCardComponent
  ],
  selector: 'app-reservation-list',
  styleUrl: './reservation-list.component.css',
  templateUrl: './reservation-list.component.html',
})
export class ReservationListComponent implements OnInit {
  private readonly _reservations: WritableSignal<ReservationResponseDTO[]> = signal<ReservationResponseDTO[]>([])
  protected reservations: Signal<ReservationResponseDTO[]> = this._reservations.asReadonly()

  private readonly reservationService: ReservationService = inject(ReservationService)

  ngOnInit(): void {
    this.reservationService.getReservations().subscribe({
      next: (result: ReservationResponseDTO[]): void => {
        this._reservations.set(result)
      },
      error: (err: Error): void => {
        console.log(err.message)
      }
    })
  }
}
