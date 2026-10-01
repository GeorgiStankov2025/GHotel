import { Service } from '@angular/core';

@Service()
export class JwtService {
  public getAccessToken(): string | null {
    return localStorage.getItem('access_token');
  }

  public getRefreshToken(): string | null {
    return localStorage.getItem('refresh_token');
  }

  public saveTokens(accessToken: string, refreshToken: string):void {
    localStorage.setItem('access_token', accessToken);
    localStorage.setItem('refresh_token', refreshToken);
  }

  isCurrentlyAuthorizedRole(role: string): boolean {
    return this.getUserRole() === role;
  }

  private getUserRole(): string | null {
    const token:string|null = this.getAccessToken();
    if (!token) return null;

    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      return payload.role || null;
    } catch {
      return null;
    }
  }
}
