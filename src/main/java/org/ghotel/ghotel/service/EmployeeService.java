package org.ghotel.ghotel.service;

import org.ghotel.ghotel.dto.request.EmployeeRequestDTO;
import org.ghotel.ghotel.dto.response.EmployeeResponseDTO;
import org.ghotel.ghotel.entity.Employee;
import org.ghotel.ghotel.exception.EmployeeException;
import org.ghotel.ghotel.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Transactional
    public EmployeeResponseDTO addEmployee(EmployeeRequestDTO request) {
        if (employeeRepository.existsByUsername(request.username())) {
            throw new EmployeeException
                    ("Invalid username.");
        }
        Employee employee = employeeMapper.toEntity(request);
        Employee saved = employeeRepository.save(employee);
        return employeeMapper.toResponse(saved);
    }


    @Transactional
    public EmployeeResponseDTO editEmployee(UUID id, EmployeeRequestDTO request) {

        Employee employee = findByIdUndeleted(id);
        employee = employeeMapper.changeEmployee(request, employee);
        return employeeMapper.toResponse(employee);
    }

    @Transactional
    public EmployeeResponseDTO deleteEmployee(UUID id) {
        Employee employee = findByIdUndeleted(id);
        employee.setUpdatedAt(LocalDateTime.now());
        employee.setDeleted(true);
        return employeeMapper.toResponse(employee);
    }

    public EmployeeResponseDTO getEmployeeById(UUID id) {
        Employee employee = findByIdUndeleted(id);
        return employeeMapper.toResponse(employee);
    }

    public List<EmployeeResponseDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.getAllByDeletedFalse();
        return employees.stream()
                .map(employeeMapper::toResponse)
                .toList();
    }

    private Employee findByIdUndeleted(UUID id) {
        return employeeRepository.getEmployeeByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new EmployeeException("Employee not found."));
    }

}
