package org.ghotel.ghotel.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.ghotel.ghotel.dto.request.EmployeeRequestDTO;
import org.ghotel.ghotel.dto.request.LoginRequestDTO;
import org.ghotel.ghotel.dto.response.EmployeeResponseDTO;
import org.ghotel.ghotel.dto.response.LoginResponseDTO;
import org.ghotel.ghotel.service.auth.AuthService;
import org.ghotel.ghotel.service.auth.AuthServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @Operation(description = "Register employee.")
    public ResponseEntity<EmployeeResponseDTO> register(
            @Valid
            @RequestBody EmployeeRequestDTO request) {
        EmployeeResponseDTO response = authService.register(request);
        log.info("Registered employee with username: {}", response.username());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @Operation(description = "Login endpoint for employees/users.")
    public ResponseEntity<LoginResponseDTO> login(
            @Valid
            @RequestBody LoginRequestDTO request) {
        LoginResponseDTO response = authService.login(request);
        log.info("Employee with username: {} logged in", request.username());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
