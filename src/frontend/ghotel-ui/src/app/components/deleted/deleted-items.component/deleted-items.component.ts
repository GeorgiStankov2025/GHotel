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
import {UserCardComponent} from '../../admin/user-card.component/user-card.component';
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

  protected selectedType: WritableSignal<string> = signal<string>('Rooms');
  // protected selectedType: FormControl<string> = new FormControl<string>('Rooms',{ nonNullable: true });

  private readonly _deletedRooms: WritableSignal<RoomResponseDTO[]> = signal<RoomResponseDTO[]>([]);
  protected deletedRooms: Signal<RoomResponseDTO[]> = this._deletedRooms.asReadonly();

  private readonly _deletedReservations: WritableSignal<ReservationResponseDTO[]> = signal<ReservationResponseDTO[]>([]);
  protected deletedReservations: Signal<ReservationResponseDTO[]> = this._deletedReservations.asReadonly();

  private readonly _deletedUsers: WritableSignal<UserResponseDTO[]> = signal<UserResponseDTO[]>([]);
  protected deletedUsers: Signal<UserResponseDTO[]> = this._deletedUsers.asReadonly();

  private readonly _deletedCustomers: WritableSignal<CustomerResponseDTO[]> = signal<CustomerResponseDTO[]>([]);
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
      case 'Rooms':
        this.getDeletedRooms();
        break;
      case 'Reservations':
        this.getDeletedReservations();
        break;
      case 'Customers':
        this.getDeletedCustomers();
        break;
      case 'Users':
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

  protected restoreDeletedUser(id: string): void {
    this.deletedItemsFacade.restoreDeletedUser(id).subscribe({
      next: (): void => {
        this._deletedUsers.update(list => list.filter(u => u.id !== id));
      },
      error: (err: Error): void => {
        console.log(err.message)
      }
    })
  }

  protected restoreDeletedRoom(id: string): void {
    this.deletedItemsFacade.restoreDeletedRoom(id).subscribe({
      next: (): void => {
        this._deletedRooms.update(list => list.filter(u => u.id !== id));
      },
      error: (err: Error): void => {
        console.log(err.message)
      }
    })
  }

  protected restoreDeletedReservation(id: string): void {
    this.deletedItemsFacade.restoreDeletedReservation(id).subscribe({
      next: (): void => {
        this._deletedReservations.update(list => list.filter(u => u.id !== id));
      },
      error: (err: Error): void => {
        console.log(err.message)
      }
    })
  }

  protected restoreDeletedCustomer(id: string): void {
    this.deletedItemsFacade.restoreDeletedCustomer(id).subscribe({
      next: (): void => {
        this._deletedCustomers.update(list => list.filter(u => u.id !== id));
      },
      error: (err: Error): void => {
        console.log(err.message)
      }
    })
  }
}
