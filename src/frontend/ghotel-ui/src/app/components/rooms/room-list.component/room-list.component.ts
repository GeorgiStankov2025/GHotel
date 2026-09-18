import {Component, inject, OnInit, Signal, signal, WritableSignal} from '@angular/core';
import {RoomResponseDTO} from '../../../model/roomResponseDTO';
import {RoomService} from '../../../services/room.service';
import {RoomCardComponent} from '../../room/room-card.component/room-card.component';

@Component({
  selector: 'app-room-list',
  styleUrl: './room-list.component.css',
  templateUrl: './room-list.component.html',
  imports: [
    RoomCardComponent
  ],
})
export class RoomListComponent implements OnInit {
  private readonly _rooms: WritableSignal<RoomResponseDTO[]> = signal([]);
  protected rooms: Signal<RoomResponseDTO[]> = this._rooms.asReadonly()

  private readonly roomService: RoomService = inject(RoomService)

  ngOnInit(): void {
    this.roomService.getRooms().subscribe({
      next: (result: RoomResponseDTO[]): void => {
        this._rooms.set(result);
      },
      error: (err: Error):void => {
        console.log(err.message)
      }
    })

  }
}
