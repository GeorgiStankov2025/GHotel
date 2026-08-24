package org.ghotel.ghotel.service.auth;

import org.ghotel.ghotel.dto.request.EmployeeRequestDTO;
import org.ghotel.ghotel.dto.request.LoginRequestDTO;
import org.ghotel.ghotel.dto.response.EmployeeResponseDTO;
import org.ghotel.ghotel.dto.response.LoginResponseDTO;
import org.ghotel.ghotel.entity.Employee;
import org.ghotel.ghotel.exception.BadCredentialsException;
import org.ghotel.ghotel.exception.InvalidRequestException;
import org.ghotel.ghotel.mapper.EmployeeMapper;
import org.ghotel.ghotel.repository.EmployeeRepository;
import org.ghotel.ghotel.security.JwtUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {
        Employee employee = employeeRepository.getEmployeeByUsernameAndDeletedFalse(request.username())
                .orElseThrow(() -> new InvalidRequestException("Login failed: Employee with username: " + request.username() + " not found."));
        if (!passwordEncoder.matches(request.rawPassword(), employee.getPassword())) {
            throw new BadCredentialsException("Login failed: Wrong username or password.");
        }
        String token = jwtUtils.generateToken(employee.getUsername());
        return employeeMapper.toLoginResponseDTO(token);
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
        return employeeMapper.toEmployeeResponseDTO(saved);
    }
}
