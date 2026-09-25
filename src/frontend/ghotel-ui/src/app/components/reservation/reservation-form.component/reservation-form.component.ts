import {Component, inject, Signal, signal, WritableSignal} from '@angular/core';
import {MatFormField, MatInput, MatLabel} from '@angular/material/input';
import {FormControl, FormGroup, ReactiveFormsModule} from '@angular/forms';
import {
  MatDatepicker,
  MatDatepickerModule,
  MatDatepickerToggle,
  MatDateRangeInput,
  MatDateRangePicker
} from '@angular/material/datepicker';
import {ReservationResponseDTO} from '../../../model/reservationResponseDTO';
import {ReservationService} from '../../../services/reservation.service';
import {ReservationRequestDTO} from '../../../model/reservationRequestDTO';
import {Router} from '@angular/router';

@Component({
  selector: 'app-reservation-form',
  styleUrl: './reservation-form.component.css',
  templateUrl: './reservation-form.component.html',
  imports: [
    MatFormField,
    MatInput,
    MatLabel,
    ReactiveFormsModule,
    MatDateRangeInput,
    MatDatepickerToggle,
    MatDateRangePicker,
    MatDatepickerModule
  ],
})
export class ReservationFormComponent {

  private readonly _id: WritableSignal<string> = signal<string>('')
  protected id: Signal<string> = this._id.asReadonly()

  private readonly router: Router = inject(Router)
  private readonly reservationService: ReservationService = inject(ReservationService)

  protected readonly reservationForm: FormGroup = new FormGroup({
    details: new FormControl(''),
    checkIn: new FormControl(''),
    checkOut: new FormControl(''),
  })

  onSubmit(): void {
    const request: ReservationRequestDTO = this.reservationForm.value;
    this.reservationService.addReservation(request).subscribe({
      next: (result: ReservationResponseDTO) => {
        if (result.id != null) {
          this._id.set(result.id)
          this.router.navigate(["/"]);
        }
      },
      error: (err: Error) => {
        console.log(err.message)
      }
    })
  }
}
