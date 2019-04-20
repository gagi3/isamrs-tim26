import {Injectable} from '@angular/core';
import {HTTP_INTERCEPTORS, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {TokenStorageService} from './token-storage.service';

const TOKEN_HEADER_KEY = 'Authorization';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private tokenStorage: TokenStorageService) {}
  intercept(req: HttpRequest<any>, next: HttpHandler) {
    let authRequest = req;
    const token = this.tokenStorage.getToken();
    if (token != null) {
      authRequest = req.clone({ headers: req.headers.set(TOKEN_HEADER_KEY, 'Bearer ' + token) });
    }
    return next.handle(authRequest);
  }
}

export const httpInterceptorProviders = [
  {
    provide: HTTP_INTERCEPTORS,
    useClass: AuthInterceptor,
    multi: true
  }
];
