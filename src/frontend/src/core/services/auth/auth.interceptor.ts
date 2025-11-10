import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from './auth.service';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const authToken = authService.getToken();

  // Se a URL não precisa de autenticação (ex: login/cadastro) ou não há token, 
  // simplesmente passe a requisição adiante.
  if (!authToken || req.url.includes('/auth/login')) {
    return next(req);
  }
  
  // Clona a requisição e adiciona o cabeçalho de Autorização
  const authReq = req.clone({
    setHeaders: {
      Authorization: `Bearer ${authToken}`
    }
  });

  // Envia a requisição modificada
  return next(authReq);
};