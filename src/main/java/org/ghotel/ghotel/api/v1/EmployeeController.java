package org.ghotel.ghotel.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.ghotel.ghotel.dto.request.EmployeeRequestDTO;
import org.ghotel.ghotel.dto.response.DeletedDTO;
import org.ghotel.ghotel.dto.response.EmployeeResponseDTO;
import org.ghotel.ghotel.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    @Operation(description = "Add employee.")
    public ResponseEntity<EmployeeResponseDTO> addEmployee(
            @Valid
            @RequestBody EmployeeRequestDTO request) {
        EmployeeResponseDTO response = employeeService.addEmployee(request);
        log.info("Created employee with id: {}", response.id());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(description = "Get employee by id.")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(
            @PathVariable UUID id) {
        EmployeeResponseDTO response = employeeService.getEmployeeById(id);
        log.info("Found employee with id: {}", response.id());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    @Operation(description = "Get all employees.")
    public ResponseEntity<List<EmployeeResponseDTO>> getEmployees() {
        List<EmployeeResponseDTO> response = employeeService.getEmployees();
        log.info("Found all undeleted employees");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/deleted/{id}")
    @Operation(description = "Get deleted employee by id.")
    public ResponseEntity<EmployeeResponseDTO> getDeletedEmployeeById(
            @PathVariable UUID id) {
        EmployeeResponseDTO response = employeeService.getDeletedEmployeeById(id);
        log.info("Found deleted employee with id: {}", id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/deleted")
    @Operation(description = "Get all deleted employees.")
    public ResponseEntity<List<EmployeeResponseDTO>> getDeletedEmployees() {
        List<EmployeeResponseDTO> response = employeeService.getDeletedEmployees();
        log.info("Found all deleted employees.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all")
    @Operation(description = "Get all employees including soft deleted ones.")
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
        List<EmployeeResponseDTO> response = employeeService.getAllEmployees();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(description = "Edit employee information.")
    public ResponseEntity<EmployeeResponseDTO> editEmployee(
            @PathVariable UUID id,
            @Valid @RequestBody EmployeeRequestDTO request) {
        EmployeeResponseDTO response = employeeService.editEmployee(id, request);
        log.info("Modified employee with id: {} ", id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Delete employee.")
    public ResponseEntity<DeletedDTO> deleteEmployee(
            @PathVariable UUID id) {
        DeletedDTO response = employeeService.deleteEmployee(id);
        log.info("Deleted employee with id: {} ", id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{id}/restore")
    @Operation(description = "Restore employee.")
    public ResponseEntity<EmployeeResponseDTO> restoreEmployee(
            @PathVariable UUID id) {
        EmployeeResponseDTO response = employeeService.restoreEmployee(id);
        log.info("Restored employee with id: {} ", id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
