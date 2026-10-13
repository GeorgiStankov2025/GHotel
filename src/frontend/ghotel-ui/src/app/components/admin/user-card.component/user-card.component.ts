import {Component, input, InputSignal, output, OutputEmitterRef} from '@angular/core';
import {UserResponseDTO} from '../../../model/userResponseDTO';

@Component({
  imports: [],
  selector: 'app-user-card',
  styleUrl: './user-card.component.css',
  templateUrl: './user-card.component.html',
})
export class UserCardComponent {
  public user: InputSignal<UserResponseDTO> = input.required<UserResponseDTO>()

  public delete: OutputEmitterRef<string> = output<string>();

  onDelete(): void {
    const id: string | undefined = this.user().id;
    if (id) {
      this.delete.emit(id);
    }
  }
}
