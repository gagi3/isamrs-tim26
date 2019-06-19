import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {TokenStorageService} from '../../../../shared/token-storage.service';
import {Observable} from 'rxjs';
import {FriendshipRequest} from '../../../../shared/model/friendship-request';
import {FriendshipRequestDTO} from '../../../../consumption/interaction/friendship-request-dto';
import {FriendshipDTO} from '../../../../consumption/interaction/friendship-dto';
import {Passenger} from '../model/passenger';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json', AuthToken: ''})
};

@Injectable({
  providedIn: 'root'
})
export class FriendshipRequestService {
  token: string = this.tokenStorage.getToken();
  headers: HttpHeaders = new HttpHeaders({AuthToken: this.token});
  URL = 'http://localhost:8080/api/friendship-request';

  constructor(private http: HttpClient, private tokenStorage: TokenStorageService) {
    httpOptions.headers.set('AuthToken', this.token);
  }

  get(): Observable<FriendshipRequest[]> {
    // httpOptions.headers.set('AuthToken', this.token);
    return this.http.get<FriendshipRequest[]>(this.URL, httpOptions);
  }

  getOne(ID: BigInteger): Observable<FriendshipRequest> {
    httpOptions.headers.set('AuthToken', this.token);
    return this.http.get<FriendshipRequest>(this.URL + '/' + ID, httpOptions);
  }

  create(dto: FriendshipRequestDTO): Observable<FriendshipRequest> {
    httpOptions.headers.set('AuthToken', this.token);
    return this.http.post<FriendshipRequest>(this.URL + '/add', dto, httpOptions);
  }

  update(req: FriendshipRequest): Observable<FriendshipRequest> {
    httpOptions.headers.set('AuthToken', this.token);
    return this.http.post<FriendshipRequest>(this.URL + '/update', req, httpOptions);
  }

  delete(ID: BigInteger): Observable<boolean> {
    httpOptions.headers.set('AuthToken', this.token);
    return this.http.delete<boolean>(this.URL + '/delete/' + ID, httpOptions);
  }

  accept(req: FriendshipRequest): Observable<boolean> {
    httpOptions.headers.set('AuthToken', this.token);
    return this.http.post<boolean>(this.URL + '/accept', req, httpOptions);
  }

  getSent(): Observable<FriendshipRequest[]> {
    httpOptions.headers.set('AuthToken', this.token);
    return this.http.get<FriendshipRequest[]>(this.URL + '/get/sent', httpOptions);
  }

  getReceived(): Observable<FriendshipRequest[]> {
    httpOptions.headers.set('AuthToken', this.token);
    return this.http.get<FriendshipRequest[]>(this.URL + '/get/received', httpOptions);
  }

  getExact(req: FriendshipDTO): Observable<FriendshipRequest> {
    httpOptions.headers.set('AuthToken', this.token);
    return this.http.post<FriendshipRequest>(this.URL + '/get/exact', req, httpOptions);
  }

  reject(req: FriendshipDTO): Observable<boolean> {
    httpOptions.headers.set('AuthToken', this.token);
    return this.http.post<boolean>(this.URL + '/reject', req, httpOptions);
  }

  remove(who: Passenger): Observable<boolean> {
    httpOptions.headers.set('AuthToken', this.token);
    return this.http.post<boolean>(this.URL + '/remove', who, httpOptions);
  }
}
