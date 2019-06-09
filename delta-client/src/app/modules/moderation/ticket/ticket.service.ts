import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {AirlineCompanyAdmin} from '../../account/profile/shared/model/airline-company-admin';
import {TokenStorageService} from '../../shared/token-storage.service';
import {Observable} from 'rxjs';
import {Ticket} from '../../shared/model/ticket';
import {Flight} from '../../shared/model/flight';
import {Seat} from '../../shared/model/seat';
import {DiscountTicketsDTO} from './discount-tickets-dto';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type' : 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class TicketService {
  token: string = this.tokenStorage.getToken();
  username = '';
  headers: HttpHeaders = new HttpHeaders({AuthToken: this.token });
  private URL = 'http://localhost:8080/api/ticket';
  admin: AirlineCompanyAdmin;

  constructor(private http: HttpClient, private tokenStorage: TokenStorageService) {
    httpOptions.headers.set('AuthToken', this.token);
  }
  get(): Observable<Ticket[]> {
    // httpOptions.headers.set('AuthToken', this.token);
    return this.http.get<Ticket[]>(this.URL, httpOptions);
  }
  getOne(ID: BigInteger): Observable<Ticket> {
    httpOptions.headers.set('AuthToken', this.token);
    return this.http.get<Ticket>(this.URL + '/' + ID, httpOptions);
  }
  create(flight: Flight, seat: Seat): Observable<Ticket> {
    httpOptions.headers.set('AuthToken', this.token);
    const data = JSON.stringify([flight, seat]);
    return this.http.post<Ticket>(this.URL + '/add', data, httpOptions);
  }
  update(ticket: Ticket): Observable<Ticket> {
    httpOptions.headers.set('AuthToken', this.token);
    return this.http.post<Ticket>(this.URL + '/update', ticket, httpOptions);
  }
  delete(ID: BigInteger): Observable<boolean> {
    httpOptions.headers.set('AuthToken', this.token);
    return this.http.delete<boolean>(this.URL + '/delete/' + ID, httpOptions);
  }
  discount(dto: DiscountTicketsDTO): Observable<Ticket[]> {
    httpOptions.headers.set('AuthToken', this.token);
    return this.http.post<Ticket[]>(this.URL + '/discount', dto, httpOptions);
  }
  getDiscounted(): Observable<Ticket[]> {
    httpOptions.headers.set('AuthToken', this.token);
    return this.http.get<Ticket[]>(this.URL + '/get/discounted', httpOptions);
  }
  reserve(dto: DiscountTicketsDTO): Observable<Ticket[]> {
    httpOptions.headers.set('AuthToken', this.token);
    return this.http.post<Ticket[]>(this.URL + '/reserve', dto, httpOptions);
  }
  quickReserve(ticket: Ticket): Observable<Ticket> {
    httpOptions.headers.set('AuthToken', this.token);
    return this.http.post<Ticket>(this.URL + '/reserve/quick', ticket, httpOptions);
  }
}
