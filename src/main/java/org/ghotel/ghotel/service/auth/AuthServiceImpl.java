package org.ghotel.ghotel.service.auth;

import lombok.extern.slf4j.Slf4j;
import org.ghotel.ghotel.common.security.service.JwtService;
import org.ghotel.ghotel.dto.request.EmployeeRequestDTO;
import org.ghotel.ghotel.dto.request.LoginRequestDTO;
import org.ghotel.ghotel.dto.request.TokenRequestDTO;
import org.ghotel.ghotel.dto.response.AuthResponseDTO;
import org.ghotel.ghotel.dto.response.EmployeeResponseDTO;
import org.ghotel.ghotel.entity.Employee;
import org.ghotel.ghotel.exception.BadCredentialsException;
import org.ghotel.ghotel.exception.InvalidRequestException;
import org.ghotel.ghotel.mapper.EmployeeMapper;
import org.ghotel.ghotel.repository.EmployeeRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(EmployeeRepository employeeRepository,
                           EmployeeMapper employeeMapper,
                           PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO request) {
        Employee employee = employeeRepository.getEmployeeByUsernameAndDeletedFalse(request.username())
                .orElseThrow(() -> new InvalidRequestException("Login failed: Employee with username: "
                        + request.username() + " not found."));
        if (!passwordEncoder.matches(request.rawPassword(), employee.getPassword())) {
            throw new BadCredentialsException("Login failed: Wrong username or password.");
        }
        log.info("User with username: {} logged in.", request.username());
        return jwtService.generateTokenPair(employee.getUsername(), employee.getRole().toString());
    }

    @Override
    public EmployeeResponseDTO register(EmployeeRequestDTO request) {
        if (employeeRepository.existsByUsername(request.username())) {
            throw new InvalidRequestException
                    ("Employee with username: " + request.username() + " already exists.");
        }
        Employee employee = employeeMapper.toEmployeeEntity(request);
        employee.setPassword(passwordEncoder.encode(request.password()));
        Employee saved = employeeRepository.save(employee);
        log.info("Registered user with username: {}.", request.username());
        return employeeMapper.toEmployeeResponseDTO(saved);
    }

    @Override
    public AuthResponseDTO refresh(TokenRequestDTO request) {
        return jwtService.refreshAccessToken(request.refreshToken());
    }
}
