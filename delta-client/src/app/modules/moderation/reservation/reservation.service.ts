import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {TokenStorageService} from '../../shared/token-storage.service';
import {BusinessReportDTO} from './business-report-dto';
import {AirlineCompanyAdmin} from '../../account/profile/shared/model/airline-company-admin';
import {Observable} from 'rxjs';
import {Reservation} from '../../shared/model/reservation';
import {Flight} from '../../shared/model/flight';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class ReservationService {
  token: string = this.tokenStorage.getToken();
  username = '';
  headers: HttpHeaders = new HttpHeaders({AuthToken: this.token});
  private URL = 'http://localhost:8080/api/reservation';
  admin: AirlineCompanyAdmin;

  constructor(private http: HttpClient, private tokenStorage: TokenStorageService) {
    httpOptions.headers.set('AuthToken', this.token);
  }

  businessReport(dto: BusinessReportDTO): Observable<Reservation[]> {
    httpOptions.headers.set('AuthToken', this.token);
    return this.http.post<Reservation[]>(this.URL + '/report', dto, httpOptions);
  }

  getFlight(ID: any): Observable<Flight> {
    httpOptions.headers.set('AuthToken', this.token);
    return this.http.get<Flight>(this.URL + '/get/flight/' + ID, httpOptions);
  }
}
