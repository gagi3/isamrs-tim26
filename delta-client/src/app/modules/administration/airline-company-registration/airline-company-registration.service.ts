import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {AirlineCompanyRegistrationDTO} from './airline-company-registration-dto';
import {AirlineCompany} from '../../shared/model/airline-company';
import {Observable} from 'rxjs';
import {TokenStorageService} from '../../shared/token-storage.service';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type' : 'application/json'})
};
@Injectable({
  providedIn: 'root'
})
export class AirlineCompanyRegistrationService {
  token: string = this.tokenStorage.getToken();
  headers: HttpHeaders = new HttpHeaders({AuthToken: this.token });
  private registrationURL = 'http://localhost:8080/api/airline-company/add';

  constructor(private http: HttpClient, private tokenStorage: TokenStorageService) {
    httpOptions.headers.set('AuthToken', this.token);
  }
  register(dto: AirlineCompanyRegistrationDTO): Observable<AirlineCompany> {
    httpOptions.headers.set('AuthToken', this.token);
    return this.http.post<AirlineCompany>(this.registrationURL, dto, httpOptions);
  }
}
