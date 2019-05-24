import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {TokenStorageService} from '../../shared/token-storage.service';
import {Observable} from 'rxjs';
import {PriceList} from '../../shared/model/price-list';
import {PriceListDTO} from './price-list-dto';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type' : 'application/json'})
};
@Injectable({
  providedIn: 'root'
})
export class PriceListService {
  token: string = this.tokenStorage.getToken();
  headers: HttpHeaders = new HttpHeaders({AuthToken: this.token });
  private URL = 'http://localhost:8080/api/price-list';

  constructor(private http: HttpClient, private tokenStorage: TokenStorageService) {
    httpOptions.headers.set('AuthToken', this.token);
  }
  get(): Observable<PriceList[]> {
    httpOptions.headers.set('AuthToken', this.token);
    return this.http.get<PriceList[]>(this.URL, httpOptions);
  }
  getOne(ID: BigInteger): Observable<PriceList> {
    httpOptions.headers.set('AuthToken', this.token);
    return this.http.get<PriceList>(this.URL + '/' + ID, httpOptions);
  }
  create(dto: PriceListDTO): Observable<PriceList> {
    httpOptions.headers.set('AuthToken', this.token);
    return this.http.post<PriceList>(this.URL + '/add', dto, httpOptions);
  }
  update(priceList: PriceList): Observable<PriceList> {
    httpOptions.headers.set('AuthToken', this.token);
    return this.http.post<PriceList>(this.URL + '/update', priceList, httpOptions);
  }
  delete(ID: BigInteger): Observable<boolean> {
    httpOptions.headers.set('AuthToken', this.token);
    return this.http.delete<boolean>(this.URL + '/delete/' + ID, httpOptions);
  }
}
