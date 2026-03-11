import { Component, inject, signal, WritableSignal } from '@angular/core';
import { LoginRestService } from './login-rest.service';
import { LoginRequest, LoginResponse } from './login.models';

@Component({
  selector: 'app-login',
  imports: [],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  private readonly _loginRestService = inject(LoginRestService);

  protected loggedIn: WritableSignal<boolean> = signal(false);
  protected loginResponse: WritableSignal<LoginResponse | null> = signal(null);
  protected loading: WritableSignal<boolean> = signal(false);
  protected errorMessage: WritableSignal<string | null> = signal(null);

  protected login(email: string | null, password: string | null): void {
    this.errorMessage.set(null);

    if (!email || !password) {
      this.errorMessage.set('Email i hasło są wymagane');
      return;
    }

    const request: LoginRequest = {
      email,
      password,
    };

    this.loading.set(true);

    this._loginRestService.login(request).subscribe({
      next: (res) => {
        this.loading.set(false);
        this.loggedIn.set(true);
        this.loginResponse.set(res);
      },
      error: (err) => {
        this.loading.set(false);
        const status = (err as any)?.status;
        if (status === 401) {
          this.errorMessage.set('Nieprawidłowy email lub hasło');
        } else {
          this.errorMessage.set('Błąd logowania. Spróbuj ponownie.');
        }
      },
    });
  }

  protected logout(): void {
    localStorage.removeItem('token');
    this.loggedIn.set(false);
    this.loginResponse.set(null);
    this.errorMessage.set(null);
  }
}
