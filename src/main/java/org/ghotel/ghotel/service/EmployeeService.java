package org.ghotel.ghotel.service;

import org.ghotel.ghotel.dto.request.EmployeeRequestDTO;
import org.ghotel.ghotel.dto.response.DeletedDTO;
import org.ghotel.ghotel.dto.response.EmployeeResponseDTO;
import org.ghotel.ghotel.entity.Employee;
import org.ghotel.ghotel.exception.InvalidRequestException;
import org.ghotel.ghotel.exception.ResourceNotFoundException;
import org.ghotel.ghotel.mapper.EmployeeMapper;
import org.ghotel.ghotel.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            throw new InvalidRequestException
                    ("Invalid username.");
        }
        Employee employee = employeeMapper.toEmployeeEntity(request);
        Employee saved = employeeRepository.save(employee);
        return employeeMapper.toEmployeeResponseDTO(saved);
    }


    @Transactional
    public EmployeeResponseDTO editEmployee(UUID id, EmployeeRequestDTO request) {
        Employee employee = findByIdUndeleted(id);
        employee = employeeMapper.updateEmployee(request, employee);
        return employeeMapper.toEmployeeResponseDTO(employee);
    }

    @Transactional
    public DeletedDTO deleteEmployee(UUID id) {
        Employee employee = findByIdUndeleted(id);
        employee.setDeleted(true);
        return new DeletedDTO("Resource deleted successfully.");
    }

    public EmployeeResponseDTO getEmployeeById(UUID id) {
        Employee employee = findByIdUndeleted(id);
        return employeeMapper.toEmployeeResponseDTO(employee);
    }

    public List<EmployeeResponseDTO> getEmployees() {
        List<Employee> employees = employeeRepository.getAllByDeletedFalse();
        return employees.stream()
                .map(employeeMapper::toEmployeeResponseDTO)
                .toList();
    }

    public List<EmployeeResponseDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(employeeMapper::toEmployeeResponseDTO)
                .toList();
    }

    private Employee findByIdUndeleted(UUID id) {
        return employeeRepository.getEmployeeByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found."));
    }



}
