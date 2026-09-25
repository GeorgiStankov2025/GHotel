import {Component, inject, input, InputSignal} from '@angular/core';
import {RoomResponseDTO} from '../../../model/roomResponseDTO';
import {Router} from '@angular/router';

@Component({
  selector: 'app-room-card',
  styleUrl: './room-card.component.css',
  templateUrl: './room-card.component.html',
  imports: [],
})
export class RoomCardComponent {

  public room: InputSignal<RoomResponseDTO|undefined> = input.required<RoomResponseDTO|undefined>()
  public detailsIsAvailable:InputSignal<boolean>= input<boolean>(true)

  private readonly router: Router = inject(Router)

  onSubmit(): void {
    this.router.navigate(["/rooms/" + this.room()?.id])
  }
}
