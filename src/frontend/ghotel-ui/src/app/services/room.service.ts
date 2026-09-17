import {inject, Service} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {RoomResponseDTO} from '../model/roomResponseDTO';
import {RoomRequestDTO} from '../model/roomRequestDTO';
import {DeletedDTO} from '../model/deletedDTO';

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

  public editRoom(id: string, request: RoomRequestDTO): Observable<RoomResponseDTO> {
    return this.httpClient.put<RoomResponseDTO>(`${this.url}/${id}`, request)
  }

  public deleteRoom(id: string): Observable<DeletedDTO> {
    return this.httpClient.delete<DeletedDTO>(`${this.url}/${id}`)
  }
}
