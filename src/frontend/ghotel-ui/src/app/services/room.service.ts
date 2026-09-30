import {inject, Service} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {RoomResponseDTO} from '../model/roomResponseDTO';
import {RoomRequestDTO} from '../model/roomRequestDTO';
import {DeletedDTO} from '../model/deletedDTO';
import {RoomReservationsResponseDTO} from '../model/roomReservationsResponseDTO';

@Service()
export class RoomService {
  private readonly url: string = 'http://localhost:8084/api/v1/room'

  private readonly httpClient: HttpClient = inject(HttpClient)

  public addRoom(request: RoomRequestDTO): Observable<RoomResponseDTO> {
    return this.httpClient.post<RoomResponseDTO>(this.url, request)
  }

  public getRoom(id: string): Observable<RoomResponseDTO> {
    return this.httpClient.get<RoomResponseDTO>(`${this.url}/${id}`)
  }

  public getRooms(): Observable<RoomResponseDTO[]> {
    return this.httpClient.get<RoomResponseDTO[]>(this.url)
  }

  public getAvailableRooms(dates:HttpParams): Observable<RoomResponseDTO[]> {
    return this.httpClient.get<RoomResponseDTO[]>(`${this.url}/available`,{params: dates})
  }

  public getRoomWithDetails(id: string): Observable<RoomReservationsResponseDTO> {
    return this.httpClient.get<RoomReservationsResponseDTO>(`${this.url}/${id}/reservations`)
  }

  public editRoom(id: string, request: RoomRequestDTO): Observable<RoomResponseDTO> {
    return this.httpClient.put<RoomResponseDTO>(`${this.url}/${id}`, request)
  }

  public deleteRoom(id: string): Observable<DeletedDTO> {
    return this.httpClient.delete<DeletedDTO>(`${this.url}/${id}`)
  }
}
