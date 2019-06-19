import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {AirlineCompanyAdmin} from '../../account/profile/shared/model/airline-company-admin';
import {TokenStorageService} from '../../shared/token-storage.service';
import {Observable} from 'rxjs';
import {Flight} from '../../shared/model/flight';
import {FlightDTO} from './flight-dto';
import {FlightSearchDTO} from "../../shared/flight-search-dto";

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class FlightService {
  token: string = this.tokenStorage.getToken();
  username = '';
  headers: HttpHeaders = new HttpHeaders({AuthToken: this.token});
  private URL = 'http://localhost:8080/api/flight';
  admin: AirlineCompanyAdmin;

  constructor(private http: HttpClient, private tokenStorage: TokenStorageService) {
    httpOptions.headers.set('AuthToken', this.token);
  }

  get(): Observable<Flight[]> {
    // httpOptions.headers.set('AuthToken', this.token);
    return this.http.get<Flight[]>(this.URL, httpOptions);
  }

  getOne(ID): Observable<Flight> {
    httpOptions.headers.set('AuthToken', this.token);
    return this.http.get<Flight>(this.URL + '/' + ID, httpOptions);
  }

  getThis(): Observable<Flight> {
    httpOptions.headers.set('AuthToken', this.token);
    return this.http.get<Flight>(this.URL + '/get', httpOptions);
  }

  create(dto: FlightDTO): Observable<Flight> {
    httpOptions.headers.set('AuthToken', this.token);
    return this.http.post<Flight>(this.URL + '/add', dto, httpOptions);
  }

  update(flight: Flight): Observable<Flight> {
    httpOptions.headers.set('AuthToken', this.token);
    return this.http.post<Flight>(this.URL + '/update', flight, httpOptions);
  }

  delete(ID: BigInteger): Observable<boolean> {
    httpOptions.headers.set('AuthToken', this.token);
    return this.http.delete<boolean>(this.URL + '/delete/' + ID, httpOptions);
  }

  filterSearch(dto: FlightSearchDTO): Observable<Flight[]> {
    httpOptions.headers.set('AuthToken', this.token);
    return this.http.post<Flight[]>(this.URL + '/search', dto, httpOptions);
  }
}
