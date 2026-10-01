import {HttpErrorResponse, HttpHandlerFn, HttpInterceptorFn, HttpRequest} from '@angular/common/http';
import {inject} from '@angular/core';
import {catchError, switchMap, throwError} from 'rxjs';
import {AuthService} from '../services/auth.service';
import {Router} from '@angular/router';
import {JwtService} from '../services/jwt.service';

export const authInterceptor: HttpInterceptorFn = (req: HttpRequest<unknown>, next: HttpHandlerFn) => {
  const authService:AuthService = inject(AuthService);
  const jwtService:JwtService = inject(JwtService);
  const token = jwtService.getAccessToken();
  const router: Router = inject(Router);

  const authReq = token ? req.clone({setHeaders: {Authorization: `Bearer ${token}`}}) : req;
  const refreshToken: string | null = jwtService.getRefreshToken();

  return next(authReq).pipe(
    catchError((error: HttpErrorResponse) => {
      if (error.status === 401 && !req.url.includes('/auth/refresh') && refreshToken) {
        return authService.refreshToken({
          refreshToken: refreshToken
        }).pipe(
          switchMap((res: any) => {
            jwtService.saveTokens(res.accessToken, res.refreshToken);

            return next(req.clone({
              setHeaders: {Authorization: `Bearer ${res.accessToken}`}
            }));
          }),
          catchError((err) => {
            authService.logout();
            return throwError(() => err);
          })
        );
      }

      if (error.status === 403) {
        router.navigate(['/unauthorized'])
      }
      return throwError(() => error);
    })
  );
};
