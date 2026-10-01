import {ChangeDetectionStrategy, Component, inject, OnInit, Signal, signal, WritableSignal} from '@angular/core';
import {UserCardComponent} from '../user-card.component/user-card.component';
import {UserResponseDTO} from '../../../model/userResponseDTO';
import {UserService} from '../../../services/user.service';

@Component({
  imports: [
    UserCardComponent
  ],
  selector: 'app-user-list',
  styleUrl: './user-list.component.css',
  templateUrl: './user-list.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class UserListComponent implements OnInit {
  private readonly _users: WritableSignal<UserResponseDTO[]> = signal<UserResponseDTO[]>([])
  protected users: Signal<UserResponseDTO[]> = this._users.asReadonly()
  private readonly userService: UserService = inject(UserService)

  ngOnInit(): void {
    this.userService.getUsers().subscribe({
      next: (result: UserResponseDTO[]): void => {
        this._users.set(result)
      },
      error: (err: Error): void => {
        console.log(err.message)
      }
    })
  }

  protected onDeleteUser(id: string): void {
    this.userService.deleteUser(id).subscribe({
      next: (): void => {
        this._users.update(list => list.filter(u => u.id !== id));
      },
      error: (err: Error): void => {
        console.log(err.message)
      }
    });
  }
}
