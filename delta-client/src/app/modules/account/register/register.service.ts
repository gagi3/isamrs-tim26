import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {RegisterDTO} from './register.dto';
import {Observable} from 'rxjs';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  private signupURL = 'http://localhost:8080/api/user/signup/';
  private validateURL = 'http://localhost:8080/api/user/validate/token=';

  constructor(private http: HttpClient) {
  }

  register(info: RegisterDTO, type: string): Observable<string> {
    return this.http.post<string>(this.signupURL + type, info, httpOptions);
  }

  validate(token: string) {
    return this.http.get(this.validateURL + token);
  }
}
