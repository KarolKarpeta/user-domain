import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RegisterRequest, User } from './register.models';

@Injectable({
  providedIn: 'root',
})
export class RegisterRestService {
  private readonly _http = inject(HttpClient);
  private url = '/users';

  public register(request: RegisterRequest): Observable<User> {
    return this._http.post<User>(`${this.url}/register`, request);
  }
}
