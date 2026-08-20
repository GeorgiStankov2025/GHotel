package org.ghotel.ghotel.service.customer;

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

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class CustomerService implements ICustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(
            CustomerRepository customerRepository,
            CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Transactional
    @Override
    public CustomerResponseDTO addCustomer(CustomerRequestDTO request) {
        Customer customer = customerMapper.toCustomerEntity(request);
        Customer saved = customerRepository.save(customer);
        return customerMapper.toCustomerResponseDTO(saved);
    }

    @Override
    public CustomerResponseDTO getCustomerById(UUID id) {
        Customer customer = findCustomerById(id);
        return customerMapper.toCustomerResponseDTO(customer);
    }

    @Override
    public List<CustomerResponseDTO> getCustomers() {
        List<Customer> customers = customerRepository.getAllByDeletedFalse();
        return customers
                .stream()
                .map(customerMapper::toCustomerResponseDTO)
                .toList();
    }

    @Override
    public CustomerReservationsResponseDTO getCustomerWithReservationsById(UUID id) {
        Customer customer = customerRepository.getWithReservationsByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found."));
        return customerMapper.toCustomerReservationsResponseDTO(customer);
    }

    @Override
    public List<CustomerReservationsResponseDTO> getCustomersWithReservations() {
        List<Customer> customers = customerRepository.getAllWithReservationsByDeletedFalse();
        return customers
                .stream()
                .map(customerMapper::toCustomerReservationsResponseDTO)
                .toList();
    }

    @Override
    public List<CustomerResponseDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers
                .stream()
                .map(customerMapper::toCustomerResponseDTO)
                .toList();
    }

    @Override
    public List<CustomerReservationsResponseDTO> getAllCustomersWithReservations() {
        List<Customer> customers = customerRepository.findAllWithReservationsBy();
        return customers
                .stream()
                .map(customerMapper::toCustomerReservationsResponseDTO)
                .toList();
    }

    @Override
    public CustomerResponseDTO getDeletedCustomerById(UUID id) {
        Customer customer = findDeletedCustomerById(id);
        return customerMapper.toCustomerResponseDTO(customer);
    }

//    public List<CustomerResponseDTO> getDeletedCustomers() {
//        List<Customer> customers = customerRepository.getAllByDeletedTrue();
//        return customers
//                .stream()
//                .map(customerMapper::toCustomerResponseDTO)
//                .toList();
//    }
//
//    public CustomerReservationsResponseDTO getDeletedCustomerWithReservationsById(UUID id) {
//        Customer customer = customerRepository.getWithReservationsByIdAndDeletedTrue(id)
//                .orElseThrow(() ->
//                        new ResourceNotFoundException("Customer not found."));
//        return customerMapper.toCustomerReservationsResponseDTO(customer);
//    }
//
//    public List<CustomerReservationsResponseDTO> getDeletedCustomersWithReservations() {
//        List<Customer> customers = customerRepository.getAllWithReservationsByDeletedTrue();
//        return customers
//                .stream()
//                .map(customerMapper::toCustomerReservationsResponseDTO)
//                .toList();
//    }

    @Transactional
    @Override
    public CustomerResponseDTO editCustomer(UUID id, CustomerRequestDTO request) {
        Customer customer = findCustomerById(id);
        customer = customerMapper.updateCustomer(request, customer);
        return customerMapper.toCustomerResponseDTO(customer);
    }

    @Transactional
    @Override
    public DeletedDTO deleteCustomer(UUID id) {
        Customer customer = findCustomerById(id);
        customer.setDeleted(true);
        return new DeletedDTO("Resource deleted successfully.");
    }

    @Transactional
    @Override
    public CustomerResponseDTO restoreCustomer(UUID id) {
        Customer customer = findCustomerById(id);
        customer.setDeleted(false);
        return customerMapper.toCustomerResponseDTO(customer);
    }

    @Override
    public Customer findCustomerById(UUID id) {
        return customerRepository.getByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found with id: "+id));
    }

    @Override
    public Customer findDeletedCustomerById(UUID id) {
        return customerRepository.getByIdAndDeletedTrue(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found with id: "+id));
    }
}
