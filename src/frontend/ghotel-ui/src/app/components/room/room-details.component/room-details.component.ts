import {Component, inject, OnInit, Signal, signal, WritableSignal} from '@angular/core';
import {RoomService} from '../../../services/room.service';
import {RoomReservationsResponseDTO} from '../../../model/roomReservationsResponseDTO';
import {ActivatedRoute} from '@angular/router';
import {DatePipe} from '@angular/common';

@Component({
  imports: [
    DatePipe
  ],
  selector: 'app-room-details.component',
  styleUrl: './room-details.component.css',
  templateUrl: './room-details.component.html',
})
export class RoomDetailsComponent implements OnInit {

  private readonly route = inject(ActivatedRoute);
  private readonly roomService: RoomService = inject(RoomService)
  private readonly _roomDetails: WritableSignal<RoomReservationsResponseDTO | undefined> = signal(undefined)
  protected roomDetails: Signal<RoomReservationsResponseDTO | undefined> = this._roomDetails.asReadonly()

  ngOnInit(): void {
    const id: string | null = this.route.snapshot.paramMap.get('id');

    if (id) {
      this.roomService.getRoomWithDetails(id).subscribe({
        next: (result: RoomReservationsResponseDTO) => {
          this._roomDetails.set(result)
        },
        error: (err) => {
          console.log(err.message)
        }
      })
    }
  }
}
