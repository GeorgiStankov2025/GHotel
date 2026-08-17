package org.ghotel.ghotel.service;

import org.ghotel.ghotel.dto.request.CustomerRequestDTO;
import org.ghotel.ghotel.dto.response.CustomerReservationsResponseDTO;
import org.ghotel.ghotel.dto.response.CustomerResponseDTO;
import org.ghotel.ghotel.dto.response.DeletedDTO;
import org.ghotel.ghotel.entity.Customer;
import org.ghotel.ghotel.exception.ResourceNotFoundException;
import org.ghotel.ghotel.mapper.CustomerMapper;
import org.ghotel.ghotel.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(
            CustomerRepository customerRepository,
            CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Transactional
    public CustomerResponseDTO addCustomer(CustomerRequestDTO request) {
        Customer customer = customerMapper.toCustomerEntity(request);
        Customer saved = customerRepository.save(customer);
        return customerMapper.toCustomerResponseDTO(saved);
    }

    public CustomerResponseDTO getCustomerById(UUID id) {
        Customer customer = findCustomerById(id);
        return customerMapper.toCustomerResponseDTO(customer);
    }

    public List<CustomerResponseDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.getAllByDeletedFalse();
        return customers
                .stream()
                .map(customerMapper::toCustomerResponseDTO)
                .toList();
    }

    public CustomerReservationsResponseDTO getCustomerWithReservationsById(UUID id) {
        Customer customer = customerRepository.getWithReservationsByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found."));
        return customerMapper.toCustomerReservationsResponseDTO(customer);
    }

    public List<CustomerReservationsResponseDTO> getAllCustomersWithReservations() {
        List<Customer> customers = customerRepository.getAllWithReservationsByDeletedFalse();
        return customers
                .stream()
                .map(customerMapper::toCustomerReservationsResponseDTO)
                .toList();
    }

    @Transactional
    public CustomerResponseDTO editCustomer(UUID id, CustomerRequestDTO request) {
        Customer customer = findCustomerById(id);
        customer = customerMapper.updateCustomer(request, customer);
        return customerMapper.toCustomerResponseDTO(customer);
    }

    @Transactional
    public DeletedDTO deleteCustomer(UUID id) {
        Customer customer = findCustomerById(id);
        customer.setDeleted(true);
        return new DeletedDTO("Resource deleted successfully.");
    }

    public Customer findCustomerById(UUID id) {
        return customerRepository.getByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found."));
    }
}
