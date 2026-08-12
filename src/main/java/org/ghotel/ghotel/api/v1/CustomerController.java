package org.ghotel.ghotel.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

import org.ghotel.ghotel.dto.request.CustomerRequest;
import org.ghotel.ghotel.dto.response.CustomerResponse;
import org.ghotel.ghotel.dto.response.CustomerRoomsResponse;
import org.ghotel.ghotel.service.CustomerService;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customer")
@Description(value = "Used for managing Customer requests")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    @Operation(description = "Add new customer.")
    public ResponseEntity<CustomerResponse> addCustomer(
            @Valid
            @RequestBody CustomerRequest request) {
        CustomerResponse response = customerService.addCustomer(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(description = "Get customer by id.")
    public ResponseEntity<CustomerResponse> getCustomerById(
            @PathVariable UUID id) {
        CustomerResponse response = customerService.getCustomerById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}/rooms")
    @Operation(description = "Get customer with id including the rooms taken by him.")
    public ResponseEntity<CustomerRoomsResponse> getCustomerWithRoomsById(
            @PathVariable UUID id) {
        CustomerRoomsResponse response = customerService.getCustomerWithRoomsById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    @Operation(description = "Get all customers.")
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        List<CustomerResponse> response = customerService.getAllCustomers();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/rooms")
    @Operation(description = "Get all customers including their rooms.")
    public ResponseEntity<List<CustomerRoomsResponse>> getAllCustomersWithRooms() {
        List<CustomerRoomsResponse> response = customerService.getAllCustomersWithRooms();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(description = "Edit customer information.")
    public ResponseEntity<CustomerResponse> editCustomer(
            @PathVariable UUID id,
            @Valid @RequestBody CustomerRequest request) {
        CustomerResponse response = customerService.editCustomer(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Delete customer.")
    public ResponseEntity<CustomerResponse> deleteCustomer(
            @PathVariable UUID id) {
        CustomerResponse response = customerService.deleteCustomer(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
