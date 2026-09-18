import {HttpErrorResponse, HttpHandlerFn, HttpInterceptorFn, HttpRequest} from '@angular/common/http';
import {inject} from '@angular/core';
import {catchError, switchMap, throwError} from 'rxjs';
import {AuthService} from '../services/auth.service';

export const authInterceptor: HttpInterceptorFn = (req: HttpRequest<unknown>, next: HttpHandlerFn) => {
  const authService = inject(AuthService);
  const token = authService.getAccessToken();

  const authReq = token ? req.clone({setHeaders: {Authorization: `Bearer ${token}`}}) : req;
  const refreshToken: string | null = authService.getRefreshToken();

  return next(authReq).pipe(
    catchError((error: HttpErrorResponse) => {
      if (error.status === 401 && !req.url.includes('/auth/refresh') && refreshToken) {
        return authService.refreshToken({
          refreshToken: refreshToken
        }).pipe(
          switchMap((res: any) => {
            authService.saveTokens(res.accessToken, res.refreshToken);

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

      return throwError(() => error);
    })
  );
};
