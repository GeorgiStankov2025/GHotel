import {Component, input, InputSignal} from '@angular/core';
import {CustomerResponseDTO} from '../../../model/customerResponseDTO';

@Component({
  imports: [],
  selector: 'app-customer-card',
  styleUrl: './customer-card.component.css',
  templateUrl: './customer-card.component.html',
})
export class CustomerCardComponent {
  public customer: InputSignal<CustomerResponseDTO | undefined> = input.required<CustomerResponseDTO | undefined  >()
}
