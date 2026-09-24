import {Component, inject, input, InputSignal, OnInit, Signal, signal, WritableSignal} from '@angular/core';
import {RoomResponseDTO} from '../../../model/roomResponseDTO';
import {RoomService} from '../../../services/room.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-room-card',
  styleUrl: './room-card.component.css',
  templateUrl: './room-card.component.html',
  imports: [],
})
export class RoomCardComponent implements OnInit {

  private readonly _room: WritableSignal<RoomResponseDTO | undefined>
    = signal<RoomResponseDTO | undefined>(undefined)
  protected room: Signal<RoomResponseDTO | undefined> = this._room.asReadonly();
  public id: InputSignal<string> = input.required<string>()

  private readonly roomService: RoomService = inject(RoomService)

  private readonly router: Router = inject(Router)

  ngOnInit(): void {
    this.roomService.getRoom(this.id()).subscribe({
      next: (result): void => {
        this._room.set(result);
      },
      error: (err: Error): void => {
        console.log(err.message)
      }
    })
  }

  onSubmit(): void {
    this.router.navigate(["/rooms/" + this.id()])
  }
}
