package org.ghotel.ghotel.service;

import org.ghotel.ghotel.dto.request.EmployeeRequest;
import org.ghotel.ghotel.dto.response.EmployeeResponse;
import org.ghotel.ghotel.entity.Employee;
import org.ghotel.ghotel.exception.EmployeeException;
import org.ghotel.ghotel.mapper.EmployeeMapper;
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
    public EmployeeResponse addEmployee(EmployeeRequest request) {
        if (employeeRepository.existsByUsername(request.username())) {
            throw new EmployeeException
                    ("Invalid username.");
        }
        Employee employee = employeeMapper.toEntity(request);
        Employee saved = employeeRepository.save(employee);
        return employeeMapper.toResponse(saved);
    }


    @Transactional
    public EmployeeResponse editEmployee(UUID id, EmployeeRequest request) {

        Employee employee = findByIdUndeleted(id);
        employee = employeeMapper.changeEmployee(request, employee);
        return employeeMapper.toResponse(employee);
    }

    @Transactional
    public EmployeeResponse deleteEmployee(UUID id) {
        Employee employee = findByIdUndeleted(id);
        employee.setUpdatedAt(LocalDateTime.now());
        employee.setDeleted(true);
        return employeeMapper.toResponse(employee);
    }

    public EmployeeResponse getEmployeeById(UUID id) {
        Employee employee = findByIdUndeleted(id);
        return employeeMapper.toResponse(employee);
    }

    public List<EmployeeResponse> getAllEmployees() {
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
