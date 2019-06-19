import {Component} from '@angular/core';
import {TokenStorageService} from './modules/shared/token-storage.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'delta-client';
  private roles: string[];
  private authority: string;
  constructor(private tokenStorage: TokenStorageService) {}
  ngOnInit() {
    if (this.tokenStorage.getToken()) {
      this.roles = this.tokenStorage.getAuthorities();
      this.roles.every( role => {
        if (role === 'ROLE_SYSTEMADMIN') {
          this.authority = 'system-admin';
          return false;
        } else if (role === 'ROLE_AIRLINECOMPANYADMIN') {
          this.authority = 'airline-company-admin';
          return false;
        } else {
          this.authority = 'passenger';
          return true;
        }
      });
    }
  }
}
