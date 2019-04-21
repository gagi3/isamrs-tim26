import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Passenger} from '../model/passenger';
import {AirlineCompanyAdmin} from '../model/airline-company-admin';
import {SystemAdmin} from '../model/system-admin';
import {Observable} from 'rxjs';
import {TokenStorageService} from '../../../../shared/token-storage.service';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json', AuthToken: '' })
};
@Injectable({
  providedIn: 'root'
})
export class ProfileService {
  token: string = this.tokenStorage.getToken();
  headers: HttpHeaders = new HttpHeaders({'AuthToken': this.token });
  passengerURL = 'http://localhost:8080/api/passenger';
  airlineCompanyAdminURL = 'http://localhost:8080/api/airline-company-admin';
  systemAdminURL = 'http://localhost:8080/api/system-admin';
  constructor(private http: HttpClient, private tokenStorage: TokenStorageService) {
    httpOptions.headers.set('AuthToken', this.token);
  }
  public updatePassenger(passenger: Passenger) {
    httpOptions.headers.set('AuthToken', this.token);
    const url = this.passengerURL + '/update';
    return this.http.post<Passenger>(url, passenger, httpOptions);
  }
  public getPassengerByUsername(username: string): Observable<Passenger> {
    httpOptions.headers.set('AuthToken', this.token);
    const url = this.passengerURL + '/get/';
    return this.http.get<Passenger>(url + username, {headers: this.headers});
  }
  public updateAirlineCompanyAdmin(admin: AirlineCompanyAdmin) {
    httpOptions.headers.set('AuthToken', this.token);
    const url = this.airlineCompanyAdminURL + '/update';
    return this.http.post<AirlineCompanyAdmin>(url, admin, httpOptions);
  }
  public getAirlineCompanyAdminByUsername(username: string) {
    httpOptions.headers.set('AuthToken', this.token);
    const url = this.airlineCompanyAdminURL + '/get/';
    return this.http.get<AirlineCompanyAdmin>(url + username, httpOptions);
  }
  public updateSystemAdmin(admin: SystemAdmin) {
    httpOptions.headers.set('AuthToken', this.token);
    const url = this.systemAdminURL + '/update';
    return this.http.post<SystemAdmin>(url, admin, httpOptions);
  }
  public getSystemAdminByUsername(username: string) {
    httpOptions.headers.set('AuthToken', this.token);
    const url = this.systemAdminURL + '/get/';
    return this.http.get<SystemAdmin>(url + username, httpOptions);
  }
}
