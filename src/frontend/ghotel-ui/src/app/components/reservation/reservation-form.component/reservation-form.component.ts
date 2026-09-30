import {Component, inject} from '@angular/core';
import {MatFormField, MatInput, MatLabel} from '@angular/material/input';
import {FormControl, FormGroup, ReactiveFormsModule} from '@angular/forms';
import {
  MatDatepickerModule,
  MatDatepickerToggle,
  MatDateRangeInput,
  MatDateRangePicker
} from '@angular/material/datepicker';
import {ReservationResponseDTO} from '../../../model/reservationResponseDTO';
import {ReservationService} from '../../../services/reservation.service';
import {ActivatedRoute, Router} from '@angular/router';
import {MatNativeDateModule} from '@angular/material/core';
import {MatIconModule} from '@angular/material/icon';
import {ReservationRequestDTO} from '../../../model/reservationRequestDTO';

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
    MatDatepickerModule,
    MatNativeDateModule,
    MatIconModule
  ],
})
export class ReservationFormComponent {
  private readonly router: Router = inject(Router)
  private readonly reservationService: ReservationService = inject(ReservationService)
  private readonly route: ActivatedRoute = inject(ActivatedRoute)

  private customerId: string = this.route.snapshot.queryParamMap.get('customerId') ?? '';

  protected readonly reservationForm: FormGroup = new FormGroup({
    details: new FormControl(''),
    checkIn: new FormControl(''),
    checkOut: new FormControl(''),
  })

  onSubmit(): void {
    const formValue = this.reservationForm.value;
    const checkIn: string = this.convertDate(formValue.checkIn, 14)
    const checkOut: string = this.convertDate(formValue.checkOut, 11)

    const request: ReservationRequestDTO = {
      customerId: this.customerId,
      details: formValue.details,
      checkIn: checkIn,
      checkOut: checkOut
    }
    this.reservationService.addReservation(request).subscribe({
      next: (result: ReservationResponseDTO) => {
        if (result.id != null) {
          this.router.navigate(["/reservation/new/rooms"], {
            queryParams: {
              reservationId: result.id,
              checkIn: checkIn,
              checkOut: checkOut
            }
          });
        }
      },
      error: (err: Error) => {
        console.log(err.message)
      }
    })
  }

  private convertDate(date: Date, hour: number): string {
    date.setHours(hour, 0, 0, 0)
    return date.toISOString()
  }
}
