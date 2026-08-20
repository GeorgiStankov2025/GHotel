package org.ghotel.ghotel.service.customer;

import org.ghotel.ghotel.dto.request.CustomerRequestDTO;
import org.ghotel.ghotel.dto.response.CustomerReservationsResponseDTO;
import org.ghotel.ghotel.dto.response.CustomerResponseDTO;
import org.ghotel.ghotel.dto.response.DeletedDTO;
import org.ghotel.ghotel.entity.Customer;

import java.util.List;
import java.util.UUID;

public interface ICustomerService {

    CustomerResponseDTO addCustomer(CustomerRequestDTO request);

    CustomerResponseDTO getCustomerById(UUID id);

    List<CustomerResponseDTO> getCustomers();

    CustomerReservationsResponseDTO getCustomerWithReservationsById(UUID id);

    List<CustomerReservationsResponseDTO> getCustomersWithReservations();

    List<CustomerResponseDTO> getAllCustomers();

    List<CustomerReservationsResponseDTO> getAllCustomersWithReservations();

    CustomerResponseDTO getDeletedCustomerById(UUID id);

    CustomerResponseDTO editCustomer(UUID id, CustomerRequestDTO request);

    DeletedDTO deleteCustomer(UUID id);

    CustomerResponseDTO restoreCustomer(UUID id);

    Customer findCustomerById(UUID id);

    Customer findDeletedCustomerById(UUID id);
}
