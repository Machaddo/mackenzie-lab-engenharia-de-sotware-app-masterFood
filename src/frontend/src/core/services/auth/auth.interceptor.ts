import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from './auth.service';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const authToken = authService.getToken();

  if (!authToken || req.url.includes('/auth/login')) {
    return next(req);
  }
  
  const authReq = req.clone({
    setHeaders: {
      Bearer: `${authToken}`
    }
  });

  return next(authReq);
};