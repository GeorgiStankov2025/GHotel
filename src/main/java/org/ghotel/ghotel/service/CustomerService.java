package org.ghotel.ghotel.service;

import org.ghotel.ghotel.dto.request.CustomerRequestDTO;
import org.ghotel.ghotel.dto.response.CustomerResponseDTO;
import org.ghotel.ghotel.entity.Customer;
import org.ghotel.ghotel.exception.CustomerException;
import org.ghotel.ghotel.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
        Customer customer = customerMapper.toEntity(request);
        Customer saved = customerRepository.save(customer);
        return customerMapper.toCustomerResponse(saved);
    }

    public CustomerResponseDTO getCustomerById(UUID id) {
        Customer customer = findCustomerById(id);
        return customerMapper.toCustomerResponse(customer);
    }

    public List<CustomerResponseDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.getAllByDeletedFalse();
        return customers
                .stream()
                .map(customerMapper::toCustomerResponse)
                .toList();
    }

    public CustomerRoomsResponse getCustomerWithRoomsById(UUID id) {
        Customer customer = customerRepository.getWithRoomsByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new CustomerException("Customer not found."));
        return customerMapper.toCustomerRoomsResponse(customer);
    }

    public List<CustomerRoomsResponse> getAllCustomersWithRooms() {
        List<Customer> customers = customerRepository.getAllWithRoomsByDeletedFalse();
        return customers
                .stream()
                .map(customerMapper::toCustomerRoomsResponse)
                .toList();
    }

    @Transactional
    public CustomerResponseDTO editCustomer(UUID id, CustomerRequestDTO request) {
        Customer customer = findCustomerById(id);
        customer = customerMapper.changeCustomer(customer, request);
        return customerMapper.toCustomerResponse(customer);
    }

    @Transactional
    public CustomerResponseDTO deleteCustomer(UUID id) {
        Customer customer = findCustomerById(id);
        customer.setUpdatedAt(LocalDateTime.now());
        customer.setDeleted(true);
        return customerMapper.toCustomerResponse(customer);
    }

    private Customer findCustomerById(UUID id) {
        return customerRepository.getByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new CustomerException("Customer not found."));
    }
}
