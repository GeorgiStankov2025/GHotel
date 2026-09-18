import {Component, inject, signal, Signal, WritableSignal} from '@angular/core';
import {MatButtonModule} from "@angular/material/button";
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {FormControl, FormGroup, ReactiveFormsModule} from '@angular/forms';
import {LoginRequestDTO} from '../../../model/loginRequestDTO';
import {AuthService} from '../../../services/auth.service';
import {Router} from '@angular/router';
import {HttpErrorResponse} from '@angular/common/http';

@Component({
  selector: 'app-login',
  styleUrl: './login.component.css',
  templateUrl: './login.component.html',
  imports: [MatFormFieldModule, MatButtonModule, MatInputModule, ReactiveFormsModule],
})
export class LoginComponent {

  private readonly authService: AuthService = inject(AuthService)

  private readonly router: Router = inject(Router)

  private readonly _errorMessage: WritableSignal<string> = signal<string>('');

  public errorMessage: Signal<string> = this._errorMessage.asReadonly();

  protected readonly loginForm: FormGroup = new FormGroup({
    username: new FormControl(''),
    rawPassword: new FormControl('')
  })

  protected onLogin(): void {
    const request: LoginRequestDTO = this.loginForm.value;
    this.authService.login(request).subscribe({
      next: (res) => {
        this.router.navigate(['/home']);
      },
      error: (err: HttpErrorResponse) => {
        if (err.status === 400) {
          this._errorMessage.set(err.message);
        } else {
          this._errorMessage.set(err.message)
        }
      }
    })
  }
}
