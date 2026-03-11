import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { LoginRequest, LoginResponse } from './login.models';

@Injectable({
  providedIn: 'root',
})
export class LoginRestService {
  private readonly _http = inject(HttpClient);
  private url = '/auth';

  login(request: LoginRequest): Observable<LoginResponse> {
    return this._http.post<LoginResponse>(`${this.url}/login`, request)
    .pipe(
      tap((res: LoginResponse) => {
        localStorage.setItem('token', res.token);
      }
    ));
  }
}