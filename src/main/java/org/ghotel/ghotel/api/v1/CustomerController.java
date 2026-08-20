package org.ghotel.ghotel.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.ghotel.ghotel.dto.request.CustomerRequestDTO;
import org.ghotel.ghotel.dto.response.CustomerReservationsResponseDTO;
import org.ghotel.ghotel.dto.response.CustomerResponseDTO;
import org.ghotel.ghotel.dto.response.DeletedDTO;
import org.ghotel.ghotel.service.customer.ICustomerService;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/customer")
@Description(value = "Used for managing Customer requests")
public class CustomerController {
    private final ICustomerService customerService;

    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    @Operation(description = "Add new customer.")
    public ResponseEntity<CustomerResponseDTO> addCustomer(
            @Valid
            @RequestBody CustomerRequestDTO request) {
        CustomerResponseDTO response = customerService.addCustomer(request);
        log.info("Created customer with id: {}", response.id());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(description = "Get customer by id.")
    public ResponseEntity<CustomerResponseDTO> getCustomerById(
            @PathVariable UUID id) {
        CustomerResponseDTO response = customerService.getCustomerById(id);
        log.info("Found customer with id: {}", response.id());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}/reservations")
    @Operation(description = "Get customer with id including the reservations made by him.")
    public ResponseEntity<CustomerReservationsResponseDTO> getCustomerWithReservationsById(
            @PathVariable UUID id) {
        CustomerReservationsResponseDTO response = customerService.getCustomerWithReservationsById(id);
        log.info("Found customer with id: {} and his reservations", response.customerId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    @Operation(description = "Get all customers.")
    public ResponseEntity<List<CustomerResponseDTO>> getCustomers() {
        List<CustomerResponseDTO> response = customerService.getCustomers();
        log.info("Found all undeleted customers");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/reservations")
    @Operation(description = "Get all customers including their reservations.")
    public ResponseEntity<List<CustomerReservationsResponseDTO>> getCustomersWithReservations() {
        List<CustomerReservationsResponseDTO> response = customerService.getCustomersWithReservations();
        log.info("Found all undeleted customers with their reservations.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/deleted/{id}")
    @Operation(description = "Get deleted customer by id.")
    public ResponseEntity<CustomerResponseDTO> getDeletedCustomerById(
            @PathVariable UUID id) {
        CustomerResponseDTO response = customerService.getDeletedCustomerById(id);
        log.info("Found deleted customer with id: {}", id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @GetMapping("/deleted/{id}/reservations")
//    @Operation(description = "Get deleted customer with id including the reservations made by him.")
//    public ResponseEntity<CustomerReservationsResponseDTO> getDeletedCustomerWithReservationsById(
//            @PathVariable UUID id) {
//        CustomerReservationsResponseDTO response = customerService.getDeletedCustomerWithReservationsById(id);
//        log.info("Found deleted customer with id: {} and his reservations", id);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//
//    @GetMapping("/deleted")
//    @Operation(description = "Get all deleted customers.")
//    public ResponseEntity<List<CustomerResponseDTO>> getDeletedCustomers() {
//        List<CustomerResponseDTO> response = customerService.getDeletedCustomers();
//        log.info("Found all deleted customers.");
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//
//    @GetMapping("/deleted/reservations")
//    @Operation(description = "Get all deleted customers including their reservations.")
//    public ResponseEntity<List<CustomerReservationsResponseDTO>> getDeletedCustomersWithReservations() {
//        List<CustomerReservationsResponseDTO> response = customerService.getDeletedCustomersWithReservations();
//        log.info("Found all deleted customers with their reservations.");
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    @GetMapping("/all")
    @Operation(description = "Get all customers including soft deleted ones.")
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers() {
        List<CustomerResponseDTO> response = customerService.getAllCustomers();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all/reservations")
    @Operation(description = "Get all customers including their reservations including soft deleted ones.")
    public ResponseEntity<List<CustomerReservationsResponseDTO>> getAllCustomersWithReservations() {
        List<CustomerReservationsResponseDTO> response = customerService.getAllCustomersWithReservations();
        log.info("Found all customers with their reservations.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    @Operation(description = "Edit customer information.")
    public ResponseEntity<CustomerResponseDTO> editCustomer(
            @PathVariable UUID id,
            @Valid @RequestBody CustomerRequestDTO request) {
        CustomerResponseDTO response = customerService.editCustomer(id, request);
        log.info("Modified customer with id: {} ", id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Delete customer.")
    public ResponseEntity<DeletedDTO> deleteCustomer(
            @PathVariable UUID id) {
        DeletedDTO response = customerService.deleteCustomer(id);
        log.info("Deleted customer with id: {} ", id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{id}/restore")
    @Operation(description = "Restore customer.")
    public ResponseEntity<CustomerResponseDTO> restoreCustomer(
            @PathVariable UUID id) {
        CustomerResponseDTO response = customerService.restoreCustomer(id);
        log.info("Restored customer with id: {} ", id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
