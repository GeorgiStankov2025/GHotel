import {inject, Service} from '@angular/core';
import {UserRequestDTO} from '../model/userRequestDTO';
import {UserResponseDTO} from '../model/userResponseDTO';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {LoginRequestDTO} from '../model/loginRequestDTO';
import {AuthResponseDTO} from '../model/authResponseDTO';

@Service()
export class AuthService {

  private readonly url: string = 'http://localhost:8084/api/v1/auth'

  private readonly httpClient: HttpClient = inject(HttpClient)

  public register(request: UserRequestDTO): Observable<UserResponseDTO> {
    return this.httpClient.post<UserResponseDTO>(this.url+'/register', request);
  }

  public login(request: LoginRequestDTO): Observable<AuthResponseDTO> {
    return this.httpClient.post<AuthResponseDTO>(this.url+'/login',request)
  }
}
