import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {TokenStorageService} from './token-storage.service';
import {Observable} from 'rxjs';
import {AirlineCompany} from './model/airline-company';


const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class AirlineCompanyService {
  token: string = this.tokenStorage.getToken();
  headers: HttpHeaders = new HttpHeaders({AuthToken: this.token});
  private URL = 'http://localhost:8080/api/airline-company';

  constructor(private http: HttpClient, private tokenStorage: TokenStorageService) {
    httpOptions.headers.set('AuthToken', this.token);
  }

  getAll(): Observable<AirlineCompany[]> {
    httpOptions.headers.set('AuthToken', this.token);
    return this.http.get<AirlineCompany[]>(this.URL, httpOptions);
  }
}
