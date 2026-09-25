import {Component, inject, input, InputSignal, OnInit, Signal, signal, WritableSignal} from '@angular/core';
import {ReservationRoomsCustomerResponseDTO} from '../../../model/reservationRoomsCustomerResponseDTO';
import {ReservationService} from '../../../services/reservation.service';
import {ReservationCardComponent} from '../reservation-card.component/reservation-card.component';
import {RoomCardComponent} from '../../room/room-card.component/room-card.component';
import {CustomerCardComponent} from '../../customer/customer-card.component/customer-card.component';

@Component({
  imports: [
    ReservationCardComponent,
    RoomCardComponent,
    CustomerCardComponent
  ],
  selector: 'app-reservation-details.component',
  styleUrl: './reservation-details.component.css',
  templateUrl: './reservation-details.component.html',
})
export class ReservationDetailsComponent implements OnInit {
  private readonly _reservationDetails: WritableSignal<ReservationRoomsCustomerResponseDTO | undefined> = signal<ReservationRoomsCustomerResponseDTO | undefined>(undefined)
  protected reservationDetails: Signal<ReservationRoomsCustomerResponseDTO | undefined> = this._reservationDetails.asReadonly()
  public id: InputSignal<string> = input.required<string>()

  private readonly reservationService: ReservationService = inject(ReservationService)

  ngOnInit(): void {
    this.reservationService.getReservationWithDetails(this.id()).subscribe({
      next: (result: ReservationRoomsCustomerResponseDTO): void => {
        this._reservationDetails.set(result)
      },
      error: (err: Error): void => {
        console.log(err.message)
      }
    })
  }
}
