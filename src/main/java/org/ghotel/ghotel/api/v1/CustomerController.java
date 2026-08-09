package org.ghotel.ghotel.api.v1;

import jakarta.validation.Valid;
import org.ghotel.ghotel.dto.request.CustomerRequest;
import org.ghotel.ghotel.dto.response.CustomerResponse;
import org.ghotel.ghotel.dto.response.CustomerRoomsResponse;
import org.ghotel.ghotel.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> addCustomer(
            @Valid
            @RequestBody CustomerRequest request) {
        CustomerResponse response = customerService.addCustomer(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(
            @PathVariable Long id) {
        CustomerResponse response = customerService.getCustomerById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}/rooms")
    public ResponseEntity<CustomerRoomsResponse> getCustomerWithRoomsById(
            @PathVariable Long id) {
        CustomerRoomsResponse response = customerService.getCustomerWithRoomsById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        List<CustomerResponse> response = customerService.getAllCustomers();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<CustomerRoomsResponse>> getAllCustomersWithRooms() {
        List<CustomerRoomsResponse> response = customerService.getAllCustomersWithRooms();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> editCustomer(
            @PathVariable Long id,
            @Valid @RequestBody CustomerRequest request) {
        CustomerResponse response = customerService.editCustomer(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomerResponse> deleteCustomer(
            @PathVariable Long id) {
        CustomerResponse response = customerService.deleteCustomer(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
