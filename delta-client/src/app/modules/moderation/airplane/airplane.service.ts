import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {TokenStorageService} from '../../shared/token-storage.service';
import {AirplaneDTO} from './airplane-dto';
import {Airplane} from '../../shared/model/airplane';
import {Observable} from 'rxjs';
import {AirlineCompanyAdmin} from '../../account/profile/shared/model/airline-company-admin';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type' : 'application/json'})
};
@Injectable({
  providedIn: 'root'
})
export class AirplaneService {
  token: string = this.tokenStorage.getToken();
  username = '';
  headers: HttpHeaders = new HttpHeaders({AuthToken: this.token });
  private addAirplaneURL = 'http://localhost:8080/api/airplane/add';
  admin: AirlineCompanyAdmin;

  constructor(private http: HttpClient, private tokenStorage: TokenStorageService) {
    httpOptions.headers.set('AuthToken', this.token);
  }
  add(dto: AirplaneDTO): Observable<Airplane> {
    httpOptions.headers.set('AuthToken', this.token);
    return this.http.post<Airplane>(this.addAirplaneURL, dto, httpOptions);
  }
}
