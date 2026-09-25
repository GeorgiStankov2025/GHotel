import {Component, inject, Signal, signal, WritableSignal} from '@angular/core';
import {MatFormField, MatInput, MatLabel} from "@angular/material/input";
import {FormControl, FormGroup, ReactiveFormsModule} from "@angular/forms";
import {CustomerService} from '../../../services/customer.service';
import {UserRequestDTO} from '../../../model/userRequestDTO';
import {CustomerResponseDTO} from '../../../model/customerResponseDTO';
import {Router} from '@angular/router';
import {CustomerRequestDTO} from '../../../model/customerRequestDTO';

@Component({
  imports: [
    MatFormField,
    MatInput,
    MatLabel,
    ReactiveFormsModule
  ],
  selector: 'app-customer-form',
  styleUrl: './customer-form.component.css',
  templateUrl: './customer-form.component.html',
})
export class CustomerFormComponent {

  private readonly _id: WritableSignal<string> = signal<string>('')
  protected id: Signal<string> = this._id.asReadonly()

  private readonly router: Router = inject(Router)
  private readonly customerService: CustomerService = inject(CustomerService)

  protected readonly customerForm: FormGroup = new FormGroup({
    firstName: new FormControl(''),
    lastName: new FormControl(''),
  })

  onSubmit(): void {
    const request: CustomerRequestDTO = this.customerForm.value;
    this.customerService.addCustomer(request).subscribe({
      next: (result: CustomerResponseDTO) => {
        if (result.id != null) {
          this._id.set(result.id)
          this.router.navigate(["reservation/new/details"]);
        }
      },
      error: (err: Error) => {
        console.log(err.message)
      }
    })
  }
}
