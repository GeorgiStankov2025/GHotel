import {Component, inject, input, InputSignal, output, OutputEmitterRef} from '@angular/core';
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

  public isDeleted: InputSignal<boolean> = input<boolean>(false);
  public restore: OutputEmitterRef<string> = output<string>();
  public delete: OutputEmitterRef<string> = output<string>();

  onSubmit(): void {
    this.router.navigate(["/reservations/"+this.reservation()?.id])
  }

  onDelete(): void {
    const id: string | undefined = this.reservation()?.id
    if (id) {
      this.delete.emit(id)
    }
  }

  onRestore(): void {
    const id: string | undefined = this.reservation()?.id
    if (id) {
      this.restore.emit(id)
    }
  }
}
