import { Component, inject, signal, WritableSignal } from '@angular/core';
import { RegisterRestService } from './register-rest.service';
import { RegisterRequest, User } from './register.models';

@Component({
  selector: 'app-register',
  imports: [],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss',
})
export class RegisterComponent {
  private readonly _registerRestService = inject(RegisterRestService);

  protected registered: WritableSignal<boolean> = signal(false);
  protected registeredUser: WritableSignal<User | null> = signal(null);
  protected loading: WritableSignal<boolean> = signal(false);
  protected errorMessage: WritableSignal<string | null> = signal(null);

  onSubmit(
    email: string | null,
    name: string | null,
    password: string | null,
  ): void {
    this.errorMessage.set(null);

    if (!email || !name || !password) {
      this.errorMessage.set('Email, imię i hasło są wymagane');
      return;
    }

    const request: RegisterRequest = {
      email,
      name,
      password,
    };

    this.loading.set(true);

    this._registerRestService.register(request).subscribe({
      next: (res) => {
        this.loading.set(false);
        this.registered.set(true);
        const user: User = {
          id: (res as any)?.id ?? '',
          email: (res as any)?.email ?? request.email,
          name: (res as any)?.name ?? request.name,
          active: (res as any)?.active ?? false,
        };
        this.registeredUser.set(user);
      },
      error: (err) => {
        this.loading.set(false);
        const status = (err as any)?.status;
        if (status === 409) {
          this.errorMessage.set('Użytkownik o podanym emailu już istnieje');
        } else {
          this.errorMessage.set('Błąd rejestracji. Spróbuj ponownie.');
        }
      },
    });
  }
}
