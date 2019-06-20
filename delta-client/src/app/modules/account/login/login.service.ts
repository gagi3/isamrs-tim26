import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {LoginDTO} from './login.dto';
import {Observable} from 'rxjs';
import {JwtResponse} from './JwtResponse';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private loginURL = 'http://localhost:8080/api/user/signin';

  constructor(private http: HttpClient) {
  }

  attemptAuth(credentials: LoginDTO): Observable<JwtResponse> {
    return this.http.post<JwtResponse>(this.loginURL, credentials, httpOptions);
  }
}
