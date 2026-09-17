import {inject, Service} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {CustomerRequestDTO} from '../model/customerRequestDTO';
import {Observable} from 'rxjs';
import {CustomerResponseDTO} from '../model/customerResponseDTO';
import {DeletedDTO} from '../model/deletedDTO';

@Service()
export class CustomerService {
  private readonly url: string = 'http://localhost:8084/api/v1/customer'

  private readonly httpClient: HttpClient = inject(HttpClient)

  public addCustomer(request: CustomerRequestDTO): Observable<CustomerResponseDTO> {
    return this.httpClient.post<CustomerResponseDTO>(this.url, request)
  }

  public getCustomer(id: string): Observable<CustomerResponseDTO> {
    return this.httpClient.get<CustomerResponseDTO>(`${this.url}/${id}`)
  }

  public getCustomers(): Observable<CustomerResponseDTO[]> {
    return this.httpClient.get<CustomerResponseDTO[]>(this.url)
  }

  public editCustomer(id: string, request: CustomerRequestDTO): Observable<CustomerResponseDTO> {
    return this.httpClient.put<CustomerResponseDTO>(`${this.url}/${id}`, request)
  }

  public deleteCustomer(id: string): Observable<DeletedDTO> {
    return this.httpClient.delete<DeletedDTO>(`${this.url}/${id}`)
  }
}
