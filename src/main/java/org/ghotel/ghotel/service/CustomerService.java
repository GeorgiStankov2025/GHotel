package org.ghotel.ghotel.service;

import org.ghotel.ghotel.dto.request.CustomerRequest;
import org.ghotel.ghotel.dto.response.CustomerResponse;
import org.ghotel.ghotel.dto.response.CustomerRoomsResponse;
import org.ghotel.ghotel.entity.Customer;
import org.ghotel.ghotel.exception.CustomerException;
import org.ghotel.ghotel.mapper.CustomerMapper;
import org.ghotel.ghotel.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
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
    public CustomerResponse addCustomer(CustomerRequest request) {
        Customer customer = customerMapper.toEntity(request);
        Customer saved = customerRepository.save(customer);
        return customerMapper.toCustomerResponse(saved);
    }

    @Transactional(readOnly = true)
    public CustomerResponse getCustomerById(Long id) {
        Customer customer = findCustomerById(id);
        return customerMapper.toCustomerResponse(customer);
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> getAllCustomers() {
        List<Customer> customers = customerRepository.getAllByDeletedFalse();
        return customers
                .stream()
                .map(customerMapper::toCustomerResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public CustomerRoomsResponse getCustomerWithRoomsById(Long id) {
        Customer customer = customerRepository.getWithRoomsByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new CustomerException("Customer not found with id: " + id));
        return customerMapper.toCustomerRoomsResponse(customer);
    }

    @Transactional(readOnly = true)
    public List<CustomerRoomsResponse> getAllCustomersWithRooms() {
        List<Customer> customers = customerRepository.getAllWithRoomsByDeletedFalse();
        return customers
                .stream()
                .map(customerMapper::toCustomerRoomsResponse)
                .toList();
    }

    @Transactional
    public CustomerResponse editCustomer(Long id, CustomerRequest request) {
        Customer customer = findCustomerById(id);
        customer = customerMapper.changeCustomer(customer, request);
        return customerMapper.toCustomerResponse(customer);
    }

    @Transactional
    public CustomerResponse deleteCustomer(Long id) {
        Customer customer = findCustomerById(id);
        customer.setDeleted(true);
        return customerMapper.toCustomerResponse(customer);
    }

    private Customer findCustomerById(Long id) {
        return customerRepository.getByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new CustomerException("Customer not found with id: " + id));
    }
}
