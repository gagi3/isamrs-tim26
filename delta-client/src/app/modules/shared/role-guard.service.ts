import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router} from '@angular/router';
import {LoginService} from '../account/login/login.service';
import {TokenStorageService} from './token-storage.service';

const TOKEN_KEY = 'AuthToken';
const AUTHORITIES_KEY = 'AuthAuthorities';

@Injectable({
  providedIn: 'root'
})
export class RoleGuardService implements CanActivate {

  constructor(public auth: LoginService, public router: Router, private tokenStorage: TokenStorageService) {
  }

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const expectedRole = route.data.expectedRole;
    const token = this.tokenStorage.getToken();
    if (!token) {
      window.alert('Please log in!');
      this.router.navigateByUrl('flight/view');
    }
    if (sessionStorage.getItem(TOKEN_KEY)) {
      JSON.parse(sessionStorage.getItem(AUTHORITIES_KEY)).forEach(authority => {
        if (authority.authority !== expectedRole) {
          window.alert('You do not have the authority to access this page.');
          this.router.navigateByUrl('flight/view');
          return false;
        }
      });
    }
    return true;
  }
}
