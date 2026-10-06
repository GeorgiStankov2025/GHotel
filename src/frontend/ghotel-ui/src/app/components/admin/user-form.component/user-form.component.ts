import {Component, inject, Signal, signal, WritableSignal} from '@angular/core';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatFormField, MatInput, MatLabel} from '@angular/material/input';
import {Router} from '@angular/router';
import {HttpErrorResponse} from '@angular/common/http';
import {UserRequestDTO} from '../../../model/userRequestDTO';
import {UserService} from '../../../services/user.service';

@Component({
  imports: [
    FormsModule,
    MatFormField,
    MatInput,
    MatLabel,
    ReactiveFormsModule
  ],
  selector: 'app-user-form',
  styleUrl: './user-form.component.css',
  templateUrl: './user-form.component.html',
})
export class UserFormComponent {
  private readonly userService: UserService = inject(UserService)

  private readonly router: Router = inject(Router)

  private readonly _errorMessage: WritableSignal<string> = signal<string>('');

  public errorMessage: Signal<string> = this._errorMessage.asReadonly();

  protected readonly userForm: FormGroup = new FormGroup({
    username: new FormControl(''),
    firstName: new FormControl(''),
    lastName: new FormControl(''),
    password: new FormControl(''),
  })

  protected onAddUser(): void {
    const request: UserRequestDTO = this.userForm.value;
    this.userService.addUser(request).subscribe({
      next: (): void => {
        this.router.navigate(['/']);
      },
      error: (err: HttpErrorResponse): void => {
        if (err.status === 400) {
          this._errorMessage.set('Invalid request');
        } else {
          this._errorMessage.set(err.message)
        }
      }
    })
  }
}
