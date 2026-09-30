import {Component, inject, OnInit, Signal, signal, WritableSignal} from '@angular/core';
import {ReservationService} from '../../../services/reservation.service';
import {ActivatedRoute, Router} from '@angular/router';
import {RoomResponseDTO} from '../../../model/roomResponseDTO';
import {HttpParams} from '@angular/common/http';
import {RoomService} from '../../../services/room.service';
import {RoomCardComponent} from '../room-card.component/room-card.component';
import {ReservationRoomRequestDTO} from '../../../model/reservationRoomRequestDTO';


@Component({
  selector: 'app-room-form',
  styleUrl: './room-form.component.css',
  templateUrl: './room-form.component.html',
  imports: [
    RoomCardComponent
  ],
})
export class RoomFormComponent implements OnInit {
  private readonly reservationService: ReservationService = inject(ReservationService)
  private readonly roomService: RoomService = inject(RoomService)
  private readonly route: ActivatedRoute = inject(ActivatedRoute)
  private readonly router: Router = inject(Router)

  private readonly reservationId: string = this.route.snapshot.queryParams['reservationId'] ?? '';
  private readonly checkIn: string = this.route.snapshot.queryParams['checkIn'] ?? '';
  private readonly checkOut: string = this.route.snapshot.queryParams['checkOut'] ?? '';

  private readonly _addedRoomsIds = signal<Set<string>>(new Set())
  protected addedRoomsIds = this._addedRoomsIds.asReadonly()

  private readonly _availableRooms: WritableSignal<RoomResponseDTO[]> = signal<RoomResponseDTO[]>([])
  protected availableRooms: Signal<RoomResponseDTO[]> = this._availableRooms.asReadonly()

  public ngOnInit(): void {
    const dates: HttpParams = new HttpParams()
      .set('checkIn', this.checkIn)
      .set('checkOut', this.checkOut)

    this.roomService.getAvailableRooms(dates).subscribe({
      next: (result: RoomResponseDTO[]): void => {
        this._availableRooms.set(result)
      },
      error: (err: Error): void => {
        console.log(err.message)
      }
    })
  }

  protected onAddRoom(roomId: string): void {
    const request: ReservationRoomRequestDTO = {
      roomId: roomId,
      reservationId: this.reservationId
    }
    this.reservationService.addRoomToReservation(request).subscribe({
      next: (): void => {
        this._addedRoomsIds.update(set => new Set(set).add(roomId))
      },
      error: (err: Error): void => {
        console.log(err.message)
      }
    })
  }

  protected isRoomAdded(roomId: string): boolean {
    return this.addedRoomsIds().has(roomId);
  }

  protected onFinish(): void {
    this.router.navigate(['/success'], {
      queryParams: {message: "Reservation successful."}
    })
  }
}
