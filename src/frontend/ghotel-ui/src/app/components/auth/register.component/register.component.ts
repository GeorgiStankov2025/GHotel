import {Component, inject, Signal, signal, WritableSignal} from '@angular/core';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatButton} from '@angular/material/button';
import {MatFormField, MatInput, MatLabel} from '@angular/material/input';
import {AuthService} from '../../../services/auth.service';
import {Router} from '@angular/router';
import {LoginRequestDTO} from '../../../model/loginRequestDTO';
import {HttpErrorResponse} from '@angular/common/http';
import {UserRequestDTO} from '../../../model/userRequestDTO';
import {UserResponseDTO} from '../../../model/userResponseDTO';

@Component({
  imports: [
    FormsModule,
    MatButton,
    MatFormField,
    MatInput,
    MatLabel,
    ReactiveFormsModule
  ],
  selector: 'app-register',
  styleUrl: './register.component.css',
  templateUrl: './register.component.html',
})
export class RegisterComponent {
  private readonly authService: AuthService = inject(AuthService)

  private readonly router: Router = inject(Router)

  private readonly _errorMessage: WritableSignal<string> = signal<string>('');

  public errorMessage: Signal<string> = this._errorMessage.asReadonly();

  protected readonly registerForm: FormGroup = new FormGroup({
    username: new FormControl(''),
    firstName: new FormControl(''),
    lastName: new FormControl(''),
    password: new FormControl(''),
  })

  protected onRegister(): void {
    const request: UserRequestDTO = this.registerForm.value;
    this.authService.register(request).subscribe({
      next: (res:UserResponseDTO):void => {
        this.router.navigate(['/']);
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
