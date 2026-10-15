import {inject, Service} from '@angular/core';
import {UserService} from '../services/user.service';
import {RoomService} from '../services/room.service';
import {ReservationService} from '../services/reservation.service';
import {CustomerService} from '../services/customer.service';
import {Observable} from 'rxjs';
import {CustomerResponseDTO} from '../model/customerResponseDTO';
import {UserResponseDTO} from '../model/userResponseDTO';
import {RoomResponseDTO} from '../model/roomResponseDTO';
import {ReservationResponseDTO} from '../model/reservationResponseDTO';

@Service()
export class DeletedItemsFacade {
  private readonly userService: UserService = inject(UserService);
  private readonly roomService: RoomService = inject(RoomService);
  private readonly reservationService: ReservationService = inject(ReservationService);
  private readonly customerService: CustomerService = inject(CustomerService);

  public getDeletedCustomers(): Observable<CustomerResponseDTO[]> {
    return this.customerService.getDeletedCustomers();
  }

  public getDeletedUsers(): Observable<UserResponseDTO[]> {
    return this.userService.getDeletedUsers();
  }

  public getDeletedRooms(): Observable<RoomResponseDTO[]> {
    return this.roomService.getDeletedRooms();
  }

  public getDeletedReservations(): Observable<ReservationResponseDTO[]> {
    return this.reservationService.getDeletedReservations();
  }

  public restoreDeletedUser(id: string): Observable<UserResponseDTO> {
    return this.userService.restoreUser(id);
  }

  public restoreDeletedRoom(id: string): Observable<RoomResponseDTO> {
    return this.roomService.restoreRoom(id);
  }

  public restoreDeletedReservation(id: string): Observable<ReservationResponseDTO> {
    return this.reservationService.restoreReservation(id);
  }

  public restoreDeletedCustomer(id: string): Observable<CustomerResponseDTO> {
    return this.customerService.restoreCustomer(id);
  }
}
