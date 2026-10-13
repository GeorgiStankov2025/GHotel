import {ChangeDetectionStrategy, Component, inject, OnInit, signal, Signal, WritableSignal} from '@angular/core';
import {DeletedItemsFacade} from '../../../facades/deleted-items.facade';
import {RoomResponseDTO} from '../../../model/roomResponseDTO';
import {ReservationResponseDTO} from '../../../model/reservationResponseDTO';
import {UserResponseDTO} from '../../../model/userResponseDTO';
import {CustomerResponseDTO} from '../../../model/customerResponseDTO';
import {JwtService} from '../../../services/jwt.service';
import {RoomCardComponent} from '../../room/room-card.component/room-card.component';
import {ReservationCardComponent} from '../../reservation/reservation-card.component/reservation-card.component';
import {CustomerCardComponent} from '../../customer/customer-card.component/customer-card.component';
import {UserCardComponent} from '../user-card.component/user-card.component';
import {ReactiveFormsModule} from '@angular/forms';

@Component({
  imports: [
    RoomCardComponent,
    ReservationCardComponent,
    CustomerCardComponent,
    UserCardComponent,
    ReactiveFormsModule
  ],
  selector: 'app-deleted-items',
  styleUrl: './deleted-items.component.css',
  templateUrl: './deleted-items.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class DeletedItemsComponent implements OnInit {
  private readonly deletedItemsFacade: DeletedItemsFacade = inject(DeletedItemsFacade);
  protected readonly jwtService: JwtService = inject(JwtService)

  protected selectedType: WritableSignal<string> = signal<string>('rooms');
  // protected selectedType: FormControl<string> = new FormControl<string>('rooms',{ nonNullable: true });

  private _deletedRooms: WritableSignal<RoomResponseDTO[]> = signal<RoomResponseDTO[]>([]);
  protected deletedRooms: Signal<RoomResponseDTO[]> = this._deletedRooms.asReadonly();

  private _deletedReservations: WritableSignal<ReservationResponseDTO[]> = signal<ReservationResponseDTO[]>([]);
  protected deletedReservations: Signal<ReservationResponseDTO[]> = this._deletedReservations.asReadonly();

  private _deletedUsers: WritableSignal<UserResponseDTO[]> = signal<UserResponseDTO[]>([]);
  protected deletedUsers: Signal<UserResponseDTO[]> = this._deletedUsers.asReadonly();

  private _deletedCustomers: WritableSignal<CustomerResponseDTO[]> = signal<CustomerResponseDTO[]>([]);
  protected deletedCustomers: Signal<CustomerResponseDTO[]> = this._deletedCustomers.asReadonly();

  ngOnInit(): void {
    this.loadItems()
  }

  onTypeChange(type: string): void {
    this.selectedType.set(type)
    this.loadItems()
  }

  loadItems(): void {
    switch (this.selectedType()) {
      case 'rooms':
        this.getDeletedRooms();
        break;
      case 'reservations':
        this.getDeletedReservations();
        break;
      case 'customers':
        this.getDeletedCustomers();
        break;
      case 'users':
        this.getDeletedUsers();
        break;
    }
  }

  private getDeletedRooms(): void {
    this.deletedItemsFacade.getDeletedRooms().subscribe({
      next: (result: RoomResponseDTO[]): void => {
        this._deletedRooms.set(result);
      },
      error: (err: Error): void => {
        console.log(err.message)
      }
    })
  }

  private getDeletedCustomers(): void {
    this.deletedItemsFacade.getDeletedCustomers().subscribe({
      next: (result: CustomerResponseDTO[]): void => {
        this._deletedCustomers.set(result);
      },
      error: (err: Error): void => {
        console.log(err.message)
      }
    })
  }

  private getDeletedReservations(): void {
    this.deletedItemsFacade.getDeletedReservations().subscribe({
      next: (result: ReservationResponseDTO[]): void => {
        this._deletedReservations.set(result);
      },
      error: (err: Error): void => {
        console.log(err.message)
      }
    })
  }

  private getDeletedUsers(): void {
    this.deletedItemsFacade.getDeletedUsers().subscribe({
      next: (result: UserResponseDTO[]): void => {
        this._deletedUsers.set(result);
      },
      error: (err: Error): void => {
        console.log(err.message)
      }
    })
  }
}
