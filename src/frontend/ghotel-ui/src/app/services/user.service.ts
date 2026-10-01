import {inject, Service} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {UserRequestDTO} from '../model/userRequestDTO';
import {Observable} from 'rxjs';
import {UserResponseDTO} from '../model/userResponseDTO';
import {DeletedDTO} from '../model/deletedDTO';

@Service()
export class UserService {
  private readonly url: string = 'http://localhost:8084/api/v1/user'

  private readonly httpClient: HttpClient = inject(HttpClient)

  public addUser(request: UserRequestDTO): Observable<UserResponseDTO> {
    return this.httpClient.post<UserResponseDTO>(this.url, request)
  }

  public getUser(id: string): Observable<UserResponseDTO> {
    return this.httpClient.get<UserResponseDTO>(`${this.url}/${id}`)
  }

  public getUsers(): Observable<UserResponseDTO[]> {
    return this.httpClient.get<UserResponseDTO[]>(this.url)
  }

  public deleteUser(id: string | undefined): Observable<DeletedDTO> {
    return this.httpClient.delete<DeletedDTO>(`${this.url}/${id}`)
  }
}
