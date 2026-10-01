import {inject, Service} from '@angular/core';
import {UserRequestDTO} from '../model/userRequestDTO';
import {UserResponseDTO} from '../model/userResponseDTO';
import {HttpClient} from '@angular/common/http';
import {Observable, tap} from 'rxjs';
import {LoginRequestDTO} from '../model/loginRequestDTO';
import {AuthResponseDTO} from '../model/authResponseDTO';
import {TokenRequestDTO} from '../model/tokenRequestDTO';
import {JwtService} from './jwt.service';

@Service()
export class AuthService {

  private readonly url: string = 'http://localhost:8084/api/v1/auth'
  private readonly jwtService: JwtService = inject(JwtService)
  private readonly httpClient: HttpClient = inject(HttpClient)

  public register(request: UserRequestDTO): Observable<UserResponseDTO> {
    return this.httpClient.post<UserResponseDTO>(`${this.url}/register`, request);
  }

  public login(request: LoginRequestDTO): Observable<AuthResponseDTO> {
    return this.httpClient.post<AuthResponseDTO>(`${this.url}/login`, request).pipe(
      tap((res) => {
        if (res.accessToken && res.refreshToken) {
          this.jwtService.saveTokens(res.accessToken, res.refreshToken)
        }
      })
    );
  }

  public refreshToken(request: TokenRequestDTO): Observable<AuthResponseDTO> {
    return this.httpClient.post<AuthResponseDTO>(`${this.url}/refresh`, request);
  }

  public isLoggedIn(): boolean {
    return !!this.jwtService.getAccessToken();
  }

  public logout(): void {
    localStorage.removeItem('access_token');
    localStorage.removeItem('refresh_token')
  }


}
