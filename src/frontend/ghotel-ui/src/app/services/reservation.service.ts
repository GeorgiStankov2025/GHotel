import {inject, Service} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ReservationRequestDTO} from '../model/reservationRequestDTO';
import {Observable} from 'rxjs';
import {ReservationResponseDTO} from '../model/reservationResponseDTO';
import {DeletedDTO} from '../model/deletedDTO';

@Service()
export class ReservationService {
  private readonly url: string = 'http://localhost:8084/api/v1/reservation'

  private readonly httpClient: HttpClient = inject(HttpClient)

  public addReservation(request: ReservationRequestDTO): Observable<ReservationResponseDTO> {
    return this.httpClient.post<ReservationResponseDTO>(this.url, request)
  }

  public getReservation(id: string): Observable<ReservationResponseDTO> {
    return this.httpClient.get<ReservationResponseDTO>(`${this.url}/${id}`)
  }

  public getReservations(): Observable<ReservationResponseDTO[]> {
    return this.httpClient.get<ReservationResponseDTO[]>(this.url)
  }

  public editReservation(id: string, request: ReservationRequestDTO): Observable<ReservationResponseDTO> {
    return this.httpClient.put<ReservationResponseDTO>(`${this.url}/${id}`, request)
  }

  public deleteReservation(id: string): Observable<DeletedDTO> {
    return this.httpClient.delete<DeletedDTO>(`${this.url}/${id}`)
  }
}
